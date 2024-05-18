package myapp;

import static org.junit.Assert.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import myapp.objects.AuthManager;
import myapp.objects.AuthManagerImpl;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;
import myapp.services.AuthService;
import myapp.services.AuthServiceImpl;


public class TestAuthServiceImpl {

	@Mock
	UsersRepository ur;
	
	@Spy
	AuthManager am = new AuthManagerImpl();
	
	@InjectMocks
	private AuthService as = new AuthServiceImpl();
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAuthentication() {
		User u = new User("n", "l", "p");
		long stime = System.currentTimeMillis();
		String password = DigestUtils.sha1Hex(u.getPassword() + stime);
		
		Mockito.when(ur.getUser(u.getLogin())).thenReturn(u);
		
		String res = as.authorization(u.getLogin(), password, stime);

		JsonObjectBuilder jb = Json.createObjectBuilder();
		jb.add("id", u.getId());
		jb.add("token", u.getToken());
		assertEquals(res, jb.build().toString());
		
		stime += 1;
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(res, null);
		
		stime = System.currentTimeMillis() - 5001;
		password = DigestUtils.sha1Hex(u.getPassword() + stime);
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(res, null);
		
		stime = System.currentTimeMillis();
		password = DigestUtils.sha1Hex("123" + stime);
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(res, null);
	}

	@Test
	public void testAuthorization() {
		User u = new User("n", "l", "p");
		u.setToken("test1245");
		long stime = System.currentTimeMillis();
		String token = DigestUtils.sha1Hex(u.getToken() + stime);
		
		Mockito.when(ur.getUser(u.getId())).thenReturn(u);
		
		User res = as.authentication(u.getId(), token, stime);
		assertEquals(res, u);
		
		
		stime += 1;
		res = as.authentication(u.getId(), token, stime);
		assertEquals(res, null);
		
		stime = System.currentTimeMillis() - 5001;
		token = DigestUtils.sha1Hex(u.getPassword() + stime);
		res = as.authentication(u.getId(), token, stime);
		assertEquals(res, null);
		
		stime = System.currentTimeMillis();
		token = DigestUtils.sha1Hex("123" + stime);
		res = as.authentication(u.getId(), token, stime);
		assertEquals(res, null);
	}

}
