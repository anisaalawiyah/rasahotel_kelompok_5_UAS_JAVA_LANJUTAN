package com.javaproject.rasahotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaproject.rasahotel.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("delete from Customer where email=:email")
    void deleteByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
