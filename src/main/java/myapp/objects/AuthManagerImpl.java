package myapp.objects;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import jakarta.ejb.Singleton;

@Singleton
public class AuthManagerImpl implements AuthManager {

	private Random r = new Random();

	@Override
	public boolean chechPasword(User user, String password, long time) {
		long stime = System.currentTimeMillis();
		if(stime >= time && stime - time < 5000) {
			String hash = DigestUtils.sha1Hex(user.getPassword() + time);
			if(hash.equals(password)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean chechToken(User user, String token, long time) {
		long stime = System.currentTimeMillis();
		if(stime >= time && stime - time < 5000) {
			String hash = DigestUtils.sha1Hex(user.getToken() + time);
			if(hash.equals(token)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String generateToken() {
		return Integer.toString(r.nextInt());
	}
	
}
