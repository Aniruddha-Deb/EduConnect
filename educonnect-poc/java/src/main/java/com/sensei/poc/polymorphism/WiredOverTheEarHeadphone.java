package com.sensei.poc.polymorphism;

import com.sensei.poc.polymorphism.connector.WiredConnector;
import com.sensei.poc.polymorphism.frame.overtheear.OverTheEarFrame;

public class WiredOverTheEarHeadphone extends Headphone<WiredOverTheEarHeadphone>{
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder( super.toString() + "\n" );
		builder.append( ((OverTheEarFrame)super.frame).toString() + "\n" );
		builder.append( ((WiredConnector)super.connector).toString() + "\n" );
		builder.append( super.getDrivers().toString() + "\n" );
		
		if( powerSource != null ) {
			builder.append( super.powerSource.toString() );
		}
		builder.append( "------------------------------------\n" );
		
		return builder.toString();
	}

}
