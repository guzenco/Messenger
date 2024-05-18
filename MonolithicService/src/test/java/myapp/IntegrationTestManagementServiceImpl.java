package myapp;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.Context;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.ejb.embeddable.EJBContainer;
import myapp.objects.Chat;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;
import myapp.repositorys.UsersRepository;
import myapp.services.ManagementService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTestManagementServiceImpl {

	static EJBContainer container;
	static ManagementService ms;
	static ChatsRepository cr;
	static UsersRepository ur;
	
	static User actor;
	
	static User u1;
	static User u2;
	
	static Chat c1;
	static Chat c2;
	
	static int id;
	static long time;
	static String token;
	
	
	static void auth(User u) {
		id = u.getId();
		time = System.currentTimeMillis();
		token = DigestUtils.sha1Hex(u.getToken() + time);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Properties p = new Properties();
		p.put("MessangerDB", "new://Resource?type=DataSource");
        p.put("MessangerDB.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("MessangerDB.JdbcUrl", "jdbc:hsqldb:mem:MessangerDB_IntegrationTestManagementServiceImpl");
        container = EJBContainer.createEJBContainer(p);
        final Context context = container.getContext();
        ms = (ManagementService) context.lookup("java:global/Messenger/ManagementServiceImpl");
        ur = (UsersRepository) context.lookup("java:global/Messenger/UsersRepositoryImpl");
        cr = (ChatsRepository) context.lookup("java:global/Messenger/ChatsRepositoryImpl");
        
        actor = new User("Ivan", "admin", "123456");
        actor.setToken("test");
        actor.setGroup(1);
        ur.postUser(actor);
        auth(actor);
        
        u1 = new User("testu", "u1","123456");
		u2 = new User("user", "u2","123456");
		
		c1 = new Chat("c1");
		c2 = new Chat("c2");
		
		ur.postUser(u1);
		ur.postUser(u2);
		
		cr.postChat(c1);
		cr.postChat(c2);
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		container.close();
	}

	@Test
	public void test1GetUsers() {
		assertEquals(null, ms.getUsers(0, null, 0));
		String res = ms.getUsers(id, token, time);
		assertNotNull(res);
		assertEquals( "[{\"id\":"+ actor.getId() +",\"name\":\"Ivan\"},{\"id\":" + u1.getId() + ",\"name\":\"testu\"},{\"id\":" + u2.getId() +",\"name\":\"user\"}]", res);
	}

	@Test
	public void test2PostUser() {
		assertEquals(null, ms.postUser(0, null, 0, "testup", "u3", "123456"));
		String res = ms.postUser(id, token, time, "testup", "u3", "123456");
		assertNotNull(res);
		assertFalse(res.contains("-1"));
		res = res.replaceAll("\"id\":\\d{1,10000}", "\"id\":x");
		assertEquals("{\"id\":x,\"name\":\"testup\"}", res);
	}

	@Test
	public void test3PutUser() {
		assertEquals(null, ms.putUser(0, null, 0, u1.getId(), "new_name", "new_password"));
		String res = ms.putUser(id, token, time, u1.getId(), "new_name", "new_password");
		assertNotNull(res);
		assertEquals("{\"id\":"+ u1.getId() +",\"name\":\"new_name\"}", res);
		User u = ur.getUser(u1.getId());
		assertEquals(u1.getId(), u.getId());
		assertEquals("new_name", u.getName());
		assertEquals("new_password", u.getPassword());		
		u1 = u;
	}

	@Test
	public void test4DeleteUser() {
		assertEquals(null, ms.deleteUser(0, null, 0, u2.getId()));
		String res = ms.deleteUser(id, token, time, u2.getId());
		assertNotNull(res);
		assertEquals("{\"id\":"+u2.getId()+",\"name\":\"user\"}", res);
		assertEquals(null, ur.getUser(u2.getId()));
	}

	@Test
	public void test5PostChat() {
		assertEquals(null, ms.postChat(0, null, 0, "test"));
		String res = ms.postChat(id, token, time, "test");
		assertNotNull(res);
		assertFalse(res.contains("-1"));
		res = res.replaceAll("\"id\":\\d{1,10000}", "\"id\":x");
		assertEquals("{\"id\":x,\"name\":\"test\",\"messages\":[],\"users\":[]}", res);
	}

	@Test
	public void test6PutChat() {
		assertEquals(null, ms.putChat(0, null, 0, c1.getId(), "test123"));
		String res = ms.putChat(id, token, time, c1.getId(), "test123");
		assertNotNull(res);
		assertFalse(res.contains("-1"));
		assertEquals("{\"id\":" + c1.getId() + ",\"name\":\"test123\",\"messages\":[],\"users\":[]}", res);
		Chat c = cr.getChat(c1.getId());
		assertEquals(c1.getId(), c.getId());
		assertEquals("test123", c.getName());
		c1 = c;
	}

	@Test
	public void test7DeleteChat() {
		assertEquals(null, ms.deleteChat(0, null, 0, c2.getId()));
		String res = ms.deleteChat(id, token, time, c2.getId());
		assertNotNull(res);
		assertEquals("{\"id\":"+ c2.getId()+ ",\"name\":\"c2\",\"messages\":[],\"users\":[]}", res);
		assertEquals(null, cr.getChat(c2.getId()));
	}

	@Test
	public void test8PostUserInChat() {
		assertEquals(null, ms.postUserInChat(0, null, 0, c1.getId(), u1.getId()));
		String res = ms.postUserInChat(id, token, time, c1.getId(), u1.getId());
		assertNotNull(res);
		assertEquals("{\"id\":"+ c1.getId() +",\"name\":\""+ c1.getName() +"\",\"messages\":[],\"users\":["+ u1.getId() +"]}", res);
		Chat c = cr.getChat(c1.getId());
		assertNotNull(c);
		assertTrue(c.hasUser(u1.getId()));
	}

	@Test
	public void test9DeleteUserInChat() {
		assertEquals(null, ms.deleteUserInChat(0, null, 0, c1.getId(), u1.getId()));
		String res = ms.deleteUserInChat(id, token, time, c1.getId(), u1.getId());
		assertNotNull(res);
		assertEquals("{\"id\":"+ c1.getId() +",\"name\":\""+ c1.getName() +"\",\"messages\":[],\"users\":[]}", res);
		Chat c = cr.getChat(c1.getId());
		assertNotNull(c);
		assertFalse(c.hasUser(u1.getId()));
	}

}
