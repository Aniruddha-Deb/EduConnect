package com.sensei.poc.polymorphism;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.polymorphism.connector.WiredConnector;
import com.sensei.poc.polymorphism.connector.WirelessConnector;
import com.sensei.poc.polymorphism.drivers.Drivers;
import com.sensei.poc.polymorphism.frame.inear.InEarFrame;
import com.sensei.poc.polymorphism.frame.overtheear.DriverHousing;
import com.sensei.poc.polymorphism.frame.overtheear.DriverHousing.DriverHousingMaterial;
import com.sensei.poc.polymorphism.frame.overtheear.Earpads;
import com.sensei.poc.polymorphism.frame.overtheear.Earpads.EarpadMaterial;
import com.sensei.poc.polymorphism.frame.overtheear.Headband;
import com.sensei.poc.polymorphism.frame.overtheear.Headband.HeadbandMaterial;
import com.sensei.poc.polymorphism.frame.overtheear.OverTheEarFrame;
import com.sensei.poc.polymorphism.manufacturer.HeadphoneManufacturer;

public class HeadphoneTest {

	private Headphone<WiredInEarHeadphone> wiredInEarHeadphone = null;
	private Headphone<WiredOverTheEarHeadphone> wiredOverTheEarHeadphone = null;
	private Headphone<WirelessInEarHeadphone> wirelessInEarHeadphone = null;
	private Headphone<WirelessOverTheEarHeadphone> wirelessOverTheEarHeadphone = null;
	private static final Logger log = LogManager.getLogger( HeadphoneTest.class );
	
	public static void main( String[] args ) {
		new HeadphoneTest().runTest();
	}
	
	public void runTest() {
		assembleHeadphone();
		outputHeadphoneAttributes();
	}

	private void outputHeadphoneAttributes() {
		log.debug( ((WiredInEarHeadphone)wiredInEarHeadphone).toString() );
		log.debug( ((WiredOverTheEarHeadphone)wiredOverTheEarHeadphone).toString() );
		log.debug( ((WirelessInEarHeadphone)wirelessInEarHeadphone).toString() );
		log.debug( ((WirelessOverTheEarHeadphone)wirelessOverTheEarHeadphone).toString() );
	}

	private void assembleHeadphone() {
		wiredInEarHeadphone = new WiredInEarHeadphone()
							 .withManufacturer( HeadphoneManufacturer.BOSE )
							 .withModel( "SoundSport II" )
							 .withConnector( new WiredConnector().withLength(2) )
		         			 .withFrame( new InEarFrame().withWeight( 143 ) )  
		         			 .withDrivers( new Drivers().withDiameter( 10 ) );
		
		wiredOverTheEarHeadphone = new WiredOverTheEarHeadphone()
								   .withManufacturer( HeadphoneManufacturer.BEATS )
								   .withModel( "Studio 3" )
								   .withConnector( new WiredConnector().withLength( 1 ) )
								   .withDrivers( new Drivers().withDiameter( 35 ) )
								   .withFrame( new OverTheEarFrame().withEarpads( new Earpads().withMaterial( EarpadMaterial.MEMORYFOAM ) )
										   							.withHeadband( new Headband().withMaterial( HeadbandMaterial.STEEL ) )
										   							.withHousing( new DriverHousing().withMaterial( DriverHousingMaterial.STEEL ) ) )
								   ;
		wirelessOverTheEarHeadphone = new WirelessOverTheEarHeadphone()
									  .withManufacturer( HeadphoneManufacturer.BOSE )
									  .withConnector( new WirelessConnector() )
									  .withFrame( new OverTheEarFrame() )
									  .withModel( "SoundTrue wireless" )
									  .withDrivers( new Drivers().withResistance( 12 ) );
		
		wirelessInEarHeadphone = new WirelessInEarHeadphone()
								 .withManufacturer( HeadphoneManufacturer.APPLE )
								 .withModel( "AirPods" )
								 .withConnector( new WirelessConnector() )
								 .withDrivers( new Drivers().withDiameter( 5 ) )
								 .withFrame( new InEarFrame().withWeight( 159 ) );
		
	}
}
