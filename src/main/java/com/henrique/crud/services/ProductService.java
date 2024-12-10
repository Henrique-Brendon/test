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
import com.henrique.crud.entities.enums.SectorType;
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
	    return execute(() -> {
	        List<Product> products = productDTOs.stream()
	                .map(this::convertProductDtoToProduct)
	                .collect(Collectors.toList());
	        return repository.saveAll(products); // Salva a lista inteira de uma vez
	    }, "Error saving products");
	}

    
    public Product update(Long id, ProductDTO productDTO) {
    	return execute(() -> {
    		Product entity = repository.findById(id).orElseThrow(() ->
    			new ServiceException("Product not found with ID: " + id)
			);
    		updateData(entity, productDTO);
    		return repository.save(entity);
    	}, "Error updating product with ID:" + id);
    	
    }
	
    private List<ProductDTO> convertProductDTO(List<Product> listProducts) {
        List<ProductDTO> listProductsDTO = new ArrayList<>();

        listProducts.forEach(product -> {
            // Mapeamento para o tipo correto de 'sector' (SectorType)
            SectorType sectorType = (product.getSector() != null) ? product.getSector().getName() : SectorType.DEFAULT;

            // Constrói o DTO com o sector correto (agora do tipo 'SectorType')
            listProductsDTO.add(new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCharacteristics(),
                formatCurrency(product.getCost()),
                formatCurrency(product.getPrice()),
                formatDate(product.getDateEntry()),
                formatDate(product.getDateExit()),
                sectorType, // Agora passa o SectorType diretamente
                product.getListCode().getCode()
            ));
        });

        return listProductsDTO;
    }

	
	private String formatCurrency(BigDecimal valueBigDecimal) {
	    if (valueBigDecimal == null) {
	        throw new IllegalArgumentException("Currency value cannot be null");
	    }
	    DecimalFormat fmt = new DecimalFormat("0.00 'R$'");
	    return fmt.format(valueBigDecimal);
	}

	private String formatDate(Instant date) {
	    if (date == null) {
	        throw new IllegalArgumentException("Date cannot be null");
	    }
	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
	    return fmt.format(date);
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
    
    private void updateData(Product entity, ProductDTO productDTO) {
        try {
            entity.setName(productDTO.name());
            entity.setCharacteristics(productDTO.characteristics());
            entity.setCost(new BigDecimal(productDTO.cost().trim()));
            entity.setPrice(new BigDecimal(productDTO.price().trim()));
            entity.setDateEntry(formatInstant(productDTO.dateEntry()));
            entity.setDateExit(formatInstant(productDTO.dateExit()));
            entity.setSector(new Sector().mapSector(productDTO.name()));
            entity.setListCode(new ListCode(productDTO.name()));
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Invalid data for updating product: " + productDTO.name(), e);
        }
    }
	
	private Instant formatInstant(String date) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate localDate = LocalDate.parse(date, fmt);
		return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}
	
}
