package com.henrique.crud.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henrique.crud.dtos.ProductDTO;
import com.henrique.crud.entities.Product;
import com.henrique.crud.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<ProductDTO> findAll() {
		return convertProductDTO(repository.findAll());
	}
	
	private List<ProductDTO> convertProductDTO(List<Product> listProducts) {
		List<ProductDTO> listProductsDTO = new ArrayList<>();
		
		listProducts.forEach(product -> {
			listProductsDTO.add(new ProductDTO(product.getId(), product.getName(), product.getCharacteristics(),
				formatCurrency(product.getCost()), formatCurrency(product.getPrice()), formatDate(product.getDateEntry()),
				formatDate(product.getDateExit()), product.getSector().getName(), product.getListCode()));
		});
		
		return listProductsDTO;
	}
	
	private String formatDate(Instant date) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
		
		String formattedDate = fmt.format(date);
		return formattedDate;
	}
	
	private String formatCurrency(BigDecimal valueBigDecimal) {
		DecimalFormat fmt = new DecimalFormat("0.00 'R$'");
		
		String formattedValue = fmt.format(valueBigDecimal);
		return formattedValue;
	}
}
