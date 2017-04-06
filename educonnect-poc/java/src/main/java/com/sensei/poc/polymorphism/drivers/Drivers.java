package com.sensei.poc.polymorphism.drivers;

public class Drivers {
	
	private int resistance = -1;
	private int number     = -1;
	private int diameter   = -1;
	
	public Drivers( int resistance, int number, int diameter ) {
		this.resistance   = resistance;
		this.number       = number;
		this.diameter     = diameter;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Driver resistance  = " + resistance + "\n" );
		builder.append( "no. of drivers     = " + number     + "\n" );
		builder.append( "diameter of driver = " + diameter   + "\n" ); 
		
		return builder.toString();
	}

}
