package com.henrique.crud.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.henrique.crud.dtos.ProductDTO;
import com.henrique.crud.entities.ListCode;
import com.henrique.crud.entities.Product;
import com.henrique.crud.entities.Sector;
import com.henrique.crud.repositories.ProductRepository;
import com.henrique.crud.services.exceptions.ServiceException;

@Service
public class ProductService extends BaseService{

	@Autowired
	private ProductRepository repository;
	
	public List<ProductDTO> findAll() {
		return convertProductDTO(repository.findAll());
	}
	
	public List<ProductDTO> getAllProductsSort(String campo, String direcao) {
		Sort.Direction direction = "DESC".equalsIgnoreCase(direcao) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Sort sort = Sort.by(direction, campo);
		
		return convertProductDTO(repository.findAll(sort));
	}
	
	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ServiceException("Product not found with ID: " + id));
	}
	
    public void deleteById(Long id) {
        execute(() -> repository.deleteById(id), "Error deleting product with id: " + id);
    }
	
   public Product insert(ProductDTO productDTO) {
        return execute(() -> repository.save(convertProductDtoToProduct(productDTO)), "Error inserting product");
    }
	
    public List<Product> insertProducts(List<ProductDTO> productDTOs) {
        List<Product> products = productDTOs.stream()
                .map(this::convertProductDtoToProduct)
                .collect(Collectors.toList());
        return repository.saveAll(products); // Salva a lista inteira de uma vez
    }
	
	private List<ProductDTO> convertProductDTO(List<Product> listProducts) {
		List<ProductDTO> listProductsDTO = new ArrayList<>();
		
		listProducts.forEach(product -> {
			listProductsDTO.add(new ProductDTO(product.getId(), product.getName(), product.getCharacteristics(),
				formatCurrency(product.getCost()), formatCurrency(product.getPrice()), formatDate(product.getDateEntry()),
				formatDate(product.getDateExit()), product.getSector().getName(), product.getListCode().getCode()));
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
	
    private Product convertProductDtoToProduct(ProductDTO productDTO) {
        return new Product(
            null,
            productDTO.name(),
            productDTO.characteristics(),
            new BigDecimal(productDTO.cost().trim()),
            new BigDecimal(productDTO.price().trim()),
            formatInstant(productDTO.dateEntry()),
            formatInstant(productDTO.dateExit()),
            new Sector().mapSector(productDTO.name()),
            new ListCode(productDTO.listCode())
        );
    }
	
	private Instant formatInstant(String date) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate localDate = LocalDate.parse(date, fmt);
		return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}
	
}
