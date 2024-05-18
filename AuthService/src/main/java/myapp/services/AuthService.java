package myapp.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import myapp.objects.User;

@Path("/auth")
public interface AuthService {
	
	@Path("/authorization")
	@GET
    @Produces("text/json")
	public String authorization(@QueryParam("login") String login, @QueryParam("password") String password,@QueryParam("time") long time);
	
	public User authentication(int id, String token, long time);
}
