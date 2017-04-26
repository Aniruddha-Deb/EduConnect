package com.educonnect.common.message.payload;

import com.educonnect.common.message.Bean;

/**
 * A payload is a generic class which serves as the parent of all types of payload 
 * carried by a {@link Bean}. It does not contain any state or behaviour of it's 
 * own, allowing all the state and behaviour to be implemented by it's child 
 * classes</p> 
 * 
 * The payload classes serve as a template for parsing and serializing the data 
 * being passed over the socket into JSON strings.
 * 
 * @author Sensei
 *
 */
public class Payload {

}
