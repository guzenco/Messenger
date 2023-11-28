package myapp.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Chat extends Entity {
	
	private String name;
	private Set<Integer> users = new HashSet<Integer>();
	private int index = 0;
	private HashMap<Integer, Message> messsegas = new HashMap<Integer, Message>();
	
	public Chat(String name) {
		this.name = name;
	}
	
	public boolean addMessage(Message m) {
		m.setId(index++);
		messsegas.put(m.getId(), m);
		index++;
		return true;
	}

	public boolean removeMessage(int id) {
		if(messsegas.containsKey(id)) {
			messsegas.remove(id);
			return true;
		}
		return false;
	}
	
	public boolean hasMessage(int id) {
		return messsegas.containsKey(id);
	}
	
	public Message getMessage(int id) {
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

	public HashMap<Integer, Message> getMesssegas() {
		return messsegas;
	}
}
