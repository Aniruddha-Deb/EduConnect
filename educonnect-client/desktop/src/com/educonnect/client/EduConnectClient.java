package com.educonnect.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EduConnectClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket s = new Socket( "127.0.0.1" , 1132 );
		PrintWriter pw = new PrintWriter( s.getOutputStream() );
		pw.println( "000 > ROLLNO=11" );
		pw.flush();
		System.out.println( "Started" );
		
		BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		String read = br.readLine();		
		System.out.println( read );
		
		s.close();
	}
}
