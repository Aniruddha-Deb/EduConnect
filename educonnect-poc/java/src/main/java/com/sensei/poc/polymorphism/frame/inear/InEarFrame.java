package com.sensei.poc.polymorphism.frame.inear;

import com.sensei.poc.polymorphism.frame.Frame;

public class InEarFrame extends Frame {
	
	public Earbud earbud = null;

	public InEarFrame( int weightInGrams, Earbud earbud ) {
		super.weightInGrams = weightInGrams;
		this.earbud = earbud;
	}
	
	public int getWeightInGrams() {
		return weightInGrams;
	}
	
	public Earbud getEarbud() {
		return earbud;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Frame = In ear\n" );
		builder.append( "	weight (in grams) = " + weightInGrams + "\n");
		builder.append( "	earbud material   = " + earbud.getMaterial().toString().toLowerCase() + "\n");
		builder.append( "	earbud size       = " + earbud.getSize().toString().toLowerCase() + "\n");
		
		return builder.toString();
	}
}
