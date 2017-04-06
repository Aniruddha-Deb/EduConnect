package com.sensei.poc.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsumerProducerTest {

	private static final Logger log = LogManager.getLogger( ConsumerProducerTest.class );
	
	public static void main( String[] args ) {

		log.info( "Starting execution test" );
		
		BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
		
		Producer p = new Producer( messages );
		Consumer c = new Consumer( messages );
		
		Thread producerThread = new Thread( p, "Producer" );
		Thread consumerThread = new Thread( c, "Consumer" );
		
		producerThread.start();
		consumerThread.start();
	}

}
