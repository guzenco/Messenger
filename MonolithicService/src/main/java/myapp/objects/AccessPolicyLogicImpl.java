package myapp.objects;

import jakarta.ejb.Stateless;

@Stateless
public class AccessPolicyLogicImpl implements AccessPolicyLogic {

	@Override
	public boolean canRead(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		if(chat.hasUser(actor.getId()))
			return true;
		return false;
	}

	@Override
	public boolean canWriteMessage(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		if(chat.hasUser(actor.getId()))
			return true;
		return false;
	}

	@Override
	public boolean canDeleteMessage(User actor, Chat chat, int message_id) {
		if(actor == null || chat == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		Message m = chat.getMessage(message_id);
		if(m != null && m.getOwner() == actor.getId())
			return true;
		return false;
	}

	@Override
	public boolean canSeeUser(User actor, User user) {
		if(actor == null || user == null)
			return false;
		return true;
	}

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

	@Override
	public boolean canSeeUsers(User actor) {
		if(actor == null)
			return false;
		if(actor.getGroup() == 1)
			return true;
		return false;
	}
	
}
