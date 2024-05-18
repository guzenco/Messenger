package myapp.objects;

import jakarta.ejb.Local;

@Local
public interface AccessPolicyLogic {

	public boolean canSeeUsers(User actor);
	public boolean canCreateUser(User actor);
	public boolean canUpdateUserPassword(User actor, User user);
	public boolean canUpdateUserName(User actor, User user);
	public boolean canDeleteUser(User actor, User user);
	
}
