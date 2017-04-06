package com.sensei.poc.polymorphism.frame.overtheear;

import com.sensei.poc.polymorphism.frame.Frame;

public class OverTheEarFrame extends Frame {
	
	private Headband      headband = null;
	private Earpads       earpads  = null;
	private DriverHousing housing  = null;
	
	public OverTheEarFrame( int weightInGrams, Headband headband, 
						    Earpads earpads, DriverHousing housing ) {
		super.weightInGrams = weightInGrams;
		this.headband = headband;
		this.earpads  = earpads;
		this.housing  = housing;
	}
	
	public int getWeightInGrams() {
		return super.weightInGrams;
	}
	
	public Headband getHeadband() {
		return headband;
	}

	public Earpads getEarpads() {
		return earpads;
	}
	
	public DriverHousing getHousing() {
		return housing;
	}
}
