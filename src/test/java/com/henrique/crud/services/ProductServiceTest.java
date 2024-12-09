package com.henrique.crud.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.henrique.crud.dtos.ProductDTO;
import com.henrique.crud.entities.Product;
import com.henrique.crud.entities.enums.SectorType;
import com.henrique.crud.repositories.ProductRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	private List<Product> listProducts; 
	private List<ProductDTO> listProductsDTOs;
    @InjectMocks
    private  ProductService service;

    @Mock
    private ProductRepository repository;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	startList();
    }
    
      
    void startList() {
		listProducts = Arrays.asList(
			    new Product(null, "RX 560", "", new BigDecimal("200.00"), new BigDecimal("300.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 570", "", new BigDecimal("300.00"), new BigDecimal("40.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 580", "", new BigDecimal("350.00"), new BigDecimal("450.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 590", "", new BigDecimal("400.00"), new BigDecimal("500.00"), Instant.now(), Instant.now(), null, null)	    
		);
		
		listProductsDTOs = Arrays.asList(
			    new ProductDTO(null, "RX 560", "", "200.00", "300.00", "02/05/2019", "22/05/2019", SectorType.HARDWARE, "teste"),
			    new ProductDTO(null, "RX 570", "", "300.00", "40.00", "15/02/2019", "28/03/2029", SectorType.HARDWARE, "teste"),
			    new ProductDTO(null, "RX 580", "", "350.00", "450.00", "12/08/2020", "20/08/2020", SectorType.HARDWARE, "teste"),
			    new ProductDTO(null, "RX 590", "", "400.00", "500.00", "15/07/2020", "13/08/2020", SectorType.HARDWARE, "teste")	    
		);
    }

}
