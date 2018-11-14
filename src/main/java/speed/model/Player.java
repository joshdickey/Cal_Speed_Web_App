package speed.model;

public class Player extends Hand{
    private Hand hand;
    private String name;
    private boolean deal;
    private boolean reset;
    private String currClick, lastClick;
    private Card currentClicked, lastClicked;
    private Card lastPlaced;

    public Player(){
        deal = true;
        reset = false;
        currClick = "";
        lastClick = "";
        currentClicked = new Card();
        lastClicked = new Card();
    }

    public Player(String name) {
        this.name = name;
        hand = new Hand();
    }

    public boolean isDeal() {
        return deal;
    }

    public boolean isReset() {return reset;}

    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    public void setReset(boolean reset) {this.reset = reset;}

    public Card drawOne(){
        return hand.drawTopCard();
    }

    public Card showTopCard()
    {
        return hand.peek();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getCurrClick() {
        return currClick;
    }

    public void setCurrClick(String currClick) {
        this.currClick = currClick;
    }

    public String getLastClick() {
        return lastClick;
    }

    public void setLastClick(String lastClick) {
        this.lastClick = lastClick;
    }

    public Card getCurrentClicked() {
        return currentClicked;
    }

    public void setCurrentClicked(Card currentClicked) {
        this.currentClicked = currentClicked;
    }

    public Card getLastClicked() {
        return lastClicked;
    }

    public void setLastClicked(Card lastClicked) {
        this.lastClicked = lastClicked;
    }

    public Card getLastPlaced() {
        return lastPlaced;
    }

    public void setLastPlaced(Card lastPlaced) {
        this.lastPlaced = lastPlaced;
    }

    @Override
    public String toString() {
        return "Player{" +
                "hand=" + hand +
                ", name='" + name + '\'' +
                ", deal=" + deal +
                ", currClick='" + currClick + '\'' +
                ", lastClick='" + lastClick + '\'' +
                ", currentClicked=" + currentClicked.toString() +
                ", lastClicked=" + lastClicked.toString() +
                '}';
    }
}
