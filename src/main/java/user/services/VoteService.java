
package main.java.user.services;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import main.java.user.entity.Choices;
import main.java.user.entity.Poll;
import main.java.user.entity.User;
import main.java.user.entity.VoteId;
import main.java.user.entity.VoteResultResponse;
import main.java.user.entity.Votes;

public class VoteService {

  static SessionFactory factory;

  public static Session init() {

    Configuration cfg = new Configuration();
    cfg.addAnnotatedClass(main.java.user.entity.Address.class);
    cfg.addAnnotatedClass(main.java.user.entity.User.class);
    cfg.addAnnotatedClass(main.java.user.entity.UserProfile.class);
    cfg.addAnnotatedClass(main.java.user.entity.Poll.class);
    cfg.addAnnotatedClass(main.java.user.entity.Choices.class);
    cfg.addAnnotatedClass(main.java.user.entity.Votes.class);
    cfg.addAnnotatedClass(main.java.user.entity.VoteId.class);
    cfg.configure();

    factory = cfg.configure()
        .buildSessionFactory();
    Session session = factory.openSession();
    return session;

  }

  public static String castVote(String username, Integer pollId, Integer choiseId) {

    Session session = init();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      User user = getUserByUsername(username);

      Choices choice = session.get(Choices.class, choiseId);

      Poll poll = session.get(Poll.class, pollId);
      
      VoteId voteId = new VoteId(user, poll);

      Votes vote = new Votes(voteId, choice);

      session.save(vote);

      tx.commit();
      session.close();

      return "User " + user.getUsername() + ", voted for poll: " + poll.getTopic();
    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return "User was unable to vote" + e.getMessage();
    }

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

  public static VoteResultResponse countVotes(Integer pollId) {
    
    Session session = init();
    Transaction tx = null;
    VoteResultResponse response = new VoteResultResponse();
    
    
    try {

      session = factory.openSession();
      tx = session.beginTransaction();
      
      //Get total votes for one poll
      String sql = "SELECT COUNT(*) FROM public.votes WHERE public.votes.poll_id= " + pollId +";";
      SQLQuery nativeQuery = session.createSQLQuery(sql);
      BigInteger data = (BigInteger) nativeQuery.getSingleResult();
      
      response.setTotalVotes(data.longValue());
      
      
      //get All choices with provided poll id
      Query queryQuestions = session.createQuery("FROM main.java.user.entity.Choices c WHERE c.poll.id = :id");
      queryQuestions.setParameter("id", pollId);
      
      List<Choices> listChoices = queryQuestions.getResultList();
      
      Long maxVotes = 0L;
      
      //count maximum votes for one choice
      for (Choices choice : listChoices) {
        
        Query query = session.createQuery("SELECT COUNT(*) FROM main.java.user.entity.Votes v JOIN v.choices c WHERE c.id=:id ");//HQL 
        query.setParameter("id", choice.getId());
        
        Long countVoteByChoice = (Long) query.getSingleResult();
        
        if(maxVotes  < countVoteByChoice) {
          maxVotes = countVoteByChoice;
          response.setWinnerChoice(choice);
        }
        
        
      }
      
      response.setChoiseVotes(maxVotes);
      
     
      
      
      //get total counts
//      Query queryTotalCounts = session.createQuery("SELECT COUNT(*) FROM main.java.user.entity.Votes JOIN FETCH main.java.user.entity.Votes.id.poll p WHERE p.id=:id");//HQL 
//      query.setParameter("id", pollId);
//      
     
      
     
      
//      response.setWinnerChoice(session.get(Choices.class, ));
      


      tx.commit();
      session.close();
      return response;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }
  }

}
