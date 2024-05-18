package myapp.objects;


import jakarta.ejb.Stateless;
import jakarta.json.Json;
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

	@Override
	public String getString(User u) {
		return toJson(u).toString();
	}

	
}
