package com.sensei.poc.polymorphism.connector;

public class WirelessConnector extends Connector {
	
	public enum ConnectionProtocol {
		WIFI, BLUETOOTH_3, BLUETOOTH_4, BLUETOOTH_BLE, FM, AM;
	}
	
	private ConnectionProtocol protocol = null;
	
	public WirelessConnector( ConnectionProtocol protocol ) {
		this.protocol = protocol;
	}
	
	public ConnectionProtocol getProtocol() {
		return protocol;
	}

}
