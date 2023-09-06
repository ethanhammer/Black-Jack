import java.util.*;

public class Player extends Dealer{


  int bal;

  int bet;

  boolean playing;

  int playerNumber;

  
  public Player(int playerNumber) {

    super();
    
    this.bal = 20;

    this.bet = 0;

    this.playerNumber = playerNumber;

    
  }

  public void setBet(int bet) {
    this.bet = bet;

  }

  public int getBal() {
    return bal;
  }
  
  public void setBal(int bal) {
    this.bal = bal;
  }

  public void updateWinnings() {
    bal += bet*2;
  }
  public int seeBet() {
    return bet;
  }

  
}