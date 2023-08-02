package com.ccp.dto;

import com.ccp.entity.Customer;
import com.ccp.entity.Retailer;
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
public class TransactionResDTO implements Serializable {
    Long id;
    Customer customer;
    Retailer retailer;
    Integer amount;
    Timestamp timestamp;
}