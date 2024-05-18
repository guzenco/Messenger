package myapp.objects;

import jakarta.ejb.Local;

@Local
public interface AccessPolicyLogic {
	public boolean canSeeUser(User actor, User user);
	public boolean canUpdateUserPassword(User actor, User user);

}
