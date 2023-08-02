package com.ccp.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CPReqDtoAll implements Serializable {
    Long customerId;
}