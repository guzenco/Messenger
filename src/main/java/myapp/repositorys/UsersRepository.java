package myapp.repositorys;

import java.util.List;

import jakarta.ejb.Local;
import myapp.objects.User;

@Local
public interface UsersRepository {

	public List<User> getUsers();
	public User getUser(int id);
	public User getUser(String login);
	public boolean postUser(User u);
	public boolean putUser(User u);
	public boolean deleteUser(User u);
}
