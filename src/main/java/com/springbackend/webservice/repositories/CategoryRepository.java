package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
