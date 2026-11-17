package com.johnakins.bankapp.customerservice.repository;

import java.util.Optional;

import com.johnakins.bankapp.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByExternalId(String externalId);
    boolean existsByEmail(String email);
}