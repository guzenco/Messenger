package myapp.objects;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Stateless
public class JsonConverter implements Converter {
	private JsonObject toJson(Message m) {
		JsonObjectBuilder jb = Json.createObjectBuilder();
		jb.add("id", m.getId());
		jb.add("date", m.getDate());
		jb.add("owner", m.getOwner());
		jb.add("content", m.getContent());
		return jb.build();
	}
	
	private JsonObject toJson(Chat c) {	
		JsonObjectBuilder jb = Json.createObjectBuilder();
		jb.add("id", c.getId());
		jb.add("name", c.getName());
		JsonArrayBuilder messages = Json.createArrayBuilder();
		c.getMessages().forEach((i, m) -> messages.add(toJson(m)));
		jb.add("messages", messages);
		JsonArrayBuilder users = Json.createArrayBuilder();
		c.getUsers().forEach((u) -> users.add(u));
		jb.add("users", users);
		
		return jb.build();
	}
	
	
	private JsonArray toJsonFromChats(List<Chat> chats) {
		JsonArrayBuilder res = Json.createArrayBuilder();
		chats.forEach((c) -> res.add(toJson(c)));
		return res.build();
	}
	

	@Override
	public String getString(Message m) {
		return toJson(m).toString();
	}

	@Override
	public String getString(Chat c) {
		return toJson(c).toString();
	}


	@Override
	public String getStringFromChats(List<Chat> chats) {
		return toJsonFromChats(chats).toString();
	}
	
}
