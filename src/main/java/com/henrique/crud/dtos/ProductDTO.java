package com.henrique.crud.dtos;

import java.math.BigDecimal;
import java.time.Instant;

import com.henrique.crud.entities.ListCode;
import com.henrique.crud.entities.Sector;
import com.henrique.crud.entities.enums.SectorType;

public record ProductDTO(
	Long id,
	String name,
	String characteristics,
	String cost,
	String price,
	String dateEntry,
	String dateExit,
	SectorType sector,
	ListCode listCode) {
}
