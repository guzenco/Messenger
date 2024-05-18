package myapp.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name="chats")
@NamedQuery(name="getAllChats", query="SELECT c FROM Chat c")
public class Chat extends Entity {
	
	@Column(name="name", length=64, nullable=false, unique=false)
	private String name;
	
	@Column(name="message_index", nullable=false)
	private int index = 0;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "chats_messages", joinColumns = { @JoinColumn(name = "chat_id") })
	@MapKeyColumn(name="index")
	private Map<Integer, Message> messsegas = new HashMap<Integer, Message>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "chats_users", joinColumns = { @JoinColumn(name = "chat_id") })
	private Set<Integer> users = new HashSet<Integer>();
		
	public Chat() {
	}

	public Chat(String name) {
		this.name = name;
	}
	
	public boolean addMessage(Message m) {
		m.setId(index);
		messsegas.put(index, m);
		index++;
		return true;
	}

	public boolean removeMessage(Integer id) {
		if(id == null)
			return false;
		if(messsegas.containsKey(id)) {
			messsegas.remove(id);
			return true;
		}
		return false;
	}
	
	public boolean hasMessage(Integer id) {
		if(id == null)
			return false;
		return messsegas.containsKey(id);
	}
	
	public Message getMessage(Integer id) {
		if(id == null)
			return null;
		return messsegas.get(id);
	}
	
	public boolean addUser(int id) {
		return users.add(id);
	}

	public boolean removeUser(int id) {
		return users.remove(id);
	}
	
	public boolean hasUser(int id) {
		return users.contains(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Integer> getUsers() {
		return users;
	}

	public Map<Integer, Message> getMessages() {
		return messsegas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(index, messsegas, name, users);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Chat)) {
			return false;
		}
		Chat other = (Chat) obj;
		return index == other.index && Objects.equals(messsegas, other.messsegas) && Objects.equals(name, other.name)
				&& Objects.equals(users, other.users);
	}

	
	
}
