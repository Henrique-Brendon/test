package com.henrique.crud.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henrique.crud.entities.enums.SectorType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id; // Alterado para o pacote correto
import jakarta.persistence.OneToMany;

@Entity
public class Sector implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private SectorType type;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sector")
	private List<Product> listProducts = new ArrayList<>();
	
	private static final Map<String, SectorType> PRODUCT_SECTORS = Map.of(
	    "RX 560", SectorType.HARDWARE,
	    "RX 570", SectorType.HARDWARE,
	    "RX 580", SectorType.HARDWARE,
	    "RX 590", SectorType.HARDWARE,
	    "MOUSE REDRAGON", SectorType.PERIPHERALS,
	    "KEYBOARD REDRAGON", SectorType.PERIPHERALS,
	    "MICROONDAS", SectorType.ELETRONICS,
	    "REFRIGERATOR", SectorType.ELETRONICS
	);
	
	private static final EnumMap<SectorType, Long> SECTOR_TYPE_TO_ID = new EnumMap<>(SectorType.class);
	static {
	    SECTOR_TYPE_TO_ID.put(SectorType.DEFAULT, 0L);
	    SECTOR_TYPE_TO_ID.put(SectorType.HARDWARE, 1L);
	    SECTOR_TYPE_TO_ID.put(SectorType.ELETRONICS, 2L);
	    SECTOR_TYPE_TO_ID.put(SectorType.PERIPHERALS, 3L);
	    SECTOR_TYPE_TO_ID.put(SectorType.SMARTPHONE, 4L);
	}
	
	public Sector() {
	
	}
	
	private Sector(SectorType type) {
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
	

	public Sector mapSector(String productName) {
	    return Optional.ofNullable(PRODUCT_SECTORS.get(productName))
	        .map(this::createSector)
	        .orElseGet(this::getDefaultSector);
	}


	private Sector createSector(SectorType sectorType) {
	    Sector sector = new Sector(sectorType);
	    sector.setId(SECTOR_TYPE_TO_ID.get(sectorType));
	    return sector;
	}
	
	private Sector getDefaultSector() {
	    return new Sector(SectorType.DEFAULT);
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
