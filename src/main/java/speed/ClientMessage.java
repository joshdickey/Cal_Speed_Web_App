package speed;

import speed.model.Card;

import java.util.ArrayList;

public class ClientMessage {

    private static ClientMessage instance = null;
    String messageType;
    String clientName;
    int playerCount;
    String playerName1, playerName2;
    ArrayList<Card> cardsOnBoard1, cardsOnBoard2;
    public boolean deal;
    Card topCard1, topCard2;
    String placedOnCard, placedOnValue;


    public static ClientMessage getInstance() {
        if (instance == null){
            instance = new ClientMessage();
        }
        return instance;
    }

    private ClientMessage() {
        playerCount = 0;
        playerName1 = "Player 1";
        playerName2 = "Player 2";
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public void setPlayerName1(String playerName1) {
        this.playerName1 = playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public void setPlayerName2(String playerName2) {
        this.playerName2 = playerName2;
    }

    public String getmessageTypee() {
        return messageType;
    }

    public void setmessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public ArrayList<Card> getCardsOnBoard1() {
        return cardsOnBoard1;
    }

    public void setCardsOnBoard1(ArrayList<Card> cardsOnBoard1) {
        this.cardsOnBoard1 = cardsOnBoard1;
    }

    public ArrayList<Card> getCardsOnBoard2() {
        return cardsOnBoard2;
    }

    public void setCardsOnBoard2(ArrayList<Card> getCardsOnBoard2) {
        this.cardsOnBoard2 = getCardsOnBoard2;
    }

    public boolean getDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    public Card getTopCard1() {
        return topCard1;
    }

    public void setTopCard1(Card topCard1) {
        this.topCard1 = topCard1;
    }

    public Card getTopCard2() {
        return topCard2;
    }

    public void setTopCard2(Card topCard2) {
        this.topCard2 = topCard2;
    }

    public String getPlacedOnCard() {
        return placedOnCard;
    }

    public void setPlacedOnCard(String placedOnCard) {
        this.placedOnCard = placedOnCard;
    }

    public String getPlacedOnValue() {
        return placedOnValue;
    }

    public void setPlacedOnValue(String placedOnValue) {
        this.placedOnValue = placedOnValue;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "messageType='" + messageType + '\'' +
                ", clientName='" + clientName + '\'' +
                ", playerCount=" + playerCount +
                ", playerName1='" + playerName1 + '\'' +
                ", playerName2='" + playerName2 + '\'' +
                ", cardsOnBoard1=" + cardsOnBoard1 +
                ", cardsOnBoard2=" + cardsOnBoard2 +
                ", deal=" + deal +
                ", topCard1=" + topCard1 +
                ", topCard2=" + topCard2 +
                ", placedOnCard='" + placedOnCard + '\'' +
                ", placedOnValue='" + placedOnValue + '\'' +
                '}';
    }
}
