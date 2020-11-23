package main.java.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "choices")
public class Choices {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  
  private String choice;
  
  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private Poll poll;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getChoice() {
    return choice;
  }

  public void setChoice(String choice) {
    this.choice = choice;
  }

  public Poll getPoll() {
    return poll;
  }

  public void setPoll(Poll poll) {
    this.poll = poll;
  }

  public Choices(String choice, Poll poll) {
    super();
    this.choice = choice;
    this.poll = poll;
  }
  
  public Choices () {
    
  }
  

}
