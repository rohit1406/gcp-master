package com.gcp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
	private String message;
}
