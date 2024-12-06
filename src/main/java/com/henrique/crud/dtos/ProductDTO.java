package com.henrique.crud.dtos;

import java.math.BigDecimal;
import java.time.Instant;

import com.henrique.crud.entities.ListCode;
import com.henrique.crud.entities.Sector;

public record ProductDTO(
	Long id,
	String name,
	String characteristics,
	BigDecimal cost,
	BigDecimal price,
	Instant dateEntry,
	Instant dateExit,
	Sector sector,
	ListCode listCode) {

}
