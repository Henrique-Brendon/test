package com.henrique.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.henrique.crud.dtos.ProductDTO;
import com.henrique.crud.entities.Product;
import com.henrique.crud.services.ProductService;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    // Endpoint para buscar todos os produtos
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);  // Retorna os produtos em formato JSON
    }

    @GetMapping("/filteredProducts")
    public ResponseEntity<List<ProductDTO>> getAllProductsSort(@RequestParam String campo, @RequestParam String direction) {
        List<ProductDTO> products = productService.getAllProductsSort(campo, direction);
        return ResponseEntity.ok(products);  // Retorna os produtos em formato JSON
    }
    
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ProductDTO productDTO) {
        productService.insert(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna status 201 Created
    }
    
    @PostMapping("/bulk-insert")
    public List<Product> insertProducts(@RequestBody List<ProductDTO> productDTOs) {
        return productService.insertProducts(productDTOs);
    }
}