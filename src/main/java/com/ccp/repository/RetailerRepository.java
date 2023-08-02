package com.ccp.repository;

import com.ccp.entity.Customer;
import com.ccp.entity.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    @Override
    Optional<Retailer> findById(Long id);
}