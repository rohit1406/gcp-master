package com.gcp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {
	private String name;
	private String url;
	private String contentType;
}
