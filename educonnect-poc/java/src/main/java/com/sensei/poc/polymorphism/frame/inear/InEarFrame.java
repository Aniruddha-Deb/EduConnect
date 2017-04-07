package com.sensei.poc.polymorphism.frame.inear;

import com.sensei.poc.polymorphism.frame.Frame;
import com.sensei.poc.polymorphism.frame.inear.Earbud.EarbudMaterial;
import com.sensei.poc.polymorphism.frame.inear.Earbud.Size;

public class InEarFrame extends Frame<InEarFrame> {
	
	public Earbud earbud = null;

	public InEarFrame() {
		super.withWeight( 100 );
		this.earbud = new Earbud( EarbudMaterial.SILICONE, Size.SMALL );
	}
	
	public int getWeightInGrams() {
		return weightInGrams;
	}
	
	public Earbud getEarbud() {
		return earbud;
	}
		
	public InEarFrame withEarbud( EarbudMaterial material, Size size ) {
		this.earbud = new Earbud( material, size );
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Headphone has an in-ear form factor\n" );
		builder.append( "	weight          = " + weightInGrams + " g\n");
		builder.append( "	earbud material = " + earbud.getMaterial().toString().toLowerCase() + "\n");
		builder.append( "	earbud size     = " + earbud.getSize().toString().toLowerCase() + "\n");
		
		return builder.toString();
	}
}
