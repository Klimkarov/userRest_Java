package main.java.user.entity;

public class VoteResultResponse {
  
  private Long totalVotes;
  private Long choiseVotes;
  private Choices winnerChoice;
  
  public Long getTotalVotes() {
    return totalVotes;
  }
  public void setTotalVotes(Long totalVotes) {
    this.totalVotes = totalVotes;
  }
  public Long getChoiseVotes() {
    return choiseVotes;
  }
  public void setChoiseVotes(Long choiseVotes) {
    this.choiseVotes = choiseVotes;
  }
  public Choices getWinnerChoice() {
    return winnerChoice;
  }
  public void setWinnerChoice(Choices winnerChoice) {
    this.winnerChoice = winnerChoice;
  }
  public VoteResultResponse(Long totalVotes, Long choiseVotes, Choices winnerChoice) {
    super();
    this.totalVotes = totalVotes;
    this.choiseVotes = choiseVotes;
    this.winnerChoice = winnerChoice;
  }
  
  
  public VoteResultResponse() {
    
  }
  

}
