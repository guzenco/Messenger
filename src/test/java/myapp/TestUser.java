package myapp;

import static org.junit.Assert.*;

import org.junit.Test;

import myapp.objects.User;

public class TestUser {

	@Test
	public void test() {
		User u = new User("n", "l", "p");
		
		assertEquals(u.getName(), "n");
		u.setName("n1");
		assertEquals(u.getName(), "n1");
		
		assertEquals(u.getGroup(), 0);
		u.setGroup(1);
		assertEquals(u.getGroup(), 1);
		
		assertEquals(u.getLogin(), "l");
		u.setLogin("l22");
		assertEquals(u.getLogin(), "l22");
		
		assertEquals(u.getPassword(), "p");
		u.setPassword("123");
		assertEquals(u.getPassword(), "123");
		
		assertEquals(u.getToken(), null);
		u.setToken("t123");
		assertEquals(u.getToken(), "t123");
	}	
}
