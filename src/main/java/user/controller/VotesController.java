
package main.java.user.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.user.entity.VoteResultResponse;
import main.java.user.services.VoteService;

@Path("/vote")
public class VotesController {

  // castVote from User to Poll
  @GET
  @Path("/{username}/{pollId}/{choiseId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String castVote(@PathParam("username") String username, @PathParam("pollId") Integer pollId,
      @PathParam("choiseId") Integer choiseId) {

    return VoteService.castVote(username, pollId, choiseId);

  }

  // getVotesVotedByUser

  // countVotesByPoll
  @GET
  @Path("/result/{pollId}")
  @Produces(MediaType.APPLICATION_JSON)
  public VoteResultResponse countVotes(@PathParam("pollId") Integer pollId) {

    return VoteService.countVotes(pollId);

  }

}
