package main.java.user.entity;

import java.util.List;

public class PollChoicesRequest {
  
  private Poll poll;
  
  private List<Choices> choices;
  
  
  public Poll getPoll() {
    return poll;
  }
  public void setPoll(Poll poll) {
    this.poll = poll;
  }
  public List<Choices> getChoices() {
    return choices;
  }
  public void setChoices(List<Choices> choices) {
    this.choices = choices;
  }
  
  

}
