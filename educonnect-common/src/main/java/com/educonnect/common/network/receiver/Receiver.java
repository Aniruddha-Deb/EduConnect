package com.educonnect.common.network.receiver;

/**
 * The receiver interface is an interface which needs to be implemented by the classes
 * which need to receive data over the socket. A receiver thread caters to the 
 * task of receiving all the data sent over the socket and sending it to the 
 * NetworkAdapter which contains it.
 * 
 * @author Sensei
 *
 */
public interface Receiver extends Runnable {

}
