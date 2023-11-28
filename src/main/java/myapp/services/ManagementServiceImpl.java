package myapp.services;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.User;
import myapp.repositorys.ChatRepository;
import myapp.repositorys.UsersRepository;

@Stateless
public class ManagementServiceImpl implements ManagementService {

	@EJB
	AccessPolicyLogic apl;
	
	@EJB
	ChatRepository cr;
	
	@EJB
	UsersRepository ur;
	
	@EJB
	AuthService as;
	
	@EJB
	Converter conventer;
	
	
	@Override
	public String getUsers(int id, String token, long time) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			if(apl.canSeeUsers(actor)) {
				List<User> users = ur.getUsers();
				return conventer.getStringFromUsers(users);
			}
		}
		return null;
	}

	@Override
	public String postUser(int id, String token, long time, String name, String login, String password) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			if(apl.canCreateUser(actor)) {
				User user = new User(name, login, password);
				if(ur.postUser(user))
					return conventer.getString(user);
			}
		}
		return null;
	}

	@Override
	public String putUser(int id, String token, long time, int user_id, String name, String password) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			User user = ur.getUser(user_id);
			boolean updated = false;
			if(apl.canUpdateUserName(actor, user)) {
				user.setName(name);
				updated = true;
			}
			if(apl.canUpdateUserPassword(actor, user)) {
				user.setPassword(password);
				updated = true;
			}
			if(updated && ur.putUser(user))
				return conventer.getString(user);
		}
		return null;
	}

	@Override
	public String deleteUser(int id, String token, long time, int user_id) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			User user = ur.getUser(user_id);
			if(apl.canDeleteUser(actor, user)) {
				if(ur.deleteUser(user))
					return conventer.getString(user);
			}
		}
		return null;
	}

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
