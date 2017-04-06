package com.sensei.poc.polymorphism;

import com.sensei.poc.polymorphism.connector.WiredConnector;
import com.sensei.poc.polymorphism.connector.WiredConnector.Jack;
import com.sensei.poc.polymorphism.drivers.Drivers;
import com.sensei.poc.polymorphism.frame.inear.Earbud;
import com.sensei.poc.polymorphism.frame.inear.InEarFrame;
import com.sensei.poc.polymorphism.manufacturer.HeadphoneManufacturer;
import com.sensei.poc.polymorphism.power.PowerSource;

public class WiredInEarHeadphone extends Headphone<WiredInEarHeadphone> {

	public WiredInEarHeadphone( HeadphoneManufacturer manufacturer, 
								String model,
								Drivers drivers,
								Jack jack, int wireLengthInMetres,
								Earbud earbud, int weightInGrams,
								PowerSource power ) {
		super.manufacturer = manufacturer;
		super.model = model;
		super.drivers = drivers;
		super.connector = new WiredConnector( jack, wireLengthInMetres );
		super.frame = new InEarFrame( weightInGrams, earbud );
		super.powerSource = power;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder( super.toString() + "\n" );
		builder.append( ((InEarFrame)super.frame).toString() + "\n" );
		builder.append( ((WiredConnector)super.connector).toString() + "\n" );
		builder.append( super.getDrivers().toString() + "\n" );
		
		if( powerSource != null ) {
			builder.append( super.powerSource.toString() );
		}
		builder.append( "------------------------------------\n" );
		
		return builder.toString();
	}

}
