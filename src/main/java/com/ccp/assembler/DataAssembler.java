package com.ccp.assembler;

import com.ccp.dto.TransactionResDTO;
import com.ccp.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class DataAssembler {
    public TransactionResDTO toDto(Transaction transaction) {
        return TransactionResDTO.builder()
                .id(transaction.getId())
                .customer(transaction.getCustomer())
                .retailer(transaction.getRetailer())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp()).build();
    }
}
