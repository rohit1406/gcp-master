package com.gcp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gcp.dto.FileDto;
import com.gcp.exceptions.FileValidationFailureException;
import com.gcp.exceptions.GcpFileUploadException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FilesService {
	@Autowired
	private DataBucketService dataBucketService;

	public List<FileDto> uploadFiles(MultipartFile multipartFile) {
		log.info("Start file upload service");
		return Arrays.asList(multipartFile).stream().map(this::uploadFile).collect(Collectors.toList());
	}

	private FileDto uploadFile(MultipartFile multipartFile) {
		if(multipartFile.getSize() > 512000) {
			throw new FileValidationFailureException("File size is exceeding configured limit of 500kb");
		}
		String originalFileName = multipartFile.getOriginalFilename();
		try {
			String contentType = multipartFile.getContentType();
			return dataBucketService.uploadFile(originalFileName, multipartFile.getBytes(), contentType);
		} catch (IOException e) {
			log.error("exception occured while uploading file", e);
			throw new GcpFileUploadException(e.getMessage());
		}
	}

	public List<FileDto> listFiles() {
		return dataBucketService.listFiles();
	}

}
