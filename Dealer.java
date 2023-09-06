import java.util.*;

public class Dealer {

  ArrayList<Integer> cards = new ArrayList<Integer>();

  boolean busted;
  
  public Dealer() {
  this.busted = false;
}
  public void setBusted(boolean busted) {
    this.busted = busted;
  }
  public boolean seeBusted() {
    return busted;
  }
  public int getValueOfCards() {
    int total = 0;
    for (int i = 0; i < cards.size(); i++) {
      total += cards.get(i);
    }
    return total;
  }
  public boolean checkBusted() {

    return (getValueOfCards() > 21);
  }
  public void addCard(int card) {
    cards.add(card);
  }

  public void resetCards() {
    for (int i = 0; i < cards.size(); i++) {
      cards.remove(i);
    }
  }

  public void showFirst() {
    System.out.println("The Dealer's first card is: |" + cards.get(0) + "|");
  }

  public void printCards() {
    for (int i = 0; i < cards.size(); i++) {
      System.out.print("|"+ cards.get(i) + "| ");
    }
  }
}