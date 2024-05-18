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
	
	private JsonObject toJson(User u) {	
		JsonObjectBuilder jb = Json.createObjectBuilder();
		jb.add("id", u.getId());
		jb.add("name", u.getName());
		return jb.build();
	}
	
	private JsonArray toJsonFromUsers(List<User> users) {
		JsonArrayBuilder res = Json.createArrayBuilder();
		users.forEach((u) -> res.add(toJson(u)));
		return res.build();
	}

	@Override
	public String getString(User u) {
		return toJson(u).toString();
	}


	@Override
	public String getStringFromUsers(List<User> users) {
		return toJsonFromUsers(users).toString();
	}
	
}
