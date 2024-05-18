package myapp;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

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
import myapp.services.UsersManagementService;
import myapp.services.UsersManagementServiceImpl;

public class TestUsersManagementServiceImpl {

	@Spy
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@Spy
	Converter conventer = new JsonConverter();
	

	@Mock
	UsersRepository ur;
	
	@Mock
	AuthServiceRemote as;
	
	@InjectMocks
	UsersManagementService ms = new UsersManagementServiceImpl();
	
	User actor = new User("n", "l", "p");
	
	@Before
	public void setUp() throws Exception {
		actor.setGroup(1);
		MockitoAnnotations.openMocks(this);
		Mockito.when(as.authentication(0, null, 0)).thenReturn(null);
		Mockito.when(as.authentication(1, null, 0)).thenReturn(actor);
	}

	@Test
	public void testGetUsers() {
		assertEquals(ms.getUsers(0, null, 0), null);
		List<User> list = new ArrayList<User>();
		list.add(new User("testu", "u1","123456"));
		list.add(new User("user", "u2","123456"));
		Mockito.when(ur.getUsers()).thenReturn(list);
		assertEquals(ms.getUsers(1, null, 0), "[{\"id\":-1,\"name\":\"testu\"},{\"id\":-1,\"name\":\"user\"}]");
	}

	@Test
	public void testPostUser() {
		assertEquals(ms.postUser(0, null, 0, "testu", "u1", "123456"), null);
		Mockito.when(ur.postUser(any())).thenReturn(true);
		assertEquals(ms.postUser(1, null, 0, "testu", "u1", "123456"), "{\"id\":-1,\"name\":\"testu\"}");
	}

	@Test
	public void testPutUser() {
		User u = new User("testu", "u1", "123456");
		u.setId(2);
		assertEquals(ms.putUser(0, null, 0, 2, "testu", "123456"), null);
		Mockito.when(ur.getUser(2)).thenReturn(u);
		Mockito.when(ur.putUser(u)).thenReturn(true);
		assertEquals(ms.putUser(1, null, 0, 2, "utest", "1238456"), "{\"id\":2,\"name\":\"utest\"}");
	}

	@Test
	public void testDeleteUser() {
		User u = new User("testu", "u1", "123456");
		u.setId(2);
		assertEquals(ms.deleteUser(0, null, 0, 2), null);
		Mockito.when(ur.getUser(2)).thenReturn(u);
		Mockito.when(ur.deleteUser(u)).thenReturn(true);
		assertEquals(ms.deleteUser(1, null, 0, 2), "{\"id\":2,\"name\":\"testu\"}");
	}


}
