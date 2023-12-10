package myapp;

import static org.junit.Assert.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import myapp.objects.AuthManager;
import myapp.objects.AuthManagerImpl;
import myapp.objects.User;

public class TestAuthManagerImpl {

	AuthManager am = new AuthManagerImpl();
	static User u;
	
	@Before
	public void setUp() throws Exception {
		u = new User();
	}

	@Test
	public void testChechPasword() {
		u.setPassword("test");
		long stime = System.currentTimeMillis();
		String password = DigestUtils.sha1Hex(u.getPassword() + stime);
		assertEquals(am.chechPasword(u, password, stime), true);
		
		assertEquals(am.chechPasword(null, password, stime), false);
		stime += 1;
		assertEquals(am.chechPasword(u, password, stime), false);
		
		stime = System.currentTimeMillis() - 5001;
		password = DigestUtils.sha1Hex(u.getPassword() + stime);
		assertEquals(am.chechPasword(u, password, stime), false);
		
		stime = System.currentTimeMillis();
		password = DigestUtils.sha1Hex("123" + stime);
		assertEquals(am.chechPasword(u, password, stime), false);
	}

	@Test
	public void testChechToken() {
		String token;
		long stime = System.currentTimeMillis();
		
		token = DigestUtils.sha1Hex(u.getToken() + stime);
		assertEquals(am.chechToken(u, token, stime), false);
		
		assertEquals(am.chechToken(null, token, stime), false);
				
		u.setToken("testT");
		token = DigestUtils.sha1Hex(u.getToken() + stime);
		assertEquals(am.chechToken(u, token, stime), true);
		
		stime += 1;
		assertEquals(am.chechToken(u, token, stime), false);
		
		stime = System.currentTimeMillis() - 5001;
		token = DigestUtils.sha1Hex(u.getPassword() + stime);
		assertEquals(am.chechToken(u, token, stime), false);
		
		stime = System.currentTimeMillis();
		token = DigestUtils.sha1Hex("123" + stime);
		assertEquals(am.chechToken(u, token, stime), false);
	}

}
