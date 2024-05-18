package myapp.repositorys;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import myapp.objects.User;

@Stateless
public class UsersRepositoryImpl implements UsersRepository {

	@PersistenceContext(unitName = "Messanger")
	EntityManager em;
	
	@Override
	public List<User> getUsers() {
		TypedQuery<User> query = em.createNamedQuery("getAllUsers", User.class);
		List<User> res = null;
		try {
			res = query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public User getUser(int id) {
		User res = null;
		try {
			res = em.find(User.class, id);
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return res;
	}

	@Override
	public User getUser(String login) {
		TypedQuery<User> query = em.createNamedQuery("findUserByLogin", User.class);
		query.setParameter("login", login);
		User res = null;
		try {
			List<User> list = query.getResultList();
			if(list.size() == 1)
				res = list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public boolean postUser(User u) {
		if(u == null)
			return false;
		try {
			if(u.getId() == -1) {
				em.persist(u);
				em.flush();
			} else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean putUser(User u) {
		if(u == null)
			return false;
		try {
			User user = getUser(u.getId());
			if(user != null) {
				if(!user.equals(u))
					em.merge(em.merge(u));	
				em.flush();
			} else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteUser(User u) {
		if(u == null)
			return false;
		try {
			User user = getUser(u.getId());
			if(user != null) {
				em.remove(user);
				em.flush();
			} else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
