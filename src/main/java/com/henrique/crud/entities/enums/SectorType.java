package com.henrique.crud.entities.enums;

public enum SectorType {

	DEFAULT(0),
	HARDWARE(1),
	ELETRONICS(2),
	PERIPHERALS(3),
	SMARTPHONE(4);
	
	private int code;
	
	private SectorType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static SectorType valueOf(int code) {
		for(SectorType value: SectorType.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Error! Invalid Sector");
	}
	
}
