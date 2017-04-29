package com.educonnect.common.network;

import java.io.BufferedReader;
import java.io.IOException;

import com.educonnect.common.message.core.Message;
import com.educonnect.common.parser.Parser;

public class NetworkUtils {
	
	public static Message readMessage( BufferedReader reader ) {
		String header = read( reader );
		String payload = read( reader );
		Message msg = Parser.parse( header, payload );
		return msg;
	}
	
	private static String read( BufferedReader reader ) {
		String strToRead = null;
		try {
			int headerLength = Integer.parseInt( reader.readLine() );
			strToRead = new String();
			
			for( int i=0; i<headerLength; i++ ) {
				char charRead = (char)reader.read();
				strToRead += charRead;
			}
			// Swallow the last new line character. Prevents the next IO operation 
			// from returning null
			reader.readLine();
		} catch ( NumberFormatException | IOException e ) {
			e.printStackTrace();
		}
		return strToRead;
	}
}
