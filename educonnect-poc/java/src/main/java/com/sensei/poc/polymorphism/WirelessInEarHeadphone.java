package com.sensei.poc.polymorphism;

import com.sensei.poc.polymorphism.connector.WirelessConnector;
import com.sensei.poc.polymorphism.frame.inear.InEarFrame;

public class WirelessInEarHeadphone extends Headphone<WirelessInEarHeadphone>{

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder( super.toString() + "\n" );
		builder.append( ((InEarFrame)super.frame).toString() + "\n" );
		builder.append( ((WirelessConnector)super.connector).toString() + "\n" );
		builder.append( super.getDrivers().toString() + "\n" );
		
		if( powerSource != null ) {
			builder.append( super.powerSource.toString() );
		}
		builder.append( "------------------------------------\n" );
		
		return builder.toString();
	}

}
