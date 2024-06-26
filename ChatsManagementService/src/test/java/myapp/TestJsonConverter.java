package myapp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.JsonConverter;
import myapp.objects.Message;
import myapp.objects.User;

public class TestJsonConverter {

	Converter converter = new JsonConverter();
	

	@Test
	public void testGetStringMessage() {
		User o = new User();
		o.setId(123);
		Message m = new Message(o, "test");
		m.setId(1);
		assertEquals(converter.getString(m), "{\"id\":1,\"date\":\"" + m.getDate() +"\",\"owner\":123,\"content\":\"test\"}");
	}

	@Test
	public void testGetStringChat() {
		Chat c = new Chat("Test");
		c.setId(455);
		assertEquals(converter.getString(c), "{\"id\":455,\"name\":\"Test\",\"messages\":[],\"users\":[]}");
		User u = new User();
		u.setId(124);
		c.addUser(u.getId());
		assertEquals(converter.getString(c), "{\"id\":455,\"name\":\"Test\",\"messages\":[],\"users\":[124]}");
		Message m = new Message(u, "test");
		c.addMessage(m);
		assertEquals(converter.getString(c), "{\"id\":455,\"name\":\"Test\",\"messages\":[{\"id\":0,\"date\":\"" + m.getDate() +"\",\"owner\":124,\"content\":\"test\"}],\"users\":[124]}");
	}


	@Test
	public void testGetStringFromChats() {
		Chat c1 = new Chat("Test");
		c1.setId(2);
		Chat c2 = new Chat("Test2");
		c2.setId(3);
		List<Chat> chats = new ArrayList<Chat>();
		assertEquals(converter.getStringFromChats(chats), "[]");
		chats.add(c1);
		assertEquals(converter.getStringFromChats(chats), "[{\"id\":2,\"name\":\"Test\",\"messages\":[],\"users\":[]}]");
		chats.add(c2);
		assertEquals(converter.getStringFromChats(chats), "[{\"id\":2,\"name\":\"Test\",\"messages\":[],\"users\":[]},{\"id\":3,\"name\":\"Test2\",\"messages\":[],\"users\":[]}]");
	}


}
