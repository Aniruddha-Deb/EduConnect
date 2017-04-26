package com.educonnect.common;

import com.google.gson.Gson;

public class JSONEnumTest {

	public enum TestEnum {
		TEST_GSON, TEST_JSON;
	}
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		String s = gson.toJson( TestEnum.TEST_GSON );
		TestEnum t = gson.fromJson( s, TestEnum.class );
		System.out.println( t );
	}

}
