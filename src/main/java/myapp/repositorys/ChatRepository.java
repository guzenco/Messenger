package myapp.repositorys;

import java.util.List;

import jakarta.ejb.Local;
import myapp.objects.Chat;

@Local
public interface ChatRepository {

	public Chat getChat(int id);
	public List<Chat> getChats();
	public boolean postChat(Chat c);
	public boolean putChat(Chat c);
	public boolean deleteChat(Chat c);
}
