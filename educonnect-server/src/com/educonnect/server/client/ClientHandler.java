package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.beans.LoginBean;
import com.google.gson.Gson;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	private static BufferedReader br = null;
	private static PrintWriter    pw = null;
	
	public static void handle( Socket s ) throws IOException {
		br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		pw = new PrintWriter( s.getOutputStream() );

		Gson gson = new Gson();
		LoginBean loginBean = gson.fromJson( br, LoginBean.class );
		System.out.println( loginBean.getPasswd() );
		System.out.println( loginBean.getRollNo() );
		System.out.println( loginBean.getHeader() );
		
	}
}
