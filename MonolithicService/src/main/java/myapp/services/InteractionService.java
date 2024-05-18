package myapp.services;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/interaction")
public interface InteractionService {
	
	@Path("/chats")
	@GET
    @Produces("text/json")
	public String getChats(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time);
	
	@Path("/chats/{chat}")
	@GET
    @Produces("text/json")
	public String getChat(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("chat") int chat_id);
	
	@Path("/chats/{chat}/messages")
	@POST
    @Produces("text/json")
	public String postMesssage(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time,
			@PathParam("chat") int chat_id, @PathParam("json") String message);
	
	@Path("/chats/{chat}/messages/{message}")
	@DELETE
    @Produces("text/json")
	public String deleteMessage(@QueryParam("user_id") int id, @QueryParam("token") String token, @QueryParam("time") long time
			, @PathParam("chat") int chat_id, @PathParam("message") int message_id);
}
