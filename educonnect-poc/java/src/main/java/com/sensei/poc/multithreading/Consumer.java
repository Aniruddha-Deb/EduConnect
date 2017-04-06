package com.sensei.poc.multithreading;

import java.util.concurrent.BlockingQueue;

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
	
	public Consumer( BlockingQueue<String> messages ) {
		this.messages = messages;
	}
	
	public void run() {
		String s = null;
		try {

			// Notice the kiss of death pattern which controls the while loop
			while( true ) {
				s = messages.take();
				if( s.equals( Command.KILL.toString() ) ) {
					System.out.println( "Received kill command. stopping execution" );
					break;
				}
				Thread.sleep( 100 );
				System.out.println( "Consumed message " + s );
			}
			
		} catch( InterruptedException ex ) {
			ex.printStackTrace();
		}
	}
}