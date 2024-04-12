package com.gcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.dto.BucketNameDto;
import com.gcp.service.GcpService;
import com.gcp.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = {CommonConstants.ENDPOINT_VERSION+"bucket"})
@Slf4j
public class GcpBucketController implements CommonConstants {
	@Autowired
	private GcpService gcpService;
	
	@Operation(summary = "list all available buckets in the GCP")
	@GetMapping("/list")
	public BucketNameDto getAvailableBucketNames(){
		return gcpService.getBucketList();
	}
}
