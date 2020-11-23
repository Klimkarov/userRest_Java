
package main.java.user.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.entity.UserResponse;
import main.java.user.services.AdminService;

@Path("/admin")
public class AdminController {

  // TODO
  // 1. Delete user - only admin can delete user
  // 2. Verify user - only admin can verify user
  // 3. Get all users that are not verified - only admin can access to list with all unverified users
  // 4. Get all users - list of users with user profiles with custom response message

  @GET
  @Path("/getAllUnverified/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getAllUnverified(@PathParam("username") String username) {

    return AdminService.getAllUnverified(username);

  }

  @GET
  @Path("/getUnverified/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getUnverified(@PathParam("username") String username) {

    return AdminService.getUnverified(username);
  }

  @GET
  @Path("/getUnverifiedList/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserResponse> getList(@PathParam("username") String username) {

    return AdminService.getList(username);

  }

  @GET
  @Path("/getUserProfiles/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserProfile> getUserProfiles(@PathParam("username") String username) {

    return AdminService.getUserProfiles(username);
  }
  
  @GET
  @Path("/exportProfileAsTxt/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public String exportProfilesAsTxt(@PathParam("username") String username) throws IOException {

    return AdminService.exportProfilesAsTxt(username);
  }
  
  
// ovoj metod e samo test za Responce, nema logicki del u service //
  @GET
  @Path("/test")
  @Produces(MediaType.APPLICATION_JSON)
  public Response test() {

    List<User> res = new ArrayList<>();
    Date date = new Date(System.currentTimeMillis());
    User user = new User("username", "email", "password", date, false, false, false);

    res.add(user);

    // return Response.ok(res, MediaType.APPLICATION_JSON).build();
    // ok - definirana funkcija
    // res - od result listat koja voobicaeno sakame da ja stavime vo return
    
    
// dodavanje na HEADERS - sto kako da vraka vo headers //
    Response.ResponseBuilder rb = Response.ok(res, MediaType.APPLICATION_JSON);
    Response response = rb.header("testHeader", "test")
        .build();

    return response;

  }

}
