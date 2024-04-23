package com.gcp.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubscriberService {
	@Value("${gcp.project.id}")
	private String projectId;
	@Value("${gcp.pubsub.sub.id}")
	private String subscriberId;

	public void consumeMessage() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriberId);
		MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
			log.info("Message received: id - {}, data - {}", message.getMessageId(), message.getData().toStringUtf8());
			consumer.ack();
		};
		Subscriber subscriber = null;
		try {
			subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
			subscriber.startAsync().awaitRunning();
			log.info("Listening for messages on {}", subscriptionName.toString());
			subscriber.awaitTerminated(30, TimeUnit.SECONDS);
		} finally {
			if (subscriber != null) {
				subscriber.stopAsync();
			}
		}
	}
}
