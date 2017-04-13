package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.LoginJSON;

public class InstanceOfExample {

	public static void main(String[] args) {
		Bean bean = new Bean( BeanHeader.LOGIN, new LoginJSON( 9, 'D', 11 ) );
		LoginBean lBean = new LoginBean( 9, 'D', 11 );
		
		if( bean instanceof LoginBean ) {
			System.out.println( "Bean super!!!!" );
		}
		else {
			System.out.println( "nop!" );
		}
		
		if( lBean instanceof LoginBean ) {
			System.out.println( "LoginBean standard" );
		}
	}
}
