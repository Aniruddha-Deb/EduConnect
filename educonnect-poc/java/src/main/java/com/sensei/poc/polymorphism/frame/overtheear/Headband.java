package com.sensei.poc.polymorphism.frame.overtheear;

public class Headband {
	
	public enum HeadbandMaterial {
		STEEL, PLASTIC;
	}
	
	private HeadbandMaterial material = null;
	
	public Headband( HeadbandMaterial material ) {
		this.material = material;
	}
	
	public HeadbandMaterial getMaterial() {
		return material;
	}
}
