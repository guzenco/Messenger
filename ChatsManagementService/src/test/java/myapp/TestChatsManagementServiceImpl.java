package myapp;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import myapp.objects.AccessPolicyLogic;
import myapp.objects.AccessPolicyLogicImpl;
import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.JsonConverter;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;
import myapp.services.AuthServiceRemote;
import myapp.services.ManagementService;
import myapp.services.ManagementServiceImpl;

public class TestChatsManagementServiceImpl {

	@Spy
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@Spy
	Converter conventer = new JsonConverter();
	
	@Mock
	ChatsRepository cr;
	
	
	@Mock
	AuthServiceRemote as;
	
	@InjectMocks
	ManagementService ms = new ManagementServiceImpl();
	
	User actor = new User();
	
	@Before
	public void setUp() throws Exception {
		actor.setGroup(1);
		MockitoAnnotations.openMocks(this);
		Mockito.when(as.authentication(0, null, 0)).thenReturn(null);
		Mockito.when(as.authentication(1, null, 0)).thenReturn(actor);
	}


	@Test
	public void testPostChat() {
		assertEquals(ms.postChat(0, null, 0, "test"), null);
		Mockito.when(cr.postChat(any())).thenReturn(true);
		assertEquals(ms.postChat(1, null, 0, "test"), "{\"id\":-1,\"name\":\"test\",\"messages\":[],\"users\":[]}");
	}

	@Test
	public void testPutChat() {
		Chat c = new Chat("test");
		c.setId(1);
		assertEquals(ms.putChat(0, null, 0, 1, "test123"), null);
		Mockito.when(cr.getChat(1)).thenReturn(c);
		Mockito.when(cr.putChat(c)).thenReturn(true);
		assertEquals(ms.putChat(1, null, 0, 1, "test123"), "{\"id\":1,\"name\":\"test123\",\"messages\":[],\"users\":[]}");
	}

	@Test
	public void testDeleteChat() {
		Chat c = new Chat("test");
		c.setId(1);
		Mockito.when(cr.getChat(1)).thenReturn(c);
		Mockito.when(cr.deleteChat(c)).thenReturn(true);
		assertEquals(ms.deleteChat(0, null, 0, 1), null);
		assertEquals(ms.deleteChat(1, null, 0, 1), "{\"id\":1,\"name\":\"test\",\"messages\":[],\"users\":[]}");
	}

	@Test
	public void testPostUserInChat() {
		User u = new User();
		u.setId(2);
		Chat c = new Chat("test");
		c.setId(1);
		Mockito.when(cr.getChat(1)).thenReturn(c);
		Mockito.when(cr.putChat(c)).thenReturn(true);
		assertEquals(ms.postUserInChat(0, null, 0, 1, 2), null);
		assertEquals(ms.postUserInChat(1, null, 0, 1, 2), "{\"id\":1,\"name\":\"test\",\"messages\":[],\"users\":[2]}");
	}

	@Test
	public void testDeleteUserInChat() {
		User u = new User();
		u.setId(2);
		Chat c = new Chat("test");
		c.setId(1);
		c.addUser(u.getId());
		Mockito.when(cr.getChat(1)).thenReturn(c);
		Mockito.when(cr.putChat(c)).thenReturn(true);
		assertEquals(ms.deleteUserInChat(0, null, 0, 1, 2), null);
		assertEquals(ms.deleteUserInChat(1, null, 0, 1, 2), "{\"id\":1,\"name\":\"test\",\"messages\":[],\"users\":[]}");
	}

}
