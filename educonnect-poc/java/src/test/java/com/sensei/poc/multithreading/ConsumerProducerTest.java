package com.sensei.poc.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerProducerTest {

	public static void main( String[] args ) {
		
		System.out.println( "Starting execution test" );
		
		BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
		
		Producer p = new Producer( messages );
		Consumer c = new Consumer( messages );
		
		Thread producerThread = new Thread( p );
		Thread consumerThread = new Thread( c );
		
		producerThread.start();
		consumerThread.start();
	}

}
