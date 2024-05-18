package myapp.objects;

import jakarta.ejb.Local;

@Local
public interface AccessPolicyLogic {
	public boolean canRead(User actor, Chat chat);
	public boolean canWriteMessage(User actor, Chat chat);
	public boolean canDeleteMessage(User actor, Chat chat, int message_id);
}
