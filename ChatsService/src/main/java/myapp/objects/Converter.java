package myapp.objects;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface Converter {
	
	public String getString(Message m);
		
	public String getString(Chat c);
	
	public String getStringFromChats(List<Chat> chats);

}
