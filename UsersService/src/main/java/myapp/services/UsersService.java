package myapp.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/interaction")
public interface UsersService {
	
	
	@Path("/users/{user}")
	@GET
    @Produces("text/json")
	public String getUser(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("user") int user_id);
	
	@Path("/users/{user}")
	@PUT
    @Produces("text/json")
	public String putUserPassword(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("user") int user_id, @PathParam("password") String password);
}
