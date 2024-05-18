package myapp.objects;

import jakarta.ejb.Stateless;

@Stateless
public class AccessPolicyLogicImpl implements AccessPolicyLogic {

	@Override
	public boolean canCreateUser(User actor) {
		if(actor == null || actor == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
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

	@Override
	public boolean canUpdateUserName(User actor, User user) {
		if(actor == null || user == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canDeleteUser(User actor, User user) {
		if(actor == null || user == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canSeeUsers(User actor) {
		if(actor == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}
	
}
