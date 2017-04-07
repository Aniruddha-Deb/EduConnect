package com.sensei.poc.polymorphism.connector;

public class WiredConnector extends Connector {
	
	public enum Jack {
		MONO_305MM, STEREO_305MM, MIC_305MM, MONO_603MM, STEREO_603MM;
		
		public String toString() { 
			switch ( this ) {
			case MONO_305MM: return "3.5mm mono";
			case STEREO_305MM: return "3.5mm stereo";
			case MIC_305MM: return "3.5mm stereo with mic";
			case MONO_603MM: return "6.3mm mono";
			case STEREO_603MM: return "6.3mm stereo";

			default: return null;
			}
		};
	}
	
	private Jack jack = null;
	private int wireLengthInMetres = -1;

	public WiredConnector() {
		this.jack = Jack.MIC_305MM ;
		this.wireLengthInMetres = 1 ;
	}
	
	public WiredConnector withJack( Jack jack ) {
		this.jack = jack ;
		return this ;
	}
	
	public WiredConnector withLength( int l ) {
		this.wireLengthInMetres = l ;
		return this ;
	}
	
	public Jack getJack() {
		return jack;
	}
	
	public int getWireLengthInMetres() {
		return wireLengthInMetres;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Headphone is a wired type\n" );
		builder.append( "	Jack type   = " + jack.toString() + "\n");
		builder.append( "	wire length = " + wireLengthInMetres + " m\n");
		
		return builder.toString();
	}

}
