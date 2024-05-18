package myapp;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.ejb.embeddable.EJBContainer;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUsersRepositoryImpl {

	static EJBContainer container;
	static UsersRepository ur;

	static User u1;
	static User u2;
	static User u3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Properties p = new Properties();
		p.put("MessangerDB", "new://Resource?type=DataSource");
        p.put("MessangerDB.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("MessangerDB.JdbcUrl", "jdbc:hsqldb:mem:MessangerDB_TestUsersRepositoryImpl");
        container = EJBContainer.createEJBContainer(p);
        final Context context = container.getContext();
        ur = (UsersRepository) context.lookup("java:global/UsersService/UsersRepositoryImpl");
        u1 = new User("Name", "Login", "Password");
    	u2 = new User("uName", "uLogin", "uPassword");
    	u3 = new User("usrName", "usrLogin", "usrPassword");
  	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		container.close();
	}

	@Test
	public void test1GetUsers() {
		List<User> users;
		users = ur.getUsers();
		assertEquals(users.size(), 0);
		
		ur.postUser(u1);
		users = ur.getUsers();
		assertEquals(users.size(), 1);
		assertEquals(users.get(0), u1);
		
		ur.postUser(u2);
		users = ur.getUsers();
		assertEquals(users.size(), 2);
		assertEquals(users.get(0), u1);
		assertEquals(users.get(1), u2);
	}

	@Test
	public void test2GetUserInt() {
		User u;
		u = ur.getUser(-1);
		assertEquals(u, null);

		
		assertNotEquals(u1.getId(), -1);
		u = ur.getUser(u1.getId());
		assertEquals(u, u1);
		assertNotEquals(u2.getId(), -1);
		u = ur.getUser(u2.getId());
		assertEquals(u, u2);
	}

	@Test
	public void test3GetUserString() {
		User u;
		u = ur.getUser(null);
		assertEquals(u, null);
		u = ur.getUser("");
		assertEquals(u, null);
		
		assertEquals(u1.getLogin(), "Login");
		u = ur.getUser(u1.getLogin());
		
		assertEquals(u.getId(), u1.getId());
		assertEquals(u, u1);
		assertEquals(u2.getLogin(), "uLogin");
		u = ur.getUser(u2.getLogin());
		assertEquals(u, u2);
	}

	@Test
	public void test4PostUser() {
		boolean res;
		int id,id2;
		User u;
		
		res = ur.postUser(null);
		assertEquals(res, false);
		
		res = ur.postUser(u3);
		assertEquals(res, true);
		id = u3.getId();
		assertNotEquals(id, -1);
		u = ur.getUser(id);
		assertEquals(u, u3);
		
		res = ur.postUser(u3);
		assertEquals(res, false);
		id2 = u3.getId();
		assertEquals(id, id2);
		u = ur.getUser(id);
		assertEquals(u, u3);
		u = ur.getUser(id + 1);
		assertEquals(u, null);
	}

	@Test
	public void test5PutUser() {
		boolean res;
		int id, id2;
		User u;
		
		res = ur.putUser(null);
		assertEquals(res, false);
		res = ur.putUser(u3);
		assertEquals(res, true);

		u3.setName("Ivan");
		u3.setLogin("Ivan_login");
		u3.setPassword("Ivan_password");
		u3.setToken("Ivan_token");
		res = ur.putUser(u3);
		assertEquals(res, true);
		id = u3.getId();
		assertNotEquals(id, -1);
		u = ur.getUser(id);
		assertEquals(u, u3);
		assertEquals(u.getName(), "Ivan");
		assertEquals(u.getLogin(), "Ivan_login");
		assertEquals(u.getPassword(), "Ivan_password");
		assertEquals(u.getToken(), "Ivan_token");

		u3.setId(999999);
		u3.setLogin("Ivanyan");
		res = ur.putUser(u3);
		assertEquals(res, false);
		id2 = u3.getId();
		assertEquals(id2, 999999);
		u = ur.getUser(id2);
		assertEquals(u, null);
		
		u3.setId(id);
		u = ur.getUser(id);
		assertNotEquals(u, u3);
		u3.setLogin("Ivan_login");
		assertEquals(u, u3);
	}

	@Test
	public void test6DeleteUser() {
		boolean res;
		List<User> users;
		users = ur.getUsers();
		
		res = ur.putUser(null);
		assertEquals(res, false);
		
		assertEquals(users.size(), 3);
		assertEquals(users.contains(u1), true);
		
		res = ur.deleteUser(u1);
		assertEquals(res, true);
		
		res = ur.deleteUser(u1);
		assertEquals(res, false);
		
		users = ur.getUsers();
		assertEquals(users.size(), 2);
		assertEquals(users.contains(u1), false);
	}

}
