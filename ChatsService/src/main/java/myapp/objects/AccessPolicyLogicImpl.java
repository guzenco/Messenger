package myapp.objects;

import jakarta.ejb.Stateless;

@Stateless
public class AccessPolicyLogicImpl implements AccessPolicyLogic {

	@Override
	public boolean canRead(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(chat.hasUser(actor.getId()))
			return true;
		return false;
	}

	@Override
	public boolean canWriteMessage(User actor, Chat chat) {
		if(actor == null || chat == null)
			return false;
		if(chat.hasUser(actor.getId()))
			return true;
		return false;
	}

	@Override
	public boolean canDeleteMessage(User actor, Chat chat, int message_id) {
		if(actor == null || chat == null)
			return false;
		Message m = chat.getMessage(message_id);
		if(m != null && m.getOwner() == actor.getId())
			return true;
		return false;
	}

}
