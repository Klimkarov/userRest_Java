package main.java.user.entity;

import java.util.List;

public class PollChoicesResponse {
  
  private Poll poll;
  private List<Choices> choices;
  private String createdBy;
   
  
  
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
  public String getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  
  

}
