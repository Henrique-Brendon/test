package com.henrique.crud.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henrique.crud.entities.enums.SectorType;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Sector implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "listCode")
	private SectorType type;
	
	private List<Product> listProducts = new ArrayList<>();
	
	public Sector(SectorType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SectorType getName() {
		return type;
	}

	public void setName(SectorType type) {
		this.type = type;
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
		Sector other = (Sector) obj;
		return Objects.equals(id, other.id);
	}
	
}
