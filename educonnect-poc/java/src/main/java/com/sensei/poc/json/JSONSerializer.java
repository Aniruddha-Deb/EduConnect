package com.sensei.poc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sensei.poc.bean.payload.JSONPayload;

public class JSONSerializer {
	
	private static Gson gsonSerializer = new GsonBuilder().setPrettyPrinting().create();

	public static String serialize( JSONPayload payload ) {
		return gsonSerializer.toJson( payload );
	}
}
