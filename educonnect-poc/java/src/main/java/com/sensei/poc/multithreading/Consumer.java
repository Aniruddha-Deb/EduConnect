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
public class Consumer implements Runnable {
	
	private BlockingQueue<String> messages = null;
	private static final Logger log = LogManager.getLogger( Consumer.class );
	
	public Consumer( BlockingQueue<String> messages ) {
		this.messages = messages;
	}
	
	public void run() {
		String message = null;
		try {

			// Notice the kiss of death pattern which controls the while loop
			while( true ) {
				message = messages.take();
				if( message.equals( Command.KILL.toString() ) ) {
					log.info( "Received kill command, stopping execution" );
					break;
				}
				Thread.sleep( 100 );
				log.info( "Consumed message " + message );
			}
			
		} catch( InterruptedException ex ) {
			log.error( "InterruptedException thrown", ex );
		}
	}
}