package speed.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> playersHand;
    private int handCount = 0;


    public Hand() {
        playersHand = new ArrayList<>();
    }

    public Hand(ArrayList<Card> cards){
        playersHand = cards;
        handCount = playersHand.size();
    }

    public ArrayList<Card> getPlayersHand() {
        return playersHand;
    }

    public void setPlayersHand(ArrayList<Card> playersHand) {
        this.playersHand = playersHand;
        this.handCount = this.playersHand.size();
    }

    public int getHandCount() {
        return handCount;
    }

    public void setHandCount(int handCount) {
        this.handCount = handCount;
    }

    public void addCardToHand(Card card){
        playersHand.add(card);
        handCount = playersHand.size();
    }

    public Card removeCardFromHand(Card card){
        int value = card.getValue();
        Card cardInHand;

        //iterates through hand and returns first matching card with the same value and removes it from hand
        for (int i = 0; i < handCount; i++){
            cardInHand = playersHand.get(i);
            if (cardInHand.getValue() == value){
                playersHand.remove(i);
                handCount = playersHand.size();
                return cardInHand;
            }
        }
        //card was not found in hand
        return cardInHand = null;
    }

    private Card checkForCard(Card card){
        int value = card.getValue();
        Card cardInHand;

        //iterates through hand and returns first matching card with the same value
        for (Card c : playersHand) {
            cardInHand = c;
            if (c.getValue() == value){
                return cardInHand;
            }
        }
        //card was not found in hand
        return cardInHand = null;
    }

    @Override
    public String toString() {
        return "Hand{" +
                ", handCount=" + handCount +
                "playersHand=" + playersHand +
                '}';
    }

    protected Card drawTopCard() {
        handCount--;
        return playersHand.remove(0);
    }

    public Card peek() {
        return playersHand.get(0);
    }
}
