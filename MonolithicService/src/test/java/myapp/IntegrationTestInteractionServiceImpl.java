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
import myapp.services.InteractionService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTestInteractionServiceImpl {

	static EJBContainer container;
	static InteractionService is;
	static ChatsRepository cr;
	static UsersRepository ur;
	
	static User actor;
	
	static User u1;
	
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
        p.put("MessangerDB.JdbcUrl", "jdbc:hsqldb:mem:MessangerDB_IntegrationTestInteractionServiceImpl");
        container = EJBContainer.createEJBContainer(p);
        final Context context = container.getContext();
        is = (InteractionService) context.lookup("java:global/Messenger/InteractionServiceImpl");
        ur = (UsersRepository) context.lookup("java:global/Messenger/UsersRepositoryImpl");
        cr = (ChatsRepository) context.lookup("java:global/Messenger/ChatsRepositoryImpl");
        
        actor = new User("Ivan", "admin", "123456");
        actor.setToken("test");
        actor.setGroup(1);
        ur.postUser(actor);
        auth(actor);
        
        u1 = new User("testu", "u1","123456");
		
		c1 = new Chat("c1");
		c2 = new Chat("c2");
		
		ur.postUser(u1);
		
		cr.postChat(c1);
		cr.postChat(c2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		container.close();
	}

	@Test
	public void test1GetChats() {
		c2.addUser(u1.getId());
		cr.putChat(c2);
		assertEquals(null, is.getChats(0, null, 0));
		assertEquals("[{\"id\":"+ c1.getId() +",\"name\":\"c1\",\"messages\":[],\"users\":[]},{\"id\":" + c2.getId() +",\"name\":\"c2\",\"messages\":[],\"users\":[" + u1.getId() + "]}]", is.getChats(id, token, time));
	}

	@Test
	public void test2GetChat() {
		assertEquals(null, is.getChat(0, null, 0, c2.getId()));
		assertEquals("{\"id\":" +c2.getId() +",\"name\":\"c2\",\"messages\":[],\"users\":[" + u1.getId() +"]}", is.getChat(id, token, time, c2.getId()));
		assertEquals(null, is.getChat(id, token, time, -1));
		
	}

	@Test
	public void test3PostMesssage() {
		assertEquals(null, is.postMesssage(0, null, 0, c1.getId(), "test"));
		String res = is.postMesssage(id, token, time, c1.getId(), "test");
		assertNotNull(res);
		assertFalse(res.contains("\"date\":\"date\""));
		res = res.replaceAll("\"date\":\".{19}\"", "\"date\":\"date\"");
		assertEquals("{\"id\":0,\"date\":\"date\",\"owner\":"+ id +",\"content\":\"test\"}", res);
	}

	@Test
	public void test4DeleteMessage() {
		assertEquals(null, is.deleteMessage(0, null, 0, c1.getId(), 0));
		String res = is.deleteMessage(id, token, time, c1.getId(), 0);
		assertNotNull(res);
		assertFalse(res.contains("\"date\":\"date\""));
		res = res.replaceAll("\"date\":\".{19}\"", "\"date\":\"date\"");
		assertEquals("{\"id\":0,\"date\":\"date\",\"owner\":"+ id +",\"content\":\"test\"}", res);
		assertEquals(null, is.deleteMessage(id, token, time, c1.getId(), 0));
	}
}
