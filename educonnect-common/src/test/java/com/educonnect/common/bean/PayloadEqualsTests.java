package com.educonnect.common.bean;

import static com.educonnect.common.bean.BeanTests.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.educonnect.common.bean.payload.AuthPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.TextPayload;

public class PayloadEqualsTests {

	@Test
	public void loginPayloadTest() {
		
		LoginPayload loginPayload = new LoginPayload( CLASS, SECTION, ROLLNO );
		assertThat( loginPayload, equalTo( loginPayload ) );
		
		LoginPayload nullPayload = null;
		assertThat( loginPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, not( equalTo( loginPayload ) ) );
		assertThat( textPayload, equalTo( textPayload ) );

		LoginPayload duplicateLoginPayload = new LoginPayload( CLASS, SECTION, ROLLNO );
		assertThat( duplicateLoginPayload, equalTo( loginPayload ) );
		assertThat( loginPayload, equalTo( duplicateLoginPayload ) );
	}

	@Test
	public void authPayloadTest() {
		
		AuthPayload authPayload = new AuthPayload( EMAIL_ID, PASSWORD );
		assertThat( authPayload, equalTo( authPayload ) );
		
		AuthPayload nullPayload = null;
		assertThat( authPayload, not( equalTo( nullPayload ) ) );
		assertThat( nullPayload, equalTo( nullPayload ) );
		
		TextPayload textPayload = new TextPayload( SENDER, TEXT );
		assertThat( textPayload, not( equalTo( authPayload ) ) );
		assertThat( textPayload, equalTo( textPayload ) );

		AuthPayload duplicateAuthPayload = new AuthPayload( EMAIL_ID, PASSWORD );
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
		
		AuthPayload authPayload = new AuthPayload( EMAIL_ID, PASSWORD );
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
}
