package com.gcp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

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
	
	@Autowired
	private DataSource dataSource;

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
	
	public boolean testConnection() {
		try (
			Connection conn = dataSource.getConnection();){
			String stmt = "select * from Persons;";
			try(PreparedStatement statement = conn.prepareStatement(stmt);){
				boolean res = statement.execute();
				log.info("statement executed {}", res);
				return true;
			}	
		} catch (SQLException e) {
			log.error("connection to gcp failed", e);
		}
		return false;
	}

}
