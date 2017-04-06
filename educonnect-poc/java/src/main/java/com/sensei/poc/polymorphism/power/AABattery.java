package com.sensei.poc.polymorphism.power;

public class AABattery extends PowerSource {
	
	private static final double AA_VOLTAGE = 1.5;
	
	public AABattery() {
		super.voltage = AA_VOLTAGE;
	}
	
	public double getVoltage() {
		return super.voltage;
	}

}
