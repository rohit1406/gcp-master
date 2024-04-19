package com.gcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.dto.ResponseDto;
import com.gcp.service.GcpService;
import com.gcp.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = {CommonConstants.ENDPOINT_VERSION})
@Slf4j
public class HelloController implements CommonConstants {
	@Value("${NAME:Dear Google Cloud}")
	private String name;
	
	@Autowired
	private GcpService gcpService;

	@Operation(summary = "hello from server")
	@GetMapping("/")
	public ResponseDto hello() {
		log.info("Saying hello from gcp");
		return ResponseDto.builder().message("Hello " + name + "!!!").build();
	}
	
	@Operation(summary = "test database connection")
	@GetMapping("/test-conn")
	public ResponseDto testConnection() {
		log.info("testing db connection");
		return ResponseDto.builder().message("Connection success: " + gcpService.testConnection()).build();
	}
}
