package myapp.objects;

import jakarta.ejb.Local;

@Local 
public interface AuthManager {

	public boolean chechPasword(User user, String password, long time);
	
	public boolean chechToken(User user, String token, long time);

	public String generateToken();
}
