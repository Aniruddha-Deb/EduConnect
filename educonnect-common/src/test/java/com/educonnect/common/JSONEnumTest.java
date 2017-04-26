package com.educonnect.common;

import com.google.gson.Gson;

public class JSONEnumTest {

	public enum TestEnum {
		TEST_GSON, TEST_JSON;
	}
	
	public static void main( String[] args ) {
		Gson gson = new Gson();
		String s = gson.toJson( new JSONEnumTest.GSONOBject().getE() );
		System.out.println( s );
		TestEnum t = gson.fromJson( s, TestEnum.class );
		System.out.println( t );
	}

	static class GSONOBject {
		TestEnum e = null;
				
		public GSONOBject() {
			e = TestEnum.TEST_GSON;
		}
		
		public TestEnum getE() {
			return e;
		}
	}
}
