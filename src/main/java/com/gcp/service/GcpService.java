package com.gcp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.exceptions.GcpInternalFailureException;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketListOption;
import com.google.cloud.storage.StorageOptions;

@Service
public class GcpService {
	@Autowired
	private DataBucketService dataBucketService;

	/**
	 * Returns the list of available buckets
	 * */
	public List<String> getBucketList() {
		try {
			return dataBucketService.getBucketList();
		} catch(Exception ex) {
			throw new GcpInternalFailureException(ex.getMessage());
		}
	}

}
