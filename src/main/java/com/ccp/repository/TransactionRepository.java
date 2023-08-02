package com.ccp.repository;

import com.ccp.entity.Customer;
import com.ccp.entity.Retailer;
import com.ccp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Override
    Optional<Transaction> findById(Long id);
    List<Transaction> findAllByCustomer(Customer customer);
    List<Transaction> findAllByCustomerAndTimestampBetween(Customer customer, Timestamp from, Timestamp to);
    @Override
    void deleteById(Long id);
}