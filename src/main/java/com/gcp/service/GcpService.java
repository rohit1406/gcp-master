package com.gcp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.dto.BucketNameDto;
import com.gcp.exceptions.GcpInternalFailureException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GcpService {
	@Autowired
	private DataBucketService dataBucketService;

	/**
	 * Returns the list of available buckets
	 * */
	public BucketNameDto getBucketList() {
		try {
			return BucketNameDto.builder().bucketNames(dataBucketService.getBucketList()).build();
		} catch(Exception ex) {
			log.error("Exception occured while getting bucket list", ex);
			throw new GcpInternalFailureException(ex.getMessage());
		}
	}

}
