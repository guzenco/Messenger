package myapp.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Message {
	
	@Column(name="index")
	private Integer id;
	
	@Column(name="date")
	private String date;
	@Column(name="owner")
	private int owner;
	@Column(name="content", length=2048, nullable=false, unique=false)
	private String content;
	
	public Message() {

	}

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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, date, id, owner);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Message)) {
			return false;
		}
		Message other = (Message) obj;
		return Objects.equals(content, other.content) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && owner == other.owner;
	}

	
	
	
}
