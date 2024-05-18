package myapp.objects;

import jakarta.ejb.Local;

@Local
public interface AccessPolicyLogic {
	public boolean canRead(User actor, Chat chat);
	public boolean canWriteMessage(User actor, Chat chat);
	public boolean canDeleteMessage(User actor, Chat chat, int message_id);
	
	public boolean canSeeUser(User actor, User user);
	
	public boolean canSeeUsers(User actor);
	public boolean canCreateUser(User actor);
	public boolean canUpdateUserPassword(User actor, User user);
	public boolean canUpdateUserName(User actor, User user);
	public boolean canDeleteUser(User actor, User user);
	
	public boolean canCreateChat(User actor);
	public boolean canUpdateChatName(User actor, Chat chat);
	public boolean canDeleteChat(User actor, Chat chat);
	
	public boolean canAddUserToChat(User actor, Chat chat);
	public boolean canDelateUserFromChat(User actor, Chat chat);
	
}
