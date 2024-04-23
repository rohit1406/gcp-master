package com.gcp.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.dto.MessageDto;
import com.gcp.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PubSubService {
	@Autowired
	private PublisherService publisherService;
	@Autowired
	private SubscriberService subscriberService;
	public ResponseDto publish(MessageDto message) {
		try {
			return ResponseDto.builder().message(publisherService.publishMessage(message)).build();
		} catch (IOException | InterruptedException | ExecutionException e) {
			log.error("Exception occured while publishing the message", e);
		}
		return null;
	}
	public void consumerMessages() {
		try {
			subscriberService.consumeMessage();
		} catch (IOException | InterruptedException | ExecutionException e) {
			log.error("exception occured while consuming the messages", e);
		} catch (TimeoutException e) {
			log.info("Subscriber terminated as configured");
		}
	}

}
