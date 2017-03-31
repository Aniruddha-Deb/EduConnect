package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.beans.Bean;
import com.educonnect.common.beans.ImageBean;
import com.educonnect.common.beans.LoginBean;
import com.educonnect.common.beans.OKBean;
import com.educonnect.common.beans.StatusBean;
import com.educonnect.common.beans.TextBean;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	private static BufferedReader br = null;
	private static PrintWriter    pw = null;
	
	private static Socket s = null;
	
	public static void handle( Socket s ) throws IOException {
		
		ClientHandler.s = s;
		br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		pw = new PrintWriter( s.getOutputStream() );

		Bean b = null;
		
		try {
			b = receive();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		System.out.println( b.getData() );
		System.out.println( b.getHeader().toString() );
	}
	
	public static Bean receive() throws Exception {
		
		ObjectInputStream ois = new ObjectInputStream( s.getInputStream() );
		Bean b = (Bean)ois.readObject();
		ois.close();		
		return b;
	}

}
