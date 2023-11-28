package myapp.services;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/management")
public interface ManagementService {
	
	@Path("/users")
	@GET
    @Produces("text/json")
	public String getUsers(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time
			);
	
	
	@Path("/users")
	@POST
    @Produces("text/json")
	public String postUser(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@QueryParam("name") String name, @QueryParam("login") String login, @QueryParam("password") String password
			);
	
	
	@Path("/users/{user}")
	@PUT
    @Produces("text/json")
	public String putUser(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("user") int user_id, @QueryParam("name") String name, @QueryParam("password") String password);
	
	
	@Path("/users/{user}")
	@DELETE
    @Produces("text/json")
	public String deleteUser(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("user") int user_id
			);
	
	
	
	@Path("/chats")
	@POST
    @Produces("text/json")
	public String postChat(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@QueryParam("name") String name
			);
	
	
	@Path("/chats/{chat}")
	@PUT
    @Produces("text/json")
	public String putChat(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("chat") int chat_id, @QueryParam("name") String name
			);
	
	
	@Path("/chats/{chat}")
	@DELETE
    @Produces("text/json")
	public String deleteChat(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("chat") int chat_id
			);
	
	
	@Path("/chats/{chat}/users")
	@POST
    @Produces("text/json")
	public String postUserInChat(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time, 
			@PathParam("chat") int chat_id, @QueryParam("id") int user_id
			);
	
	
	@Path("/chats/{chat}/users/{user}")
	@DELETE
    @Produces("text/json")
	public String deleteUserInChat(
			@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time, 
			@PathParam("chat") int chat_id, @PathParam("user") int user_id
			);
}
