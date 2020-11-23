
package main.java.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
public class Votes {

  // @Id
  // @GeneratedValue(strategy=GenerationType.IDENTITY)
  // private Integer id;

  // @ManyToOne
  // @JoinColumn(referencedColumnName = "id")
  // private User user;
  //
  // @ManyToOne
  // @JoinColumn(referencedColumnName = "id")
  // private Poll poll;

  @EmbeddedId
  private VoteId id;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private Choices choices;

  public VoteId getId() {
    return id;
  }

  public void setId(VoteId id) {
    this.id = id;
  }

  public Choices getChoices() {
    return choices;
  }

  public void setChoices(Choices choices) {
    this.choices = choices;
  }

  public Votes(VoteId id, Choices choices) {
    super();
    this.id = id;
    this.choices = choices;
  }

  public Votes() {

  }

}
