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

}
