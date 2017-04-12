package com.educonnect.common.serializer;

import com.educonnect.common.bean.Bean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class handles the serializing of Java object beans into strings which 
 * are then sent over the network. It follows the EduConnect protocol while 
 * serializing the beans.</p>
 * 
 * The serializer serializes the Header flag of the bean as a generic string whereas 
 * the payload is serialized as a JSON string with Pretty printing enabled for 
 * easier debugging.
 * 
 * @author Sensei
 *
 */
public class Serializer {
	
	/**
	 * The static method which handles the serialization of a bean to a string which 
	 * can readily be sent over the connection. 
	 * 
	 * @param bean The bean object to be serialized
	 * @return A serialized bean in the form of a String
	 */
	public static String serialize( Bean bean ) {
		StringBuilder serializedBean = new StringBuilder();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		serializedBean.append( "HEADER=" + bean.getHeader().toString() + ";\n" );
		serializedBean.append( "PAYLOAD=" + gson.toJson( bean.getPayload() ) );
		
		return serializedBean.toString();
	}
}
