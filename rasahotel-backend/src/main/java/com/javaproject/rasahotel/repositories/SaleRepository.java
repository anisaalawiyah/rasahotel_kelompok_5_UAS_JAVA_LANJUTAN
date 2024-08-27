package com.javaproject.rasahotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.rasahotel.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, String> {

}
