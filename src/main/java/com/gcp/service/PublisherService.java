package com.gcp.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gcp.dto.MessageDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PublisherService {
	@Value("${gcp.project.id}")
	private String projectId;
	@Value("${gcp.pubsub.topic.id}")
	private String topicId;

	public String publishMessage(MessageDto message) throws IOException, InterruptedException, ExecutionException {
		TopicName topicName = TopicName.of(projectId, topicId);
		Publisher publisher = null;
		try {
			publisher = Publisher.newBuilder(topicName).build();
			PubsubMessage pubSubMessage = PubsubMessage.newBuilder()
					.setData(ByteString.copyFromUtf8(message.getMessage())).build();
			ApiFuture<String> messageIdFuture = publisher.publish(pubSubMessage);
			log.info("Message published - {}", messageIdFuture.get());
			return messageIdFuture.get();
		} finally {
			if (publisher != null) {
				publisher.shutdown();
				publisher.awaitTermination(1, TimeUnit.MINUTES);
			}
		}
	}
}
