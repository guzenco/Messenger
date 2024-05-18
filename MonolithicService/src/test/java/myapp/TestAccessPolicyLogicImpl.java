package myapp;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import myapp.objects.AccessPolicyLogic;
import myapp.objects.AccessPolicyLogicImpl;
import myapp.objects.Chat;
import myapp.objects.Message;
import myapp.objects.User;

public class TestAccessPolicyLogicImpl {

	static Chat c1;
	static Chat c2;
	static User u;
	static User a ;
	static Message am ;
	static Message um;
	
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		u = new User();
		a = new User();
		u.setId(0);
		a.setId(1);
		c1 = new Chat("c1");
		c2 = new Chat("c2");
		c1.addUser(u.getId());
		am = a.writeMessage(c1, "amess");
		um = u.writeMessage(c1, "umess");
		a.setGroup(1);
	}

	@Test
	public void testCanRead() {
		assertEquals(apl.canRead(a, c1), true);
		assertEquals(apl.canRead(u, c1), true);
		assertEquals(apl.canRead(u, c2), false);
	}

	@Test
	public void testCanWriteMessage() {
		assertEquals(apl.canWriteMessage(a, c1), true);
		assertEquals(apl.canWriteMessage(u, c1), true);
		assertEquals(apl.canWriteMessage(u, c2), false);
	}

	@Test
	public void testCanDeleteMessage() {
		assertEquals(apl.canDeleteMessage(a, c1, am.getId()), true);
		assertEquals(apl.canDeleteMessage(a, c1, um.getId()), true);
		assertEquals(apl.canDeleteMessage(u, c1, am.getId()), false);
		assertEquals(apl.canDeleteMessage(u, c1, um.getId()), true);
		assertEquals(apl.canDeleteMessage(u, c2, um.getId()), false);
	}

}
