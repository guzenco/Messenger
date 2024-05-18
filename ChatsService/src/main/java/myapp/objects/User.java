package myapp.objects;


@jakarta.persistence.Entity
public class User extends Entity {
	
		
	public User() {
	}
	
	
	public Message writeMessage(Chat chat, String content) {
		Message m = new Message(this, content);
		chat.addMessage(m);
		return m;
	}
	
}
