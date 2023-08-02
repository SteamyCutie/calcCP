package com.ccp.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.bigdeal.doerslab.entity.Bid}
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReqDTO implements Serializable {
    Long id;
    Long customerId;
    Long retailerId;
    Integer amount;
    Timestamp timestamp;
}