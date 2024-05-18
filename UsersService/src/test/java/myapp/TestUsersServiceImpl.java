package myapp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import myapp.objects.AccessPolicyLogic;
import myapp.objects.AccessPolicyLogicImpl;
import myapp.objects.Converter;
import myapp.objects.JsonConverter;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;
import myapp.services.AuthServiceRemote;
import myapp.services.UsersService;
import myapp.services.UsersServiceImpl;

public class TestUsersServiceImpl {

	@Spy
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@Spy
	Converter conventer = new JsonConverter();
	
	@Mock
	UsersRepository ur;
	
	@Mock
	AuthServiceRemote as;
	
	@InjectMocks
	UsersService is = new UsersServiceImpl();
	
	User actor = new User("n", "l", "p");
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Mockito.when(as.authentication(0, null, 0)).thenReturn(null);
		Mockito.when(as.authentication(1, null, 0)).thenReturn(actor);
	}

	@Test
	public void testGetUsers() {
		assertEquals(is.getUser(0, null, 0, 1), null);
		User u1 = new User("testu", "u1","123456");
		Mockito.when(ur.getUser(1)).thenReturn(u1);
		assertEquals(is.getUser(1, null, 0, 1), "{\"id\":-1,\"name\":\"testu\"}");
	}
	
	@Test
	public void testPutUser() {
		assertEquals(is.putUserPassword(0, null, 0, 2, "123456"), null);
		Mockito.when(ur.getUser(2)).thenReturn(actor);
		Mockito.when(ur.putUser(actor)).thenReturn(true);
		assertEquals(is.putUserPassword(1, null, 0, 2, "1238456"), "{\"id\":-1,\"name\":\"n\"}");
	}
	
}
