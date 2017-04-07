package com.sensei.poc.polymorphism.connector;

public class WirelessConnector extends Connector {
	
	public enum ConnectionProtocol {
		WIFI, BLUETOOTH_3, BLUETOOTH_4, BLUETOOTH_BLE, FM, AM;
	}
	
	private ConnectionProtocol protocol = null;
	
	public WirelessConnector() {
		this.protocol = ConnectionProtocol.BLUETOOTH_BLE;
	}
	
	public ConnectionProtocol getProtocol() {
		return protocol;
	}
	
	public WirelessConnector withConnector( ConnectionProtocol cp ) {
		this.protocol = cp;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Headphone is a wireless type\n" );
		builder.append( "	connection protocol = " + protocol.toString().toLowerCase() + "\n" );
		
		return builder.toString();
	}

}
