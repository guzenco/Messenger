package myapp;

import static org.junit.Assert.*;

import org.junit.Test;

import myapp.objects.Chat;
import myapp.objects.Message;
import myapp.objects.User;

public class TestChat {

	@Test
	public void test() {
		Chat c = new Chat("t1");
		assertEquals(c.getName(), "t1");
		c.setName("t2");
		assertEquals(c.getName(), "t2");
		
		User u = new User();
		assertEquals(c.hasUser(u.getId()), false);
		assertEquals(c.removeUser(u.getId()), false);
		assertEquals(c.addUser(u.getId()), true);
		assertEquals(c.addUser(u.getId()), false);
		assertEquals(c.hasUser(u.getId()), true);
		assertEquals(c.removeUser(u.getId()), true);
		assertEquals(c.removeUser(u.getId()), false);
		assertEquals(c.hasUser(u.getId()), false);
		
		Message m = new Message(u, "test");
		assertEquals(c.getMessage(m.getId()), null);
		assertEquals(c.hasMessage(m.getId()), false);
		assertEquals(c.removeMessage(m.getId()), false);
		assertEquals(c.addMessage(m), true);
		assertEquals(c.addMessage(m), true);
		assertEquals(c.hasMessage(m.getId()), true);
		assertEquals(c.getMessage(m.getId()), m);
		assertEquals(c.removeMessage(m.getId()), true);
		assertEquals(c.removeMessage(m.getId()), false);
		assertEquals(c.hasMessage(m.getId()), false);
		assertEquals(c.getMessage(m.getId()), null);
	}

}
