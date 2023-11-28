package myapp.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message extends Entity {
	
	private String date;
	private int owner;
	private String content;
	
	public Message(User owner, String content) {
		this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss"));
		this.owner = owner.getId();
		this.content = content;
	}
	
	public String getDate() {
		return date;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner.getId();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
