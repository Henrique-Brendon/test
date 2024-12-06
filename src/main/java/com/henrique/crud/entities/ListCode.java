package com.henrique.crud.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListCode {

	private Long id;
	private String code;
	
	private List<Product> listProducts = new ArrayList<>();
	
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
