package com.henrique.crud.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.henrique.crud.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findAll(Sort sort);
}
