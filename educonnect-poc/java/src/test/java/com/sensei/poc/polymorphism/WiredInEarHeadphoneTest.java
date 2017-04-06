package com.sensei.poc.polymorphism;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.polymorphism.connector.WiredConnector;
import com.sensei.poc.polymorphism.connector.WiredConnector.Jack;
import com.sensei.poc.polymorphism.drivers.Drivers;
import com.sensei.poc.polymorphism.frame.inear.Earbud;
import com.sensei.poc.polymorphism.frame.inear.Earbud.EarbudMaterial;
import com.sensei.poc.polymorphism.frame.inear.Earbud.Size;
import com.sensei.poc.polymorphism.frame.inear.InEarFrame;
import com.sensei.poc.polymorphism.frame.overtheear.Headband.HeadbandMaterial;
import com.sensei.poc.polymorphism.manufacturer.HeadphoneManufacturer;

public class WiredInEarHeadphoneTest {

	private Headphone headphone = null;
	private static final Logger log = LogManager.getLogger( WiredInEarHeadphoneTest.class );
	
	public static void main( String[] args ) {
		new WiredInEarHeadphoneTest().runTest();
	}
	
	public void runTest() {
		assembleHeadphone();
		outputHeadphoneAttributes();
	}

	private void outputHeadphoneAttributes() {
		if( headphone instanceof WiredInEarHeadphone ) {			
			log.debug( ((WiredInEarHeadphone)headphone).toString() );
		}
		else {
			System.out.println( "Not a wired in ear headphone" );
		}
	}

	private void assembleHeadphone() {
		headphone = new WiredInEarHeadphone( HeadphoneManufacturer.BOSE, 
											 "SoundSport II",
											 new Drivers( 
													 4, 
													 2, 
													 5 ), 
											 Jack.MIC_305MM, 
											 1, 
											 new Earbud( 
													 EarbudMaterial.FOAM, 
													 Size.SMALL ), 
											 143, 
											 null );
		headphone.withConnector( new WiredConnector().withLength(2) )
		         .withFrame( new InEarFrame( 143, 
		        		 	 new Earbud( EarbudMaterial.SILICONE, Size.SMALL ) ) ;
		         
	}
}
