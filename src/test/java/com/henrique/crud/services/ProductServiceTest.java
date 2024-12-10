package com.henrique.crud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henrique.crud.dtos.ProductDTO;
import com.henrique.crud.entities.Product;
import com.henrique.crud.entities.enums.SectorType;
import com.henrique.crud.repositories.ProductRepository;
import com.henrique.crud.services.exceptions.ServiceException;

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
    	//BDDMockito.given(repository.save(ArgumentMatchers.any(Product.class))).willReturn(new Product());
    }
    
    @Test
    void testFindById_Success() {
    	when(repository.findById(1L)).thenReturn(Optional.of(listProducts.get(0)));
    	
    	Product result = service.findById(1L);
    	
        assertEquals(1L, result.getId());
        assertEquals(listProducts.get(0).getName(), result.getName());
    }
    
    @Test
    void testFindById_NotFound() {
    	when(repository.findById(1L)).thenReturn(Optional.empty());
    	
    	assertThrows(ServiceException.class, () -> service.findById(1L));
    }
    
    @Test
    void testInsertSuccess() {
    	// CONFIGURAÇÃO: Define o comportamento simulado do repositório ao salvar um produto.
        // Quando o método 'save' for chamado com qualquer instância de 'Product', ele retornará o primeiro item da lista 'listProducts'.
        when(repository.save(any(Product.class))).thenReturn(listProducts.get(0));
        // EXECUÇÃO: Chama o método 'insert' no serviço passando o primeiro item da lista 'listProductsDTOs'.
        // Este é o método que está sendo testado.
        Product result = service.insert(listProductsDTOs.get(0));
        // VERIFICAÇÃO: Compara o nome do produto retornado com o nome do DTO original.
        // Garante que o método 'insert' converteu e salvou o produto corretamente.
        assertEquals(listProductsDTOs.get(0).name(), result.getName());
        // Validação de outros campos 
        assertEquals(listProductsDTOs.get(0).characteristics(), result.getCharacteristics());
        assertEquals(new BigDecimal(listProductsDTOs.get(0).cost()), result.getCost());
        // VERIFICAÇÃO: Verifica se o método 'save' do repositório foi chamado exatamente uma vez com qualquer instância de 'Product'.
        verify(repository, times(1)).save(any(Product.class));
    }
    
    @Test
    void testInsertWithInvalidData() {
    	ProductDTO invalidProductDTO = new ProductDTO(null, null, "", "invalid-cost", "20.00", "10/12/2023", "11/12/2023", null, "Code1");
    	assertThrows(ServiceException.class, () -> {
    		service.insert(invalidProductDTO);
    	});
    	//assertEquals("Error inserting product", exception.getMessage());
    	//assertTrue(exception.getMessage().contains("Error inserting product"));
    }
 
    void startList() {
		listProducts = Arrays.asList(
			    new Product(1L, "RX 560", "", new BigDecimal("200.00"), new BigDecimal("300.00"), Instant.now(), Instant.now(), null, null),
			    new Product(2L, "RX 570", "", new BigDecimal("300.00"), new BigDecimal("40.00"), Instant.now(), Instant.now(), null, null),
			    new Product(3L, "RX 580", "", new BigDecimal("350.00"), new BigDecimal("450.00"), Instant.now(), Instant.now(), null, null),
			    new Product(4L, "RX 590", "", new BigDecimal("400.00"), new BigDecimal("500.00"), Instant.now(), Instant.now(), null, null)	    
		);
		
		listProductsDTOs = Arrays.asList(
			    new ProductDTO(1L, "RX 560", "", "200.00", "300.00", "02/05/2019", "22/05/2019", SectorType.HARDWARE, "teste"),
			    new ProductDTO(2L, "RX 570", "", "300.00", "40.00", "15/02/2019", "28/03/2029", SectorType.HARDWARE, "teste"),
			    new ProductDTO(3L, "RX 580", "", "350.00", "450.00", "12/08/2020", "20/08/2020", SectorType.HARDWARE, "teste"),
			    new ProductDTO(4L, "RX 590", "", "400.00", "500.00", "15/07/2020", "13/08/2020", SectorType.HARDWARE, "teste")	    
		);
    }

}
