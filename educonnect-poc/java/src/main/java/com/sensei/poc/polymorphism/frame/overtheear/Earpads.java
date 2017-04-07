package com.sensei.poc.polymorphism.frame.overtheear;

public class Earpads {
	
	public enum EarpadMaterial {
		VELOUR, LEATHER, MEMORYFOAM, FOAM
	}
	
	private EarpadMaterial material = null;
	
	public Earpads() {
		this.material = EarpadMaterial.FOAM;
	}
	
	public EarpadMaterial getMaterial() {
		return material;
	}
	
	public Earpads withMaterial( EarpadMaterial e ) {
		this.material = e;
		return this;
	}
}
