package com.sensei.poc.bean.header;

public enum BeanHeader {
	
	LOGIN, AUTH, NACK, TEXT, FILE;
	
	@Override
	public String toString() {
		return "HEADER=" + super.toString();
	}
}
