package com.ccp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponse {
    private long timestamp;
    private int status;
    private int code;
    private Object result;
}