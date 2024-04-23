package com.gcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.dto.BucketNameDto;
import com.gcp.dto.MessageDto;
import com.gcp.dto.ResponseDto;
import com.gcp.service.GcpService;
import com.gcp.service.PubSubService;
import com.gcp.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = {CommonConstants.ENDPOINT_VERSION+"pubsub"})
@Slf4j
public class PubSubController implements CommonConstants {
	@Autowired
	private PubSubService pubSubService;
	
	@Operation(summary = "list all available buckets in the GCP")
	@PostMapping("/publish")
	public ResponseDto publishMessage(@RequestBody MessageDto message){
		log.info("Message received to publish");
		return pubSubService.publish(message);
	}
	
	@GetMapping("/consume")
	public ResponseDto consumerMessages() {
		log.info("notification received to consume the message");
		pubSubService.consumerMessages();
		return ResponseDto.builder().message("notification received to consume the message").build();
	}
}
