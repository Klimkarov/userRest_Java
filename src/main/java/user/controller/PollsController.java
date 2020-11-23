package main.java.user.controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.user.entity.Poll;
import main.java.user.entity.PollChoicesRequest;
import main.java.user.entity.PollChoicesResponse;
import main.java.user.entity.User;
import main.java.user.services.PollService;
import main.java.user.services.UserService;

@Path("/poll")
public class PollsController {
  
  //createPoll
  @POST
  @Path("/{username}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createPoll(@PathParam(value="username")String username, PollChoicesRequest pollRequest) {
    
    Integer pollId = PollService.createPoll(username, pollRequest);
    
    return "Poll with id " + pollId + " is created successfully";
  }

  
  //getPollById
//  @GET
//  @Path("/{id}")
//  @Consumes(MediaType.APPLICATION_JSON)
//  public Poll getPollById(@PathParam(value = "id")Integer id) {
//    return PollService.getPollById(id);
//  }
  
  //getAllPolls
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<PollChoicesResponse> getPolls() {
    return PollService.getPolls();
  }
  
  //getAllPollsCreatedInLast 1 day
  
  //update Poll
  
  //deletePoll
  
  //getAllPollsCreatedByUser
  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Poll> getPollCreatedBy(@PathParam(value = "username")String username) {
    return PollService.getPollCreatedBy(username);
  }
  

  //getAllPollsVotedByUser
  
}
