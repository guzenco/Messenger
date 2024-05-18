package myapp.objects;

import jakarta.ejb.Stateless;

@Stateless
public class AccessPolicyLogicImpl implements AccessPolicyLogic {


	@Override
	public boolean canCreateChat(User actor) {
		if(actor == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canUpdateChatName(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canDeleteChat(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canAddUserToChat(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}

	@Override
	public boolean canDelateUserFromChat(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}
	
}
