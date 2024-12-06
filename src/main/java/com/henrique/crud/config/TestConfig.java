package com.henrique.crud.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.henrique.crud.entities.Product;
import com.henrique.crud.entities.Sector;
import com.henrique.crud.repositories.ProductRepository;
import com.henrique.crud.repositories.SectorRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		
		List<Product> listProducts = Arrays.asList(
			    new Product(null, "RX 560", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 570", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 580", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "RX 590", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    
			    new Product(null, "MOUSE REDRAGON", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "MOUSE REDRAGON", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "KEYBOARD REDRAGON", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "KEYBOARD REDRAGON", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    
			    new Product(null, "MICROONDAS", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "MICROONDAS", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
			    new Product(null, "REFRIGERATOR", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null)	    
		);
		
		List<Sector> listSectors = new ArrayList<>();

		for (Product product : listProducts) {
		    // Obtém o setor correspondente ao produto
		    Sector sector = new Sector().mapSector(product.getName());
		    
		    // Associa o setor ao produto
		    product.setSector(sector);

		    // Adiciona o produto na lista de produtos do setor
		    sector.getListProducts().add(product);

		    // Garante que o setor está na lista para ser salvo posteriormente
		    if (!listSectors.contains(sector)) {
		        listSectors.add(sector);
		    }
		}
		
		sectorRepository.saveAll(listSectors);
		productRepository.saveAll(listProducts);
		
	}

}
