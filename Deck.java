import java.util.*;

public class Deck extends Data {

  ArrayList<Integer> deck = new ArrayList<Integer>();
  Random rand = new Random();
  
  public Deck() {
     resetDeck();
    shuffleDeck();
  }
  
  public void resetDeck() {
    for (int i = 0; i < deck.size(); i ++) {
      deck.remove(i);
    }
    for (int i = 0; i < defaultDeck.length; i++) {
      deck.add(defaultDeck[i]);
    }
  }
  
  public void shuffleDeck() {
   Collections.shuffle(deck);
  }
  
  public int randomCard() {
    int num = rand.nextInt(deck.size());
    int card = deck.get(num);
    deck.remove(num);
    return card;
    
  }
}