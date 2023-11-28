package myapp.objects;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface Converter {
	
	public String getString(Message m);
		
	public String getString(Chat c);
	
	public String getString(User u);

	public String getStringFromChats(List<Chat> chats);

	public String getStringFromUsers(List<User> users);
	
}
