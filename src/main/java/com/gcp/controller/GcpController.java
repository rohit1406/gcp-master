package com.gcp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.service.GcpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GcpController {
	@Value("${NAME:Dear Google Cloud}")
	private String name;
	
	@Autowired
	private GcpService gcpService;

	@GetMapping("/")
	public String hello() {
		log.info("Saying hello from gcp");
		return "Hello " + name + "!!!";
	}
	
	@GetMapping("/list-buckets")
	public List<String> getAvailableBucketNames(){
		return gcpService.getBucketList();
	}

}
