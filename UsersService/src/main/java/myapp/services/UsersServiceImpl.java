package myapp.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import myapp.objects.AccessPolicyLogic;
import myapp.objects.Converter;
import myapp.objects.User;
import myapp.repositorys.UsersRepository;

@Stateless
public class UsersServiceImpl implements UsersService {

	@EJB
	AccessPolicyLogic apl;
	
	@EJB
	UsersRepository ur;
	
	@EJB
	AuthServiceRemote as;
	
	@EJB
	Converter conventer;

	@Override
	public String getUser(int id, String token, long time, int user_id) {
		User actor = as.authentication(id, token, time);
		User user = ur.getUser(user_id);
		if(actor != null) {
			if(apl.canSeeUser(actor, user)) {
				return conventer.getString(user);
			}
		}
		return null;
	}

	@Override
	public String putUserPassword(int id, String token, long time, int user_id, String password) {
		User actor = as.authentication(id, token, time);
		if(actor != null) {
			User user = ur.getUser(user_id);
			boolean updated = false;
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
	
	

}
