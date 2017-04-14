package com.educonnect.common.engine;

import com.educonnect.common.bean.payload.Payload;

public abstract class Engine {
	
	public abstract void updateUI( Payload p );
	
	public abstract void start();
	
	public abstract void shutdown();
	
}
