package com.sensei.poc.polymorphism.frame.overtheear;

import java.awt.Color;

public class DriverHousing {
	
	public enum DriverHousingMaterial {
		STEEL, PLASTIC, POLYMER, MESH
	}
	
	private DriverHousingMaterial material = null;
	private Color                 color    = null;

	
	public DriverHousing( DriverHousingMaterial material, Color color ) {
		this.material = material;
		this.color = color;
	}
	
	public DriverHousingMaterial getMaterial() {
		return material;
	}
	
	public Color getColor() {
		return color;
	}
}
