
package main.java.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import main.java.user.entity.Choices;
import main.java.user.entity.Poll;
import main.java.user.entity.PollChoicesRequest;
import main.java.user.entity.PollChoicesResponse;
import main.java.user.entity.User;

public class PollService {
  static SessionFactory factory;

  public static Session init() {

    Configuration cfg = new Configuration();
    cfg.addAnnotatedClass(main.java.user.entity.Address.class);
    cfg.addAnnotatedClass(main.java.user.entity.User.class);
    cfg.addAnnotatedClass(main.java.user.entity.UserProfile.class);
    cfg.addAnnotatedClass(main.java.user.entity.Poll.class);
    cfg.addAnnotatedClass(main.java.user.entity.Choices.class);
    cfg.addAnnotatedClass(main.java.user.entity.Votes.class);
    cfg.configure();

    factory = cfg.configure()
        .buildSessionFactory();
    Session session = factory.openSession();
    return session;

  }

  public static Integer createPoll(String username, PollChoicesRequest pollRequest) {
    Session session = init();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      User user = getUserByUsername(username);

      Date date = new Date(System.currentTimeMillis());

      Poll poll = new Poll(pollRequest.getPoll().getTopic(), user, date);

      session.save(poll);

      for (Choices choise : pollRequest.getChoices()) {

        choise.setPoll(poll);
        session.save(choise);

      }

      tx.commit();
      session.close();
      return poll.getId();

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }

  private static void createChoise(Poll poll, String choice) {
    // TODO Auto-generated method stub

  }

  private static User getUserByUsername(String username) {
    Session session = init();
    Transaction tx = null;

    try {

      session = factory.openSession();
      tx = session.beginTransaction();

      // get logged in user
      Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username ");
      query.setParameter("username", username);

      User user = (User) query.getResultList()
          .get(0);

      tx.commit();
      session.close();
      return user;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }

  public static List<PollChoicesResponse> getPolls() {
    Session session = init();
    Transaction tx = null;

    List<PollChoicesResponse> result = new ArrayList<PollChoicesResponse>();

    try {

      session = factory.openSession();
      tx = session.beginTransaction();

      Query queryPolls = session.createQuery("FROM main.java.user.entity.Poll p");
      List<Poll> polls = queryPolls.getResultList();

      for (Poll poll : polls) {

        Query queryQuestions = session.createQuery("FROM main.java.user.entity.Choices c JOIN c.poll p WHERE p.id = :id");
        queryQuestions.setParameter("id", poll.getId());
        List<Choices> choices = queryQuestions.getResultList();

        PollChoicesResponse response = new PollChoicesResponse();
        response.setPoll(poll);
        response.setChoices(choices);
        response.setCreatedBy(poll.getUser().getUsername());

        result.add(response);

      }

      tx.commit();
      session.close();

      return result;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }

  public static Poll getPollById(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  
  
  public static List<Poll> getPollCreatedBy(String username) {
    Session session = init();
    Transaction tx = null;

    try {

      session = factory.openSession();
      tx = session.beginTransaction();
      Date date = new Date(System.currentTimeMillis());

      Query queryUser = session
          .createQuery("SELECT k FROM main.java.user.entity.User k WHERE k.username = :username");
      queryUser.setParameter("username", username);
      User user = (User) queryUser.getResultList().get(0);

      Query queryPolls = session.createQuery("SELECT p FROM main.java.user.entity.Poll p JOIN p.user u WHERE u.id = :id ");
      queryPolls.setParameter("id", user.getId());
      List<Poll> polls = queryPolls.getResultList();

      
      tx.commit();
      session.close();
      
      return polls;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }

}
