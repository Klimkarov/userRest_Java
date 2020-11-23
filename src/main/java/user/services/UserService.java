
package main.java.user.services;

import java.util.Date;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import main.java.user.entity.Address;
import main.java.user.entity.SignInRequest;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;

public class UserService {
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

	public static void createUser(User user) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Date date = new Date(System.currentTimeMillis());
			user.setCreatedOn(date);

			session.save(user);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void updateUser(Integer id, User user) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();
			Date date = new Date(System.currentTimeMillis());

			User userOldParameters = session.get(User.class, id);
			userOldParameters.setPassword(user.getPassword());
			userOldParameters.setCreatedOn(date);
			userOldParameters.setEmail(user.getEmail());

			session.update(userOldParameters);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void signIn(SignInRequest request) {

		User user = validateUser(request);  // obj user = metod validate(request) //
		                                    // gi zima vrednostito username i pass od validateUser //

		if (user != null) {
			Session session = init();
			Transaction tx = null;

			try {

				session = factory.openSession();
				tx = session.beginTransaction();

				User userOldParameters = session.get(User.class, user.getId()); // user.getId() oti nema vo @PathParam //

				userOldParameters.setSignedIn(true);

				session.update(userOldParameters);

				tx.commit();
				session.close();

			} catch (Exception e) {
				tx.rollback();
				System.out.println(e);
			}

		} else {
			System.out.println("Username or password is invalid");

		}

	}

	private static User validateUser(SignInRequest request) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();
			                                     
			Query query = session.createQuery(		
			"SELECT k FROM main.java.user.entity.User k WHERE k.username = :username AND k.password = :password");
			// Select = selekcija konkretno na koi parametri vo dadeno query
			
	/* postavuvanje na username i pass na class User, no ovoj pat username i pass mu se dodeluvaat
	 * t.e gi prima od class SignInRequest za da se proveri validnost, pa taka vo JCON koga ke piseme username i pass
	 * se pishuvaat vo JCON na SignInRequest */			
			query.setParameter("username", request.getUsername()); 
			query.setParameter("password", request.getPassword());

			User user = (User) query.getResultList().get(0);

			tx.commit();
			session.close();

			return user;

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}
	
	
	
	

	public static void signOut(String username) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username ");
			query.setParameter("username", username);

			User user = (User) query.getResultList().get(0);

			// Step by step
			// List<User> rs = query.getResultList();
			// User user = rs.get(0);

			// update user
			user.setSignedIn(false);
			session.update(user);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void createUserProfile(String username, UserProfile userProfile) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username ");
			query.setParameter("username", username);

			User user = (User) query.getResultList().get(0);
			
            // Se polnat objektite na soodvetnite entity za da moze vo JCON da gi piseme parametrite so nivni argumenti //
			// address se polni zatoa sto UserProfile e vo relacija so address //
			// User ne se ponli zatoa sto veke prethodno ima podatoci, bila creirana vo baza //
			Address address = new Address(userProfile.getAddress().getStreet(), userProfile.getAddress().getCity(),
					userProfile.getAddress().getZipCode());
			session.save(address);

			UserProfile profile = new UserProfile(userProfile.getFirstName(), userProfile.getLastName(), address,
					userProfile.getAge(), userProfile.getInterests(), user);
			session.save(profile);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void updateProfile(String username, UserProfile userProfile) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username");
			query.setParameter("username", username);

			User user = (User) query.getResultList().get(0);

			// check if user can change its own profile
			 if (user.getId() == userProfile.getUser().getId()) { 
		// so ovoj kod mora vo JCON da se stavat id za sekoj objekt  i site parametri i za Class USER //
				 
		//   if (user.getId() == userProfile.getId()) so ovoj kod moze samo vo JCON param na Address i UserProfile
				 
				Address addressOldValues = session.get(Address.class, userProfile.getAddress().getId());
				addressOldValues.setCity(userProfile.getAddress().getCity());
				addressOldValues.setZipCode(userProfile.getAddress().getZipCode());
				addressOldValues.setStreet(userProfile.getAddress().getStreet());
				
				session.update(addressOldValues);

				
				UserProfile userProfileOldValues = session.get(UserProfile.class, userProfile.getId());

				userProfileOldValues.setFirstName(userProfile.getFirstName());
				userProfileOldValues.setLastName(userProfile.getLastName());
				userProfileOldValues.setAge(userProfile.getAge());
				userProfileOldValues.setInterests(userProfile.getInterests());
				
				session.update(userProfileOldValues);

				tx.commit();
				session.close();

			} else {

				System.out.println("User with username: " + username + "can only update its own profile");
			}

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	// create user profile
	// 1. url parameter username
	// 2. get User by username from database in User object
	// 3. set User in userProfile constructor
	// 4. save user profile

}
