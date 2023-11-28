package myapp.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import myapp.objects.AuthManager;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;

@Stateless
public class AuthServiceImpl implements AuthService {

	@EJB
	UsersRepository ur;
	
	@EJB
	AuthManager am;
	
	@Override
	public User authentication(int id, String token, long time) {
		User u = ur.getUser(id);
		if(am.chechToken(u, token, time)) {
			return u;
		}
		return null;
	}

	@Override
	public String authorization(String login, String password, long time) {	
		User u = ur.getUser(login);
		if(am.chechPasword(u, password, time)) {
			u.setToken(am.generateToken());
			ur.putUser(u);
			
			JsonObjectBuilder jb = Json.createObjectBuilder();
			jb.add("id", u.getId());
			jb.add("token", u.getToken());
			return jb.build().toString();
		}
		return null;
	}
	
}
