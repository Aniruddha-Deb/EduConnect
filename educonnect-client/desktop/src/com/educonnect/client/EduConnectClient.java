package com.educonnect.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.educonnect.common.beans.LoginBean;
import com.google.gson.Gson;

public class EduConnectClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket s = new Socket( "127.0.0.1" , 1132 );
		PrintWriter pw = new PrintWriter( s.getOutputStream() );
		LoginBean loginBean = new LoginBean( 11, "Hiii", null );
		Gson gson = new Gson();
		
		String loginString = gson.toJson( loginBean );
		
		pw.println( loginString );
		pw.flush();
				
		s.close();
	}
}
