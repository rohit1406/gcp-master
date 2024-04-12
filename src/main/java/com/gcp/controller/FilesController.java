package com.gcp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gcp.dto.FileDto;
import com.gcp.service.FilesService;
import com.gcp.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = {CommonConstants.ENDPOINT_VERSION+"files"})
@Slf4j
public class FilesController {
	@Autowired
	private FilesService filesService;
	
	@Operation(summary = "lists all the file details available in the default configured GCP bucket")
	@GetMapping
	public List<FileDto> listFiles(){
		log.info("request received to list files");
		return filesService.listFiles();
	}
	
	@Operation(summary = "Upload a file to the default configured GCP bucket")
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public List<FileDto> addFiles(@RequestPart("file")MultipartFile multipartFile) {
		log.info("files to be uploaded received");
		return filesService.uploadFiles(multipartFile);
	}

}
