package main.java.user.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.user.entity.SignInRequest;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.services.UserService;

@Path("/controller")
public class UserController {

	/*
	 * User Rest controller:
	 * 
	 * 1. Create user 2. Update user can delete user 4. Sign in user 5. Sign out
	 * user
	 */
//TODO
	//1. Create user profile
	//2. Update user profile
	
	
	
	
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(User user) {
		UserService.createUser(user);
		return "User " + user.getUsername() + " is created successfully";
	}

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(@PathParam("id") Integer id, User user) {

		UserService.updateUser(id, user);
		return "";
	}

	/* Obicno koga pravi signIn - se najavuva user koj prethodno ima napraveno profil so svoi pod vo aplikacijata
	 za signIn trebaat samo username i pass, pa taka ako ovie se isti so username i pass na userot 
	 togas ke bide validno, za ova ne ni e potrebna cela User class tuku druga nova nadvoreshna clasa - signInRequest
	 so ovie 2 parametri */
	@POST
	@Path("/signIn")
	@Consumes(MediaType.APPLICATION_JSON)
	public String signIn(SignInRequest request) { // SignInRequest nadvoreshna klasa za username i pass //
		UserService.signIn(request);
		return "User " + request.getUsername() + "has been logged in";

	}
	
	/* za signOut potrebno e da se znae samo username i ke bide get metoda, zatoa sto veke ovoj username postoi
	 * prethodno od signIn, odtuka ako username e ist togas so pomos na ovoj metod ke moze da se napravi signOut
	*/
	@GET
	@Path("/signOut/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String signOut(@PathParam("username") String username) {
		
		UserService.signOut(username);
		return "User " + username + " has been sign out"; 
	}
	
	
	
	@POST
	@Path("/createUserProfile/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUserProfile(@PathParam("username") String username, UserProfile userProfile) {
		
		UserService.createUserProfile(username, userProfile);
		return "User profile is created";
	}
	
	
  @PUT
  @Path("/updateUserProfile/{username}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateProfile(@PathParam("username") String username, UserProfile userProfile) {
	  
	  UserService.updateProfile(username, userProfile);
    return "User profile with username: " + username + " is updated";
  }
	
	

}
