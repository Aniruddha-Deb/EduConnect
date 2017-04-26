package com.educonnect.common.message;

import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.TextPayload;

/**
 * A TextBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending text messages across the connection. By default, it 
 * sets the header to {@link Header#TEXT} and the payload to a {@link TextPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class TextBean extends Bean {
	
	/**
	 * The standard constructor for creating a TextBean
	 * 
	 * @param sender A string which contains either the name or roll number of 
	 * the sender of the text
	 * @param text The content of the text sent.
	 */
	public TextBean( String sender, String text ) {
		super( Header.TEXT, new TextPayload( sender, text ) );
	}
}
