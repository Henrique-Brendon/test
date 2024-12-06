package com.henrique.crud.services;

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
				product.getCost(), product.getPrice(), product.getDateEntry(), product.getDateExit(),
				product.getSector(), product.getListCode()));
		});
		
		return listProductsDTO;
	}
}
