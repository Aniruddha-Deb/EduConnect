package com.educonnect.common.message;

import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.FilePayload;

/**
 * A FileBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending files across the connection. By default, it sets the 
 * header to {@link Header#FILE} and the payload to a {@link FilePayload}, 
 * which is created based on the filepath passed to the constructor.  
 * 
 * @author Sensei
 */
public class FileBean extends Bean {
	
	/**
	 * The standard constructor for creating a FileBean
	 * 
	 * @param filepath The path of the file which is to be converted into a bean. 
	 */
	public FileBean( String filepath ) {
		super( Header.FILE, new FilePayload( filepath ) );
	}
}
