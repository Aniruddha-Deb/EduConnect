package com.educonnect.common.bean.payload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FilePayload extends Payload {
	
	private String base64EncodedFile = null;
	
	public FilePayload( String filepath ) {
		byte[] loadedFile = null;
		
		try {
			loadedFile = loadFile( filepath );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		byte[] encoded = Base64.getEncoder().encode( loadedFile );
		base64EncodedFile = new String( encoded );  
	}

	private byte[] loadFile( String filepath ) throws IOException {
	    InputStream is = Files.newInputStream( Paths.get( filepath ) );

	    File file = new File( filepath );
	    
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

	public File getFile() {
		File file = null;
		try {
			file = File.createTempFile( "payload", ".file" );
			OutputStream os = Files.newOutputStream( Paths.get( file.getAbsolutePath() ) );
			byte[] decoded = Base64.getDecoder().decode( base64EncodedFile );
			os.write( decoded );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return file;
	}
}
