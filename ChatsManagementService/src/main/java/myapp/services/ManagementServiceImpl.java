package myapp.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;

@Stateless
public class ManagementServiceImpl implements ManagementService {

	@EJB
	AccessPolicyLogic apl;
	
	@EJB
	ChatsRepository cr;
	
	
	@EJB
	AuthServiceRemote as;
	
	@EJB
	Converter conventer;
	

	@Override
	public String postChat(int id, String token, long time, String name) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			if(apl.canCreateChat(actor)) {
				Chat chat = new Chat(name);
				if(cr.postChat(chat))
					return conventer.getString(chat);
			}
		}
		return null;
	}

	@Override
	public String putChat(int id, String token, long time, int chat_id, String name) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			Chat chat = cr.getChat(chat_id);
			boolean updated = false;
			if(apl.canUpdateChatName(actor, chat)) {
				chat.setName(name);
				updated = true;
			}
			if(updated && cr.putChat(chat))
				return conventer.getString(chat);
		}
		return null;
	}

	@Override
	public String deleteChat(int id, String token, long time, int chat_id) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			Chat chat = cr.getChat(chat_id);
			if(apl.canDeleteChat(actor, chat)) {
				if(cr.deleteChat(chat))
					return conventer.getString(chat);
			}
				
		}
		return null;
	}

	@Override
	public String postUserInChat(int id, String token, long time, int chat_id, int user_id) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			Chat chat = cr.getChat(chat_id);
			if(apl.canAddUserToChat(actor, chat)) {
				boolean res = chat.addUser(user_id);
				if(res && cr.putChat(chat))
					return conventer.getString(chat);
			}
		}
		return null;
	}

	@Override
	public String deleteUserInChat(int id, String token, long time, int chat_id, int user_id) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			Chat chat = cr.getChat(chat_id);
			if(apl.canDelateUserFromChat(actor, chat)) {
				boolean res = chat.removeUser(user_id);
				if(res && cr.putChat(chat))
					return conventer.getString(chat);
			}
		}
		return null;
	}

}
