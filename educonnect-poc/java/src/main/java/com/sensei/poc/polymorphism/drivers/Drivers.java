package com.sensei.poc.polymorphism.drivers;

public class Drivers {
	
	private int resistance = -1;
	private int number     = -1;
	private int diameter   = -1;
	
	public Drivers() {
		this.resistance   = 24;
		this.number       = 2;
		this.diameter     = 35;
	}
	
	public int getResistance() {
		return resistance;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getDiameter() {
		return diameter;
	}
	
	public Drivers withResistance( int r ) {
		this.resistance = r;
		return this;
	}
	
	public Drivers withNumber( int number ) {
		this.number = number;
		return this;
	}
	
	public Drivers withDiameter( int diameter ) {
		this.diameter = diameter;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Driver resistance  = " + resistance + " Ω\n" );
		builder.append( "no. of drivers     = " + number     + "\n" );
		builder.append( "diameter of driver = " + diameter   + " mm\n" ); 
		
		return builder.toString();
	}

}
