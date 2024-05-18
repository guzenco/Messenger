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
		
		assertEquals(u.getToken(), "");
		u.setToken("t123");
		assertEquals(u.getToken(), "t123");
	}	
	
	@Test
	public void testEqual() {
		User u1 = new User("n1", "l1", "p1");
		User u2 = new User("n1", "l1", "p1");
		u1.setId(0);
		u2.setId(0);
		assertEquals(u1, u2);
		//Test super
		u2.setId(1);
		assertNotEquals(u1, u2);
		u2.setId(0);
		//Test name
		u2.setName("n2");
		assertNotEquals(u1, u2);
		u2.setName("n1");
		//Test login
		u2.setLogin("l2");
		assertNotEquals(u1, u2);
		u2.setLogin("l1");
		//Test password
		u2.setPassword("p2");
		assertNotEquals(u1, u2);
		u2.setPassword("p1");
		//Test token
		u2.setToken("1");
		assertNotEquals(u1, u2);
		u2.setToken("");
	}
}
