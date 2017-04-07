package com.sensei.poc.polymorphism.frame.overtheear;

import java.awt.Color;

public class DriverHousing {
	
	public enum DriverHousingMaterial {
		STEEL, PLASTIC, POLYMER, MESH
	}
	
	private DriverHousingMaterial material = null;
	private Color                 color    = null;

	
	public DriverHousing() {
		this.material = DriverHousingMaterial.PLASTIC;
		this.color = new Color( 0, 0, 0 );
	}
	
	public DriverHousingMaterial getMaterial() {
		return material;
	}
	
	public Color getColor() {
		return color;
	}
	
	public DriverHousing withColor( Color c ) {
		this.color = c;
		return this;
	}
	
	public DriverHousing withMaterial( DriverHousingMaterial m ) {
		this.material = m;
		return this;
	}
}
