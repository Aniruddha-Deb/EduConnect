package com.sensei.poc.polymorphism.frame.overtheear;

public class Headband {
	
	public enum HeadbandMaterial {
		STEEL, PLASTIC;
	}
	
	private HeadbandMaterial material = null;
	
	public Headband() {
		this.material = HeadbandMaterial.PLASTIC;
	}
	
	public HeadbandMaterial getMaterial() {
		return material;
	}
	
	public Headband withMaterial( HeadbandMaterial m ) {
		this.material = m;
		return this;
	}
}
