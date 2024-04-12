package com.gcp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.gcp.dto.FileDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketListOption;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataBucketService {
	@Value("${gcp.config.file:gcp-account-config.json}")
	private String gcpAccountConfigFile;
	
	@Value("${gcp.project.id:project-job-420017}")
	private String gcpProjectId;
	
	@Value("${gcp.bucket.it:rsm-file-storage}")
	private String gcpBucketId;
	
	@Value("${gcp.directory.name:}")
	private String gcpDirectoryName;
	
	private Storage gcpStorage;
	
	@PostConstruct
	public void init() {
		log.info("initializing GCP storage");
		try {
			InputStream gcpAccountConfigStream = new ClassPathResource(gcpAccountConfigFile).getInputStream();
			StorageOptions storageOptions = StorageOptions.newBuilder().setProjectId(gcpProjectId)
					.setCredentials(GoogleCredentials.fromStream(gcpAccountConfigStream)).build();
			gcpStorage = storageOptions.getService();
		} catch (IOException e) {
			log.error("Exception occured while connecting to gcp storage", e);
		}
	}

	public List<String> getBucketList() {
		log.info("Fetching bucket lists from google cloud");
		return gcpStorage.list(BucketListOption.pageSize(100))
		.streamValues().map(bucket -> bucket.getName()).collect(Collectors.toList());
	}

	public FileDto uploadFile(String originalFileName, byte[] fileContent, String contentType) throws IOException {
		log.info("Start file uploading process on GCS: {}, {}", originalFileName, contentType);
		Bucket bucket = gcpStorage.get(gcpBucketId, Storage.BucketGetOption.fields());
		String id = UUID.randomUUID().toString();
		String fileName = id + "." + com.google.common.io.Files.getFileExtension(originalFileName);
		Blob blob = bucket.create(fileName, fileContent,
				contentType);
		return FileDto.builder().name(fileName).url(blob.getMediaLink()).contentType(contentType).build();
	}

	public List<FileDto> listFiles() {
		Bucket bucket = gcpStorage.get(gcpBucketId, Storage.BucketGetOption.fields());
		return bucket.list().streamValues().map(blob -> FileDto.builder().name(blob.getName())
				.contentType(blob.getContentType()).url(blob.getMediaLink()).build()).collect(Collectors.toList());
	}

}
