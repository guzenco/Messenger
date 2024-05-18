package myapp.objects;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name="users")
@NamedQuery(name="getAllUsers", query="SELECT u FROM User u")
@NamedQuery(name="findUserByLogin", query="SELECT u FROM User u WHERE u.login = :login")
public class User extends Entity {
	
	@Column(name="name", length=64, nullable=false, unique=false)
	private String name;
	
	@Column(name="login", length=16, nullable=false, unique=true)
	private String login;
	
	@Column(name="password", length=64, nullable=false, unique=false)
	private String password;
	
	@Column(name="token", length=64, nullable=false, unique=false)
	private String token = "";
	
	@Column(name="group_id", nullable=false)
	private int group = 0;
	
	public User() {
	}
	
	public User(String name, String login, String password) {
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(group, login, name, password, token);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return group == other.group && Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(token, other.token);
	}
	
	
}
