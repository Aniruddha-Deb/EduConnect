package com.sensei.poc.multithreading;

import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a proof-of-concept example of the producer-consumer pattern using 
 * threads. This example uses a BlockingQueue to transfer messages between the 
 * producer and consumer.  
 * 
 * @author Sensei
 *
 */
public class Producer implements Runnable {

	private BlockingQueue<String> messages = null;
	private static final Logger log = LogManager.getLogger( Producer.class );
	
	public Producer( BlockingQueue<String> messages ) {
		this.messages = messages;
	}
	
	public void run() {
		try {
			
			for( int i=0; i<10; i++ ) {
				Thread.sleep( 100 );
				messages.put( Integer.toString( i ) );
				log.info( "Produced messsage " + i );
			}
			log.info( "Putting kill command to kill consumer" );
			messages.put( Command.KILL.toString() );
			
		} catch( InterruptedException ex ) {
			log.error( "InterruptedException thrown", ex );
		}
	}
}