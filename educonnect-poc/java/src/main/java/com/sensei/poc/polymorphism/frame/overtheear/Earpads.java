package com.sensei.poc.polymorphism.frame.overtheear;

public class Earpads {
	
	public enum EarpadMaterial {
		VELOUR, LEATHER, MEMORYFOAM, FOAM
	}
	
	private EarpadMaterial material = null;
	
	public Earpads( EarpadMaterial material ) {
		this.material = material;
	}
	
	public EarpadMaterial getMaterial() {
		return material;
	}
}
