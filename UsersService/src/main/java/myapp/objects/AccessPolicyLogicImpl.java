package myapp.objects;

import jakarta.ejb.Stateless;

@Stateless
public class AccessPolicyLogicImpl implements AccessPolicyLogic {

	@Override
	public boolean canSeeUser(User actor, User user) {
		if(actor == null || user == null)
			return false;
		return true;
	}


	@Override
	public boolean canUpdateUserPassword(User actor, User user) {
		if(actor == null || user == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		if(actor.getId() == user.getId())
			return true;
		return false;
	}
	
}
