package com.sensei.poc.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.polymorphism.Headphone;
import com.sensei.poc.polymorphism.WiredInEarHeadphone;
import com.sensei.poc.polymorphism.connector.WiredConnector;
import com.sensei.poc.polymorphism.drivers.Drivers;
import com.sensei.poc.polymorphism.frame.inear.InEarFrame;
import com.sensei.poc.polymorphism.manufacturer.HeadphoneManufacturer;

public class SerializerTest {
	
	public static final Logger log = LogManager.getLogger( SerializerTest.class );
	
	public static void main( String[] args ) {

		Headphone<WiredInEarHeadphone> headphone = new WiredInEarHeadphone()
												   .withManufacturer( HeadphoneManufacturer.BOSE )
												   .withModel( "SoundSport II" )
												   .withConnector( new WiredConnector() )
												   .withDrivers( new Drivers() )
												   .withFrame( new InEarFrame() );
		log.debug( JSONSerializer.serialize( headphone ) );
	}

}
