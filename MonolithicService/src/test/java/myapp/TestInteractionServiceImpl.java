package myapp;

import static org.junit.Assert.*;

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
import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.JsonConverter;
import myapp.objects.Message;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;
import myapp.services.AuthService;
import myapp.services.InteractionService;
import myapp.services.InteractionServiceImpl;

public class TestInteractionServiceImpl {

	@Spy
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@Spy
	Converter conventer = new JsonConverter();
	
	@Mock
	ChatsRepository cr;
	
	@Mock
	AuthService as;
	
	@InjectMocks
	InteractionService is = new InteractionServiceImpl();
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	User u = new User("n", "l", "p");
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Mockito.when(as.authentication(0, null, 0)).thenReturn(u);
	}

	@Test
	public void testGetChats() {
		List<Chat> chats = new ArrayList<Chat>();
		Chat c1 = new Chat("c1");
		Chat c2 = new Chat("c2");
		c2.addUser(u.getId());
		chats.add(c1);
		chats.add(c2);
		Mockito.when(cr.getChats()).thenReturn(chats);
		assertEquals(is.getChats(0, null, 0), "[{\"id\":-1,\"name\":\"c2\",\"messages\":[],\"users\":[-1]}]");
	}

	@Test
	public void testGetChat() {
		Chat c1 = new Chat("c1");
		Mockito.when(cr.getChat(0)).thenReturn(c1);
		assertEquals(is.getChat(0, null, 0, 0), null);
		c1.addUser(u.getId());
		assertEquals(is.getChat(0, null, 0, 0), "{\"id\":-1,\"name\":\"c1\",\"messages\":[],\"users\":[-1]}");
	}

	@Test
	public void testPostMesssage() {
		Chat c1 = new Chat("c1");
		Mockito.when(cr.getChat(0)).thenReturn(c1);
		assertEquals(is.postMesssage(0, null, 0, 0, "test"), null);
		c1.addUser(u.getId());
		assertNotEquals(is.postMesssage(0, null, 0, 0, "test"), null);
	}

	@Test
	public void testDeleteMessage() {
		Chat c1 = new Chat("c1");
		Mockito.when(cr.getChat(0)).thenReturn(c1);
		assertEquals(is.deleteMessage(0, null, 0, 0, 0), null);
		Message m = u.writeMessage(c1, "test");
		assertNotEquals(is.deleteMessage(0, null, 0, 0, m.getId()), null);
	}
	
}
