package myapp.services;

import myapp.objects.User;

public interface AuthServiceRemote {
	
	public User authentication(int id, String token, long time);
}
