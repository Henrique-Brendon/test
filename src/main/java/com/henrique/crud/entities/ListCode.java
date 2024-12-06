package com.henrique.crud.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Alterado para o pacote correto
import jakarta.persistence.OneToMany;

@Entity
public class ListCode implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String code;
	
	@JsonIgnore
	@OneToMany(mappedBy = "listCode")
	private List<Product> listProducts = new ArrayList<>();
	
	public ListCode() {
		
	}
	
	public ListCode(Long id, String code) {
		this.id = id;
		this.code = code;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<Product> getListProducts() {
		return listProducts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListCode other = (ListCode) obj;
		return Objects.equals(id, other.id);
	}
	
}
