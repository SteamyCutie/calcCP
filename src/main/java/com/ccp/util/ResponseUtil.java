package com.ccp.util;

import com.ccp.dto.RequestResponse;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ResponseUtil {
    public static ResponseEntity<Object> badRequest(Object data) {
        return ResponseEntity.ok().body(RequestResponse.builder().code(400)
                .result(data)
                .status(400)
                .timestamp(Instant.now().getEpochSecond())
                .build());
    }

    public static ResponseEntity<Object> successResponse(Object data) {
        return ResponseEntity.ok().body(RequestResponse.builder().code(200)
                .result(data)
                .status(200)
                .timestamp(Instant.now().getEpochSecond())
                .build());
    }
}