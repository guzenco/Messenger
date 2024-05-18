package myapp.services;

import jakarta.ejb.Stateless;
import myapp.objects.User;

@Stateless
public class AuthServiceRemoteImpl implements AuthServiceRemote {

	@Override
	public User authentication(int id, String token, long time) {
		return null;
	}

	
}
