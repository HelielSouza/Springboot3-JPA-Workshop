package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.Category;
import com.springbackend.webservice.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
