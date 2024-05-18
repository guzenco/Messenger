package myapp;

import static org.junit.Assert.*;

import org.junit.Test;

import myapp.objects.Entity;

public class TestEntity {

	@Test
	public void test() {
		Entity e = new Entity(); 
		
		assertEquals(e.getId(), -1);
		e.setId(1);
		assertEquals(e.getId(), 1);
	}

}
