package main.java.user.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VoteId implements Serializable{
  
  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private User user;
  
  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private Poll poll;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Poll getPoll() {
    return poll;
  }

  public void setPoll(Poll poll) {
    this.poll = poll;
  }

  public VoteId(User user, Poll poll) {
    super();
    this.user = user;
    this.poll = poll;
  }
  
  public VoteId() {
    
  }
  
  

}
