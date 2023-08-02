package com.ccp.controller;

import com.ccp.assembler.DataAssembler;
import com.ccp.dto.CPReqDtoAll;
import com.ccp.dto.CPReqDtoBetween;
import com.ccp.dto.CPReqDtoMonth;
import com.ccp.dto.TransactionReqDTO;
import com.ccp.entity.Customer;
import com.ccp.entity.Retailer;
import com.ccp.entity.Transaction;
import com.ccp.repository.CustomerRepository;
import com.ccp.repository.RetailerRepository;
import com.ccp.repository.TransactionRepository;
import com.ccp.util.DateUtil;
import com.ccp.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final RetailerRepository retailerRepository;
    private final DataAssembler dataAssembler;

    public TransactionController(TransactionRepository transactionRepository, CustomerRepository customerRepository, RetailerRepository retailerRepository, DataAssembler dataAssembler) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.retailerRepository = retailerRepository;
        this.dataAssembler = dataAssembler;
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long transId) {
        Optional<Transaction> res = transactionRepository.findById(transId);
        if(res.isEmpty()) {
            return ResponseUtil.badRequest("Invalid transaction id");
        }
        return ResponseUtil.successResponse(dataAssembler.toDto(res.get()));
    }

    @PostMapping("/getCPBetween")
    public ResponseEntity<Object> getCPBetween(@RequestBody CPReqDtoBetween info) {
        if(customerRepository.findById(info.getCustomerId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid customer id");
        }
        Customer customer = customerRepository.findById(info.getCustomerId()).get();
        Timestamp from = new Timestamp(info.getFrom().getTime());
        Timestamp to = new Timestamp(info.getTo().getTime());
        List<Transaction> transactionList = transactionRepository.findAllByCustomerAndTimestampBetween(customer, from, to);
        int mark = 0;
        for(Transaction transaction: transactionList) {
            if(transaction.getAmount() >= 100 ) {
                mark += (transaction.getAmount() - 100) * 2 + 50;
            }
        }
        return ResponseUtil.successResponse(mark);
    }

    @PostMapping("/getCPAll")
    public ResponseEntity<Object> getCPAll(@RequestBody CPReqDtoAll info) {
        if(customerRepository.findById(info.getCustomerId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid customer id");
        }
        Customer customer = customerRepository.findById(info.getCustomerId()).get();
        List<Transaction> transactionList = transactionRepository.findAllByCustomer(customer);
        int mark = 0;
        for(Transaction transaction: transactionList) {
            if(transaction.getAmount() >= 100 ) {
                mark += (transaction.getAmount() - 100) * 2 + 50;
            }
        }
        return ResponseUtil.successResponse(mark);
    }

    @PostMapping("/getCPMonth")
    public ResponseEntity<Object> getCPMonth(@RequestBody CPReqDtoMonth info) {
        if(customerRepository.findById(info.getCustomerId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid customer id");
        }
        Customer customer = customerRepository.findById(info.getCustomerId()).get();

        Timestamp from = new Timestamp(DateUtil.getFirstDayOfMonth(info.getDate()).getTime());
        Timestamp to = new Timestamp(DateUtil.getFirstDateOfNextMonth(info.getDate()).getTime());

        List<Transaction> transactionList = transactionRepository.findAllByCustomerAndTimestampBetween(customer, from, to);
        int mark = 0;
        for(Transaction transaction: transactionList) {
            if(transaction.getAmount() >= 100 ) {
                mark += (transaction.getAmount() - 100) * 2 + 50;
            }
        }
        return ResponseUtil.successResponse(mark);
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(TransactionReqDTO transactionReqDTO) {
        Transaction transaction = new Transaction();
        if(customerRepository.findById(transactionReqDTO.getCustomerId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid customer id");
        }
        Customer customer = customerRepository.findById(transactionReqDTO.getCustomerId()).get();
        if(retailerRepository.findById(transactionReqDTO.getRetailerId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid customer id");
        }
        Retailer retailer = retailerRepository.findById(transactionReqDTO.getRetailerId()).get();
        transaction.setCustomer(customer);
        transaction.setRetailer(retailer);
        transaction.setAmount(transactionReqDTO.getAmount());
        transaction.setTimestamp(Timestamp.from(Instant.now()));
        return ResponseUtil.successResponse(dataAssembler.toDto(transactionRepository.save(transaction)));
    }

    @PutMapping
    public ResponseEntity<Object> updateTransaction(TransactionReqDTO transactionReqDTO) {
        if(transactionReqDTO.getId() == null) return ResponseUtil.badRequest("Invalid transaction id");
        if(transactionRepository.findById(transactionReqDTO.getId()).isEmpty()) {
            return ResponseUtil.badRequest("Invalid transaction id");
        }
        Transaction transaction = transactionRepository.findById(transactionReqDTO.getId()).get();
        if(transactionReqDTO.getCustomerId() != null) {
            if (customerRepository.findById(transactionReqDTO.getCustomerId()).isEmpty()) {
                return ResponseUtil.badRequest("Invalid customer id");
            }
            Customer customer = customerRepository.findById(transactionReqDTO.getCustomerId()).get();
            transaction.setCustomer(customer);

        }
        if(transactionReqDTO.getRetailerId() != null) {
            if (retailerRepository.findById(transactionReqDTO.getRetailerId()).isEmpty()) {
                return ResponseUtil.badRequest("Invalid customer id");
            }
            Retailer retailer = retailerRepository.findById(transactionReqDTO.getRetailerId()).get();
            transaction.setRetailer(retailer);
        }
        transaction.setTimestamp(Timestamp.from(Instant.now()));
        return ResponseUtil.successResponse(dataAssembler.toDto(transactionRepository.save(transaction)));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long transId) {
        Optional<Transaction> res = transactionRepository.findById(transId);
        if(res.isEmpty()) {
            return ResponseUtil.badRequest("Invalid transation id");
        }
        transactionRepository.deleteById(transId);
        return ResponseUtil.successResponse("Successfully deleted");
    }

}
