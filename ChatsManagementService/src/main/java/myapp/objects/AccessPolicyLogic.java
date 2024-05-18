package myapp.objects;

import jakarta.ejb.Local;

@Local
public interface AccessPolicyLogic {
	
	public boolean canCreateChat(User actor);
	public boolean canUpdateChatName(User actor, Chat chat);
	public boolean canDeleteChat(User actor, Chat chat);
	
	public boolean canAddUserToChat(User actor, Chat chat);
	public boolean canDelateUserFromChat(User actor, Chat chat);
	
}
