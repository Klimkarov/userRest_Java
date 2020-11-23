package main.java.user.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.java.user.entity.SignInRequest;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.entity.UserResponse;

public class AdminService {
	static SessionFactory factory;

	public static Session init() {

		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(main.java.user.entity.Address.class);
		cfg.addAnnotatedClass(main.java.user.entity.User.class);
		cfg.addAnnotatedClass(main.java.user.entity.UserProfile.class);
		cfg.configure();

		factory = cfg.configure().buildSessionFactory();
		Session session = factory.openSession();
		return session;

	}



	public static List<User> getAllUnverified(String username) {
		

		if (checkIfAdmin(username)) {

			return getAllNotVerifiedUsers();

		} else {
			System.out.println("User is not administrator");
			return null;
		}

	}
	
	private static boolean checkIfAdmin(String username) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username AND k.isAdmin=:isAdmin");
			query.setParameter("username", username);
			query.setParameter("isAdmin", true);

			User user = (User) query.getResultList().get(0);

			// Step by step
			// List<User> rs = query.getResultList();
			// User user = rs.get(0);
			tx.commit();
			session.close();

			if (user != null) {
				return true;
			}

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
		return false;
	}


	private static List<User> getAllNotVerifiedUsers() {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified ");
			query.setParameter("verified", false);

			List<User> users = query.getResultList();

			tx.commit();
			session.close();
			return users;

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
			return null;
		}
	}

	
	public static List<User> getUnverified(String username) {

		if (isAdministrator(username)) {
			return getUnverifiedUsers();
		} else {
			System.out.println("User is not admin");
		}

		return null;

	}

	private static List<User> getUnverifiedUsers() {
		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified");
			query.setParameter("verified", false);

			List<User> res = query.getResultList();

			tx.commit();
			session.close();

			return res;
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;

	}

	private static boolean isAdministrator(String username) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			// get logged in user
			Query query = session
					.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username AND k.isAdmin=:isAdmin");
			query.setParameter("username", username);
			query.setParameter("isAdmin", true);

			User user = (User) query.getResultList().get(0);

			tx.commit();
			session.close();

			if (user != null) {
				return true;
			}

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
		return false;

	}

	public static List<UserResponse> getList(String username) {
		
		if (isAdministrator(username))
			return getUnverifiedUsernames();

		else {
			System.out.println("User is not admin");
			return null;
		}

	}

	private static List<UserResponse> getUnverifiedUsernames() {

		Session session = init();
		Transaction tx = null;

		List<UserResponse> result = new ArrayList<>();

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified");
			query.setParameter("verified", false);

			List<User> res = query.getResultList();

			for (User user : res) {

				UserResponse response = new UserResponse();
				response.setId(user.getId());
				response.setUsername(user.getUsername());

				result.add(response);

			}

			tx.commit();
			session.close();

			return result;
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}

	public static List<UserProfile> getUserProfiles(String username) {

		if (isAdministrator(username))
			return getAllUserProfiles();
		else {
			System.out.println("User is not admin");
			return null;
		}

	}

	private static List<UserProfile> getAllUserProfiles() {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("SELECT p FROM main.java.user.entity.UserProfile p ");
			List<UserProfile> res = query.getResultList();

			tx.commit();
			session.close();
			return res;
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}

  public static String exportProfilesAsTxt(String username) throws IOException {
    // TODO Auto-generated method stub
    
    
    if (isAdministrator(username))
      return getAllUserProfilesAsTxt();
    else {
      System.out.println("User is not admin");
      return null;
    }
    
  }

  private static String getAllUserProfilesAsTxt() throws IOException {
    
    
    List<UserProfile> profiles = getAllUserProfiles();
    
    String path = "C:\\Users\\iskratel\\Documents\\workspace-spring-tool-suite-4-4.1.1.RELEASE\\localComutation\\userRest\\src.main.resources\\userProfiles.txt";
    
    File file = new File(path);
    
    if (!file.exists()) {
      file.createNewFile();
    }
    
    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    
    
    for (UserProfile userProfile : profiles) {
      
      String content = " Username: " + userProfile.getUser().getUsername() + ", created on: " + userProfile.getUser().getCreatedOn() + "\r\n" + 
      " First name: " + userProfile.getFirstName() + "\r\n"
          
      + "Last name: " + userProfile.getLastName() + "\r\n "
      
      + "*************************************************" + "\r\n " ;
      
      bw.append(content);
    }
    
    
    bw.close();
    
    
    return "File location: " + path;
  }

	// create user profile
	// 1. url parameter username
	// 2. get User by username from database in User object
	// 3. set User in userProfile constructor
	// 4. save user profile

}
