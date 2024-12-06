package com.henrique.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henrique.crud.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
