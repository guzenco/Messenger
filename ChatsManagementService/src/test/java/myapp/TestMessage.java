package myapp;

import static org.junit.Assert.*;

import org.junit.Test;

import myapp.objects.Message;
import myapp.objects.User;

public class TestMessage {

	@Test
	public void test() {
		User u = new User();
		Message m = new Message(u, "test");
		assertNotEquals(m.getDate(), null);
		assertEquals(m.getOwner(), -1);
		u.setId(10);
		m.setOwner(u);
		assertEquals(m.getOwner(), 10);
		assertEquals(m.getContent(), "test");
		m.setContent("123");
		assertEquals(m.getContent(), "123");
	}

}
