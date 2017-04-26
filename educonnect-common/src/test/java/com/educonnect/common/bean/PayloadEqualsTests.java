package com.educonnect.common.bean;

import static com.educonnect.common.bean.BeanTests.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.payload.*;

public class PayloadEqualsTests {

	@Test
	public void loginPayloadTest() {
		
		LoginPayload loginPayload = new LoginPayload( EMAIL_ID, PASSWORD.toCharArray(), ClientType.STUDENT );
		assertThat( loginPayload, equalTo( loginPayload ) );
		
		LoginPayload nullPayload = null;
		assertThat( loginPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, not( equalTo( loginPayload ) ) );
		assertThat( textPayload, equalTo( textPayload ) );

		LoginPayload duplicateLoginPayload = new LoginPayload( EMAIL_ID, PASSWORD.toCharArray(), ClientType.STUDENT );
		assertThat( duplicateLoginPayload, equalTo( loginPayload ) );
		assertThat( loginPayload, equalTo( duplicateLoginPayload ) );
	}

	@Test
	public void authPayloadTest() {
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, equalTo( authPayload ) );
		
		AuthPayload nullPayload = null;
		assertThat( authPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, not( equalTo( authPayload ) ) );
		assertThat( textPayload, equalTo( textPayload ) );

		AuthPayload duplicateAuthPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( duplicateAuthPayload, equalTo( authPayload ) );
		assertThat( authPayload, equalTo( duplicateAuthPayload ) );
	}

	@Test
	public void textPayloadTest() {
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, equalTo( textPayload ) );
		
		TextPayload nullPayload = null;
		assertThat( textPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, not( equalTo( textPayload ) ) );
		assertThat( authPayload, equalTo( authPayload ) );

		TextPayload duplicateTextPayload = new TextPayload( SENDER, TEXT );
		assertThat( duplicateTextPayload, equalTo( textPayload ) );
		assertThat( textPayload, equalTo( duplicateTextPayload ) );
	}
	
	@Test
	public void filePayloadTest() {
		
		FilePayload filePayload = new FilePayload( IN_FILE_PATH );
		assertThat( filePayload, equalTo( filePayload ) );
		
		FilePayload nullPayload = null;
		assertThat( filePayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, not( equalTo( filePayload ) ) );
		assertThat( textPayload, equalTo( textPayload ) );

		FilePayload duplicateAuthPayload = new FilePayload( IN_FILE_PATH );
		assertThat( duplicateAuthPayload, equalTo( filePayload ) );
		assertThat( filePayload, equalTo( duplicateAuthPayload ) );
	}
	
	@Test
	public void failPayloadTest() {
		
		FailPayload failPayload = new FailPayload( FAIL_CAUSE );
		assertThat( failPayload, equalTo( failPayload ) );
		
		FailPayload nullPayload = null;
		assertThat( failPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, not( equalTo( failPayload ) ) );
		assertThat( authPayload, equalTo( authPayload ) );

		FailPayload duplicateFailPayload = new FailPayload( FAIL_CAUSE );
		assertThat( duplicateFailPayload, equalTo( failPayload ) );
		assertThat( failPayload, equalTo( duplicateFailPayload ) );
	}

	@Test
	public void infoPayloadTest() {
		
		InfoPayload infoPayload = new InfoPayload( INFO );
		assertThat( infoPayload, equalTo( infoPayload ) );
		
		InfoPayload nullPayload = null;
		assertThat( infoPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, not( equalTo( infoPayload ) ) );
		assertThat( authPayload, equalTo( authPayload ) );

		InfoPayload duplicateInfoPayload = new InfoPayload( INFO );
		assertThat( duplicateInfoPayload, equalTo( infoPayload ) );
		assertThat( infoPayload, equalTo( duplicateInfoPayload ) );
	}

	@Test
	public void registerPayloadTest() {
		
		RegisterPayload registerPayload = new RegisterPayload( CLASS, SECTION, ROLLNO, EMAIL_ID, PASSWORD );
		assertThat( registerPayload, equalTo( registerPayload ) );
		
		RegisterPayload nullPayload = null;
		assertThat( registerPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, not( equalTo( registerPayload ) ) );
		assertThat( authPayload, equalTo( authPayload ) );

		RegisterPayload duplicateRegisterPayload = new RegisterPayload( CLASS, SECTION, ROLLNO, EMAIL_ID, PASSWORD );
		assertThat( duplicateRegisterPayload, equalTo( registerPayload ) );
		assertThat( registerPayload, equalTo( duplicateRegisterPayload ) );
	}

	@Test
	public void shutdownPayloadTest() {
		
		ShutdownPayload shutdownPayload = new ShutdownPayload();
		assertThat( shutdownPayload, equalTo( shutdownPayload ) );
		
		TextPayload nullPayload = null;
		assertThat( shutdownPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		AuthPayload authPayload = new AuthPayload( AUTH_TOKEN );
		assertThat( authPayload, not( equalTo( shutdownPayload ) ) );
		assertThat( authPayload, equalTo( authPayload ) );

		ShutdownPayload duplicateShutdownPayload = new ShutdownPayload();
		assertThat( duplicateShutdownPayload, equalTo( shutdownPayload ) );
		assertThat( shutdownPayload, equalTo( duplicateShutdownPayload ) );
	}
}
