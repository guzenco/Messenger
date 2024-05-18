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

}
