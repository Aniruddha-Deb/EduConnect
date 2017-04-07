package com.sensei.poc.polymorphism.frame.overtheear;

import com.sensei.poc.polymorphism.frame.Frame;

public class OverTheEarFrame extends Frame<OverTheEarFrame> {
	
	private Headband      headband = null;
	private Earpads       earpads  = null;
	private DriverHousing housing  = null;
	
	public OverTheEarFrame() {
		super.withWeight( 200 );
		this.headband = new Headband();
		this.earpads  = new Earpads();
		this.housing  = new DriverHousing();
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
	
	public OverTheEarFrame withHeadband( Headband h ) {
		this.headband = h;
		return this;
	}
	
	public OverTheEarFrame withEarpads( Earpads e ) {
		this.earpads = e;
		return this;
	}
	
	public OverTheEarFrame withHousing( DriverHousing d ) {
		this.housing = d;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Headphone has an over-the-ear form factor\n" );
		builder.append( "	weight = " + super.weightInGrams + " g\n" );
		builder.append( "	headband material = " + headband.getMaterial().toString().toLowerCase() + "\n" ); 
		builder.append( "	earpad material   = " + earpads.getMaterial().toString().toLowerCase() + "\n" );
		builder.append( "	driver housing material = " + housing.getMaterial().toString().toLowerCase() + "\n" );
		builder.append( "	driver housing color    = " + housing.getColor().toString() + "\n" ); 
		
		return builder.toString();
	}
	
}
