package myapp.objects;


@jakarta.persistence.Entity
public class User extends Entity {
	
	private int group = 0;
	
	public User() {
	}
	
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	public Message writeMessage(Chat chat, String content) {
		Message m = new Message(this, content);
		chat.addMessage(m);
		return m;
	}
	
}
