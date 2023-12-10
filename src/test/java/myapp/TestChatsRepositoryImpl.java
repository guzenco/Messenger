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
import myapp.objects.Chat;
import myapp.objects.Message;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestChatsRepositoryImpl {

	static EJBContainer container;
	static ChatsRepository cr;

	static Chat c1 ;
	static Chat c2 ;
	static Chat c3 ;
	
	static User u;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Properties p = new Properties();
		p.put("MessangerDB", "new://Resource?type=DataSource");
        p.put("MessangerDB.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("MessangerDB.JdbcUrl", "jdbc:hsqldb:mem:MessangerDB_TestChatsRepositoryImpl");
        container = EJBContainer.createEJBContainer(p);
        final Context context = container.getContext();
        cr = (ChatsRepository) context.lookup("java:global/Messenger/ChatsRepositoryImpl");
        u = new User("Name", "Login", "Password");
        u.setId(0);
        c1 = new Chat("Chat1");
        c1.addUser(0);
        u.writeMessage(c1, "test");
        c2 = new Chat("Chat2");
        c2.addUser(0);
        c2.addUser(1);
        c3 = new Chat("Chat3");
  	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		container.close();
	}
	
	
	@Test
	public void test1GetChats() {
		List<Chat> chats;
		chats = cr.getChats();
		assertEquals(chats.size(), 0);
		
		assertEquals(cr.postChat(c1), true);
		
		chats = cr.getChats();
		assertEquals(chats.size(), 1);
		assertEquals(chats.get(0), c1);
		
		assertEquals(cr.postChat(c2), true);
		chats = cr.getChats();
		assertEquals(chats.size(), 2);
		assertEquals(chats.get(0), c1);
		assertEquals(chats.get(1), c2);;
	}

	@Test
	public void test2GetChat() {
		Chat c;
		c = cr.getChat(-1);
		assertEquals(c, null);
		c = cr.getChat(0);
		assertEquals(c, null);
		
		assertNotEquals(c1.getId(), -1);
		c = cr.getChat(c1.getId());
		
		assertEquals(c, c1);
		assertNotEquals(c2.getId(), -1);
		c = cr.getChat(c2.getId());
		assertEquals(c, c2);
	}

	@Test
	public void test3PostChat() {
		boolean res;
		int id,id2;
		Chat u;
		
		res = cr.postChat(null);
		assertEquals(res, false);
		
		res = cr.postChat(c3);
		assertEquals(res, true);
		id = c3.getId();
		assertNotEquals(id, -1);
		u = cr.getChat(id);
		assertEquals(u, c3);
		
		res = cr.postChat(c3);
		assertEquals(res, false);
		id2 = c3.getId();
		assertEquals(id, id2);
		u = cr.getChat(id);
		assertEquals(u, c3);
		u = cr.getChat(id + 1);
		assertEquals(u, null);
	}

	@Test
	public void test4PutChat() {
		boolean res;
		int id, id2;
		Chat c;
		
		res = cr.putChat(null);
		assertEquals(res, false);
		res = cr.putChat(c3);
		assertEquals(res, true);

		c3.setName("Ivan chat");
		c3.addUser(13);
		Message m = u.writeMessage(c3, "test");
		res = cr.putChat(c3);
		assertEquals(res, true);
		id = c3.getId();
		assertNotEquals(id, -1);
		c = cr.getChat(id);
		assertEquals(c, c3);
		assertTrue(c.hasUser(13));
		assertTrue(c.hasMessage(m.getId()));

		c3.setId(999999);
		res = cr.putChat(c3);
		assertEquals(res, false);
		id2 = c3.getId();
		assertEquals(id2, 999999);
		c = cr.getChat(id2);
		assertEquals(c, null);
		
		c3.setId(id);
		c = cr.getChat(id);
		assertEquals(c, c3);
	}

	@Test
	public void test5DeleteChat() {
		boolean res;
		List<Chat> chats;
		chats = cr.getChats();
		
		res = cr.putChat(null);
		assertEquals(res, false);
		
		assertEquals(chats.size(), 3);
		assertEquals(chats.contains(c1), true);
		
		res = cr.deleteChat(c1);
		assertEquals(res, true);
		
		res = cr.deleteChat(c1);
		assertEquals(res, false);
		
		chats = cr.getChats();
		assertEquals(chats.size(), 2);
		assertEquals(chats.contains(c1), false);
	}

}
