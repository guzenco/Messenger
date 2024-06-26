package myapp.services;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.Converter;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;

@Stateless
public class UsersManagementServiceImpl implements UsersManagementService {

	@EJB
	AccessPolicyLogic apl;
	
	@EJB
	UsersRepository ur;
	
	@EJB
	AuthServiceRemote as;
	
	@EJB
	Converter conventer;
	
	
	@Override
	public String getUsers(int id, String token, long time) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			if(apl.canSeeUsers(actor)) {
				List<User> users = ur.getUsers();
				return conventer.getStringFromUsers(users);
			}
		}
		return null;
	}

	@Override
	public String postUser(int id, String token, long time, String name, String login, String password) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			if(apl.canCreateUser(actor)) {
				User user = new User(name, login, password);
				if(ur.postUser(user))
					return conventer.getString(user);
			}
		}
		return null;
	}

	@Override
	public String putUser(int id, String token, long time, int user_id, String name, String password) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			User user = ur.getUser(user_id);
			boolean updated = false;
			if(apl.canUpdateUserName(actor, user) && password != null) {
				user.setName(name);
				updated = true;
			}
			if(apl.canUpdateUserPassword(actor, user) && password != null) {
				user.setPassword(password);
				updated = true;
			}
			if(updated && ur.putUser(user)) {
				return conventer.getString(user);
			}
		}
		return null;
	}

	@Override
	public String deleteUser(int id, String token, long time, int user_id) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			User user = ur.getUser(user_id);
			if(apl.canDeleteUser(actor, user)) {
				if(ur.deleteUser(user))
					return conventer.getString(user);
			}
		}
		return null;
	}

}
