package com.sensei.poc.bean.payload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileJSON extends JSONPayload {
	
	String file = null;
	
	public FileJSON( String fileLocation ) {
		byte[] loadedFile = null;
		
		try {
			loadedFile = loadFile( fileLocation );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		byte[] encoded = Base64.getEncoder().encode( loadedFile );
		file = new String( encoded );  
	}
	
	private byte[] loadFile( String fileLocation ) throws IOException {
	    InputStream is = Files.newInputStream( Paths.get( fileLocation ) );

	    File file = new File( fileLocation );
	    
	    long length = file.length();
	    if ( length > Integer.MAX_VALUE ) {
	        throw new IllegalArgumentException( "File uploaded is too large" );
	    }
	    
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while ( offset < bytes.length
	           && ( numRead=is.read( bytes, offset, bytes.length-offset) ) >= 0 ) {
	        offset += numRead;
	    }

	    if ( offset < bytes.length ) {
	        throw new IOException( "Could not completely read file " + file.getName());
	    }

	    is.close();
	    
	    return bytes;
	}
	
	public String getFile() {
		return file;
	}
}
