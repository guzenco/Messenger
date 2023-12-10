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
import myapp.objects.User;
import myapp.repositorys.UsersRepository;
import myapp.services.AuthService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTestAuthServiceImpl {

	static EJBContainer container;
	static AuthService as;
	static UsersRepository ur;
	
	static User u;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Properties p = new Properties();
		p.put("MessangerDB", "new://Resource?type=DataSource");
        p.put("MessangerDB.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("MessangerDB.JdbcUrl", "jdbc:hsqldb:mem:MessangerDB_IntegrationTestAuthServiceImpl");
        
        container = EJBContainer.createEJBContainer(p);
        final Context context = container.getContext();
        
        as = (AuthService) context.lookup("java:global/Messenger/AuthServiceImpl");
        ur = (UsersRepository) context.lookup("java:global/Messenger/UsersRepositoryImpl");
        
        u = new User("test_user", "u123456","123456");
        
        ur.postUser(u);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		container.close();
	}

	@Test
	public void test1Authentication() {
		long stime = System.currentTimeMillis();
		String password = DigestUtils.sha1Hex(u.getPassword() + stime);
		
		String res = as.authorization(u.getLogin(), password, stime);
		User u1;
		u1 = ur.getUser(u.getId());
		assertEquals(u.getId(), u1.getId());
		assertNotEquals(u.getToken(), u1.getId());
		u = u1;
		assertEquals("{\"id\":" + u.getId() + ",\"token\":\"" + u.getToken() + "\"}", res);
		
		stime += 1;
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(null, res);
		
		stime = System.currentTimeMillis() - 5001;
		password = DigestUtils.sha1Hex(u.getPassword() + stime);
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(null, res);
		
		stime = System.currentTimeMillis();
		password = DigestUtils.sha1Hex("123" + stime);
		res = as.authorization(u.getLogin(), password, stime);
		assertEquals(null, res);
	}

	@Test
	public void test2Authorization() {
		long stime = System.currentTimeMillis();
		String token = DigestUtils.sha1Hex(u.getToken() + stime);
		
		User res = as.authentication(u.getId(), token, stime);
		assertEquals(u, res);
		
		
		stime += 1;
		res = as.authentication(u.getId(), token, stime);
		assertEquals(null, res);
		
		stime = System.currentTimeMillis() - 5001;
		token = DigestUtils.sha1Hex(u.getPassword() + stime);
		res = as.authentication(u.getId(), token, stime);
		assertEquals(null, res);
		
		stime = System.currentTimeMillis();
		token = DigestUtils.sha1Hex("123" + stime);
		res = as.authentication(u.getId(), token, stime);
		assertEquals(null, res);
	}

}
