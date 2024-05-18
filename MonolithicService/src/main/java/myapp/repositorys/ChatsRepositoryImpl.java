package myapp.repositorys;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import myapp.objects.Chat;

@Stateless
public class ChatsRepositoryImpl implements ChatsRepository {

	@PersistenceContext(unitName = "Messanger")
	EntityManager em;
	
	@Override
	public Chat getChat(int id) {
		Chat res = null;
		try {
			res = em.find(Chat.class, id);
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return res;
	}

	@Override
	public List<Chat> getChats() {
		TypedQuery<Chat> query = em.createNamedQuery("getAllChats", Chat.class);
		List<Chat> res = null;
		try {
			res = query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean postChat(Chat c) {
		if(c == null)
			return false;
		try {
			if(c.getId() == -1) {
				em.persist(c);
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
	public boolean putChat(Chat c) {
		if(c == null)
			return false;
		try {
			Chat chat = getChat(c.getId());
			if(chat != null) {
				if(!chat.equals(c))
					em.merge(c);
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
	public boolean deleteChat(Chat c) {
		if(c == null)
			return false;
		try {
			Chat chat = getChat(c.getId());
			if(chat != null) {
				em.remove(chat);
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
