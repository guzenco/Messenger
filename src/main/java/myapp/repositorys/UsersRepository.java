package myapp.repositorys;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.transaction.Transactional;
import myapp.objects.User;

@Local
@Transactional
public interface UsersRepository {

	public List<User> getUsers();
	public User getUser(int id);
	public User getUser(String login);
	public boolean postUser(User u);
	public boolean putUser(User u);
	public boolean deleteUser(User u);
}
