package com.educonnect.client.network.dummyserver;

import java.io.BufferedReader;

import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;

public class DummyClientReceiver implements Runnable{

	
	private DummyClientModel cModel = null;
	private BufferedReader reader = null;
	
	public DummyClientReceiver( BufferedReader reader, DummyClientModel cModel ) {
		this.cModel = cModel;
		this.reader = reader;
	}
	
	@Override
	public void run() {
		
		Payload p = null;
		try {
			String s = reader.readLine();
			System.out.println( s );
			p = Parser.parse( s );
			
			while( !( p instanceof ShutdownPayload ) ) {
				cModel.receive( s );
				s = reader.readLine();
				p = Parser.parse( s );
			}
			
			reader.close();
			cModel.shutDown();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
