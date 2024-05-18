package myapp;

import org.junit.BeforeClass;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.AccessPolicyLogicImpl;
import myapp.objects.User;

public class TestAccessPolicyLogicImpl {

	static User u;
	static User a ;
	
	AccessPolicyLogic apl = new AccessPolicyLogicImpl();
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		u = new User();
		a = new User();
		u.setId(0);
		a.setId(1);
		a.setGroup(1);
	}


}
