package myapp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import myapp.objects.Converter;
import myapp.objects.JsonConverter;
import myapp.objects.User;

public class TestJsonConverter {

	Converter converter = new JsonConverter();

	@Test
	public void testGetStringUser() {
		User u = new User("test", "ulogin", "upassw");
		u.setId(124);
		assertEquals(converter.getString(u), "{\"id\":124,\"name\":\"test\"}");
	}

	@Test
	public void testGetStringFromUsers() {
		User u1 = new User("test", "ulogin", "upassw");
		u1.setId(5);
		User u2 = new User("test123", "ulogin789", "123456");
		u1.setId(7);
		List<User> users = new ArrayList<User>();
		assertEquals(converter.getStringFromUsers(users), "[]");
		users.add(u1);
		assertEquals(converter.getStringFromUsers(users), "[{\"id\":7,\"name\":\"test\"}]");
		users.add(u2);
		assertEquals(converter.getStringFromUsers(users), "[{\"id\":7,\"name\":\"test\"},{\"id\":-1,\"name\":\"test123\"}]");
	}

}
