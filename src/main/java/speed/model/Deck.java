package speed.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    //private static Deck instance;

    //returns the static Deck object
   /* public static Deck getDeck(){
        if (instance == null){
            instance = new Deck();
        }
        return instance;
    }*/

    public Deck() {
       // int count = 0;
        //creates and adds 52 cards to the deck
        for (int i = 1; i <= 13; i++) {



            for (int j = 0; j < 4; j++) {
                Card card = new Card(i);

                if (j == 0){
                    card.setSuit("C");
                }
                if (j == 1 ){
                    card.setSuit("D");
                }
                if (j == 2){
                    card.setSuit("H");
                }
                if (j == 3 ){
                    card.setSuit("S");
                }
                deck.add(card);

            }
          //  count++;

        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card deal() {
        Card card = null;
        if (deck.size() > 0) {
            card = deck.remove(0);
        }
        return card;
    }

    public Deck reset() {
        Deck temp = new Deck();
        return temp;
    }

    public Card peek() {
        Card card = deck.get(0);
        return card;
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public int size() {
        return deck.size();
    }
}
