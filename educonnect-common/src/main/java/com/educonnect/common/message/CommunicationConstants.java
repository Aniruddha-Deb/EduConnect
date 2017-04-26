package com.educonnect.common.message;

public class CommunicationConstants {
	
	public static final String DB_HEADER_INFO = "DB headers=";
	
	public static final String NAME_INFO = "Name=";
	
	public static final String REQUEST_TABLE_INFO = "Requesting table ";
	
	public static final String getDBHeaders( String infoPayloadContents ) {
		return infoPayloadContents.substring( DB_HEADER_INFO.length() );
	}
	
	public static final String getName( String infoPayloadContents ) {
		return infoPayloadContents.substring( NAME_INFO.length() );
	}
	
	public static final String getRequestedTable( String infoPayloadContents ) {
		return infoPayloadContents.substring( REQUEST_TABLE_INFO.length() );
	}
}
