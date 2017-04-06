package com.sensei.poc.polymorphism.frame.inear;

public class Earbud {
	
	public enum EarbudMaterial {
		SILICONE, FOAM
	}
	
	public enum Size {
		SMALL, MEDIUM, LARGE
	}
	
	private EarbudMaterial material = null;
	private Size           size     = null;
	
	public Earbud( EarbudMaterial material, Size size ) {
		this.material = material;
		this.size     = size;
	}
	
	public EarbudMaterial getMaterial() {
		return material;
	}
	
	public Size getSize() {
		return size;
	}
}
