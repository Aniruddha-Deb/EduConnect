package com.sensei.poc.polymorphism;

import com.sensei.poc.polymorphism.connector.Connector;
import com.sensei.poc.polymorphism.drivers.Drivers;
import com.sensei.poc.polymorphism.frame.Frame;
import com.sensei.poc.polymorphism.manufacturer.HeadphoneManufacturer;
import com.sensei.poc.polymorphism.power.PowerSource;

@SuppressWarnings("unchecked")
public abstract class Headphone<T extends Headphone> {
	
	protected HeadphoneManufacturer manufacturer = null;
	protected String       model        = null;
	protected Connector    connector    = null;
	protected Drivers      drivers      = null;
	protected Frame        frame        = null;
	protected PowerSource  powerSource  = null;
	
	public HeadphoneManufacturer getManufacturer() {
		return manufacturer;
	}
	
	public String getModel() {
		return model;
	}
	
	public Connector getConnector() {
		return connector;
	}
	
	public Drivers getDrivers() {
		return drivers;
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public PowerSource getPowerSource() {
		return powerSource;
	}
	
	public T withManufacturer( HeadphoneManufacturer manufacturer ) {
		this.manufacturer = manufacturer;
		return (T)this;
	}
	
	public T withModel( String model ) {
		this.model = model;
		return (T)this;
	}
	
	public T withConnector( Connector c ) {
		this.connector = c ;
		return (T)this ;
	}
	
	public T withFrame( Frame f ) {
		this.frame = f ;
		return (T)this ;
	}
	
	public T withDrivers( Drivers d ) {
		this.drivers = d;
		return (T)this;
	}
	
	public T withPowerSource( PowerSource p ) {
		this.powerSource = p;
		return (T)this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append( "\n------------------------------------\n" );
		builder.append( "manufacturer = " + manufacturer.toString().toLowerCase() + "\n");
		builder.append( "model        = " + model + "\n" );
		
		return builder.toString();
	}
}
