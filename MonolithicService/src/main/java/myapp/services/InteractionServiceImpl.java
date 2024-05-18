package myapp.services;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.Chat;
import myapp.objects.Converter;
import myapp.objects.Message;
import myapp.objects.User;
import myapp.repositorys.ChatsRepository;

@Stateless
public class InteractionServiceImpl implements InteractionService {

	@EJB
	AccessPolicyLogic apl;
	
	@EJB
	ChatsRepository cr;
	
	@EJB
	AuthService as;
	
	@EJB
	Converter conventer;
	
	@Override
	public String getChats(int id, String token, long time) {
		User u = as.authentication(id, token, time);
		if(u != null) {
			List<Chat> chats = cr.getChats();
			chats.removeIf(c -> !apl.canRead(u, c));
			return conventer.getStringFromChats(chats);
		}
		return null;
	}

	@Override
	public String getChat(int id, String token, long time, int chat_id) {
		User u = as.authentication(id, token, time);
		if(u != null) {
			Chat c = cr.getChat(chat_id);
			if(apl.canRead(u, c))
				return conventer.getString(c);
		}
		return null;
	}

	@Override
	public String postMesssage(int id, String token, long time, int chat_id, String message) {
		User u = as.authentication(id, token, time);
		if(u != null) {
			Chat c = cr.getChat(chat_id);
			if(apl.canWriteMessage(u, c)) {
				Message m = u.writeMessage(c, message);
				cr.putChat(c);
				return conventer.getString(m);
			}
		}
		return null;
	}

	@Override
	public String deleteMessage(int id, String token, long time, int chat_id, int message_id) {
		User u = as.authentication(id, token, time);
		if(u != null) {
			Chat c = cr.getChat(chat_id);
			if(apl.canDeleteMessage(u, c, message_id)) {
				Message m = c.getMessage(message_id);
				if(c.removeMessage(message_id)) {
					cr.putChat(c);
					return conventer.getString(m);
				}
			}
		}
		return null;
	}

}
