package com.educonnect.common.bean.payload;

import java.util.Arrays;

public class DatabasePayload extends Payload {
	
	private String[]   headers = null;
	private String[][] data    = null;

	public DatabasePayload( String[] headers, String[][] data ) {
		this.headers = headers;
		this.data    = data;
	}
	
	public String[] getHeaders() {
		return headers;
	}
	
	public String[][] getData() {
		return data;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) return true;
		if( obj == null ) return false;
		if( obj.getClass() == this.getClass() && obj.hashCode() == this.hashCode() ) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		
		int headersHash = headers == null ? 97 : Arrays.hashCode( headers );
		int dataHash = 0;
		
		for( String[] row : data ) {
			int rowHash = row == null ? 83 : Arrays.hashCode( row );
			dataHash += rowHash;
		}
		
		return headersHash + dataHash;
	}

}
