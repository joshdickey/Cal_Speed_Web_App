package testsocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    private static Game instance = null;
    private ClientMessage clientMessage = ClientMessage.getInstance();
    private Deck deck = Deck.getDeck();
    private static Player player1, player2;

    private List<Session> sessions = new ArrayList<>();
    private int playerCount = 0;
    private Hand boardHand1, totalBoardhand1;
    private Hand boardhand2, totalBoardhand2;
    private Hand hand;
    private Hand hand2;
    private HashMap<String, Card> hashMap;
    private Card currentCardClicked;
    private Card lastCardClicked;

    public  synchronized void join(Session session) {
        sessions.add(session);
        session.getUserProperties();

        System.out.println("Game: join " + playerCount);
        System.out.println("join: Session properties: " + session.getUserProperties());
        playerCount++;

    }
    public synchronized void leave(Session session){
        sessions.remove(session);
    }

    public void updateGame(ClientMessage message) {

        clientMessage = message;

        //sets player names in the client message
        clientMessage.setPlayerName2(player2.getName());
        clientMessage.setPlayerName1(player1.getName());

        //sets messageType to Wait

        System.out.println("updateGame(): " + clientMessage.toString());
        //sends payload to clients
        sendToAll(clientMessage);
    }

    //game is a Singleton
    public synchronized static Game getGame(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    //Sends server message to all open clients as JSON
    private synchronized void sendToAll(ClientMessage message){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();
        System.out.println("\nsendToAll()");
        for (Session session :
                sessions) {
            if(session.isOpen()){
                try{session.getBasicRemote().sendText(gson.toJson(message));}
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addPlayer(ClientMessage message) {

        clientMessage = message;
        //set player count
        clientMessage.setPlayerCount(playerCount);
        //creates both players and gives them names
        if (clientMessage.getPlayerCount() == 1){

            player1 = new Player();
            player2 = new Player();
            player1.setName(clientMessage.getClientName());
            player2.setName(clientMessage.getPlayerName2());
            player1.setDeal(true);
            updateGame(clientMessage);
        }
        if (clientMessage.getPlayerCount() == 2){
            player2.setDeal(true);
            player2.setName(clientMessage.getClientName());
            //game now has 2 players and is ready to be set up
            setupGame();
        }

    }

    public ClientMessage getGameStats(){
        return clientMessage;
    }

    private void setupGame(){

        hand = new Hand();
        hand2 = new Hand();
        boardHand1 = new Hand();
        boardhand2 = new Hand();
        totalBoardhand2 = new Hand();
        totalBoardhand1 = new Hand();

        hashMap = new HashMap<>();

        //deal to both players
        for (int i = 0; i < 26; i++){
            hand.addCardToHand(deck.deal());
            hand2.addCardToHand(deck.deal());
        }
        player1.setHand(hand);
        player2.setHand(hand2);

        dealCards();
    }

    //dael cards from players hand to the board
    private void dealCards(){

        //clears cards on board
        boardHand1.getPlayersHand().clear();
        boardhand2.getPlayersHand().clear();

        //TODO added hashMap but still needs to convert the boardHand arrays over
        hashMap.clear();

        System.out.println("DEALING new cards from hand");

        //checks if both players are ready to deal out cards
        if (player1.isDeal() && player2.isDeal()){

            //players deal 4 each on board
            for (int i = 0; i < 4; i++) {

                Card temp1 = player1.drawOne();
                Card temp2 = player2.drawOne();

                //adds cards from players hand to boardHand
                boardHand1.addCardToHand(temp1);
                boardhand2.addCardToHand(temp2);
                //keeps a running total of the cards stacked on the board. not sure why I made this actually
                totalBoardhand1.addCardToHand(temp1);
                totalBoardhand2.addCardToHand(temp2);

                //hashMap that correlates the cards on the board with the button they are associated with
                hashMap.put("btn" + i, temp1);
                hashMap.put("btn" + (i + 4), temp2);

            }

            flagMatches();

            System.out.println("dealCards() HashMap: " + hashMap);

            //set the 8 cards that go on the board in the payload to be sent to the client
           // clientMessage.setCardsOnBoard1(boardHand1.getPlayersHand());
           // clientMessage.setCardsOnBoard2(boardhand2.getPlayersHand());
            //flag that the players are no longer ready to deal out cards
            player1.setDeal(false);
            player2.setDeal(false);
           /* System.out.println("\n\nafter dealCards(): " + clientMessage.toString() + "\n" +
                    "player 1: " + player1.toString() + "\n" +
                    "player 2: " + player2.toString());*/

        }

        clientMessage.setTopCard1(player1.showTopCard());
        clientMessage.setTopCard2(player2.showTopCard());
        updateGame(clientMessage);
    }

    /*
    * Sets correct player's deal variable as true
    * */
    public void setPlayerReadyToDeal(ClientMessage message) {

        clientMessage = message;
        /*System.out.println("setPlayerReadyToDeal(): " + clientMessage.toString() + "\n" +
               "player 1: " + player1.toString() + "\n" +
                "player 2: " + player2.toString());*/

        if (clientMessage.clientName.equals(clientMessage.playerName1) && clientMessage.deal){
            player1.setDeal(true);
        }
        if (clientMessage.clientName.equals(clientMessage.playerName2) && clientMessage.deal){
            player2.setDeal(true);
        }

        //deals new cards from hand
        dealCards();
    }

    public void match(ClientMessage message){

        clientMessage = message;
        int cardIndex = Integer.parseInt(message.placedOnCard.substring(3));
        String targetCardBtn = message.placedOnCard;

        //assigns the current player depending on which client is placing cards
        Player currPlayer = (message.clientName.equals(player1.getName())) ? player1 : player2;

        if (hashMap.get(targetCardBtn).isHasMatch()) {


            //draws card from hand
            Card playersTopCard = currPlayer.drawOne();
            // playersTopCard.setPlacedOnMatch(true);

            //updates current and last clicked cards for player
            currPlayer.setLastClicked(currPlayer.getCurrentClicked());
            currPlayer.setCurrentClicked(hashMap.get(targetCardBtn));

            System.out.println("replacing: " + currPlayer.getCurrentClicked().toString() + " With: " + playersTopCard.toString());

            //replace target card with top card from players hand
            hashMap.put(targetCardBtn, playersTopCard);

            //updated appropriate board Hand TODO duplicate code from flagMatches()
            if (cardIndex < 4) {
                boardHand1.getPlayersHand().set(cardIndex, playersTopCard);
                clientMessage.setCardsOnBoard1(boardHand1.getPlayersHand());
            }
            if (cardIndex >= 4) {
                boardhand2.getPlayersHand().set(cardIndex - 4, playersTopCard);
                clientMessage.setCardsOnBoard2(boardhand2.getPlayersHand());
            }

            //check to see if player got a match
            if (isThereMatch(currPlayer)) {
                System.out.println("\n\nMATCH\n\n");
                clientMessage.setMessageType("MATCH");
            }else {
                clientMessage.setMessageType("PLACED");
            }
            //TODO think about where this goes
            flagMatches();

        }else {
            clientMessage.setMessageType("REJECT");
        }

        clientMessage.setTopCard1(player1.showTopCard());
        clientMessage.setTopCard2(player2.showTopCard());
        updateGame(clientMessage);

    }

    private boolean isThereMatch(Player currPlayer) {
        Card blankCard = new Card();
        Player otherPlayer = (clientMessage.clientName.equals(player1.getName())) ? player2 : player1;

        if (currPlayer.getLastClicked().getNumber() == currPlayer.getCurrentClicked().getNumber() && currPlayer.getLastClicked().getNumber() != -1){
            System.out.println(currPlayer.getName() + " has found a match");
            currPlayer.setLastClicked(blankCard);
            currPlayer.setCurrentClicked(blankCard);
            return true;
        }
        if (currPlayer.getCurrentClicked().getNumber() == otherPlayer.getCurrentClicked().getNumber() && otherPlayer.getCurrentClicked().getNumber() != -1){
            System.out.println(currPlayer.getName() + " has found a match with " + otherPlayer.getName());
            currPlayer.setCurrentClicked(blankCard);
            otherPlayer.setCurrentClicked(blankCard);
            return true;
        }
        return false;
    }

    private void flagMatches(){
        String prefix = "btn";
        for (int i = 0; i< 8; i++){
            Card temp = hashMap.get(prefix + i);

            for (int j = 0; j < 8; j++){
                Card temp2 = hashMap.get(prefix + j);
                if (temp.getNumber() == temp2.getNumber() && i != j){
                    temp.addMatch(temp2);
                    temp.setHasMatch(true);
                    System.out.println("\nflagMatches() " + temp.toString() + " AND " + temp2.toString());
                }
                hashMap.put(prefix + i, temp);
            }
            if (i < 4){
                boardHand1.getPlayersHand().set(i, temp);
                clientMessage.setCardsOnBoard1(boardHand1.getPlayersHand());
            }
            if (i >= 4){
                boardhand2.getPlayersHand().set(i - 4 , temp);
                clientMessage.setCardsOnBoard2(boardhand2.getPlayersHand());
            }
        }

    }


    public void checkForMatch(ClientMessage message) {


        clientMessage = message;

        String cardIndex = message.placedOnCard.substring(3);
        //String cardIndex = message.placedOnCard;

        //TODO need to be able to either revert placed card to original card if not a match or not allow card placement if there there is no match on the board
        if (message.clientName.equals(message.playerName1)) {
            System.out.println("before checking match: LastClick " + player1.getLastClick() + " CurrentClick: " + player1.getCurrClick() );
            player1.setLastClick(player1.getCurrClick());
            player1.setCurrClick(message.placedOnCard);

            player1.setLastClicked(player1.getCurrentClicked());
            player1.setCurrentClicked(hashMap.get(message.placedOnCard));

            System.out.println("\nAfter checking match: LastClick " + player1.getLastClick() + " CurrentClick: " + player1.getCurrClick() );
           // System.out.println("checkForMatch() ismatch player 1 " + isMatch(player1) + " : " + player1.toString());

            replaceCard(cardIndex, player1);
        }

        if (message.clientName.equals(message.playerName2)) {
            System.out.println("before checking match: LastClick " + player2.getLastClick() + " CurrentClick: " + player2.getCurrClick() );
            player2.setLastClick(player2.getCurrClick());
            player2.setCurrClick(message.placedOnCard);
            System.out.println("\nAfter checking match: LastClick " + player2.getLastClick() + " CurrentClick: " + player2.getCurrClick() );
            //System.out.println("checkForMatch() ismatch player 2 " + isMatch(player2) + " : "+  player2.toString());

            player1.setLastClicked(player1.getCurrentClicked());
            player1.setCurrentClicked(hashMap.get(message.placedOnCard));

            replaceCard(cardIndex, player2);
        }

        updateGame(clientMessage);
    }

    private void replaceCard(String cardIndex, Player player) {
        Card playingCard = player.showTopCard();
        Card drawnCard = player.drawOne();
        currentCardClicked = hashMap.get(player.getCurrClick());
        lastCardClicked = hashMap.get(player.getName() + "Replaced");

        System.out.println("replacing: " + currentCardClicked + " With: "+ playingCard);

        //stores replaced card TODO not sure we need this. initial idea it was to be able to backtrack if not a match and needed a place to store the replaced card
        hashMap.put(player.getName() + "Replaced", currentCardClicked);


        //TODO fix matching logic to backtrack non matching pairs
        if (isMatch(player)) {
            System.out.println("\n\nMATCH\n\n");
        }
        //places player card on board card
        hashMap.put(player.getCurrClick(), drawnCard);
        if (cardIndex.equals("0") || cardIndex.equals("1") || cardIndex.equals("2") || cardIndex.equals("3")){
            boardHand1.getPlayersHand().set(Integer.parseInt(cardIndex), hashMap.get(player.getCurrClick()));
            clientMessage.setCardsOnBoard1(boardHand1.getPlayersHand());
        }
        if (cardIndex.equals("4") || cardIndex.equals("5") || cardIndex.equals("6") || cardIndex.equals("7")){
            boardhand2.getPlayersHand().set(Integer.parseInt(cardIndex) - 4, hashMap.get(player.getCurrClick()));
            clientMessage.setCardsOnBoard2(boardhand2.getPlayersHand());
        }


    }

    private boolean isMatch(Player player){
       // Card lastClicked = hashMap.get(player.getName()+"Replaced");

        if (!player.getLastClick().equals("")){
            System.out.println("isMatch() LastClick: " + player.getLastClick() +" : " + lastCardClicked + " CurrClick: " + player.getCurrClick() +
                    " : " + currentCardClicked);

            System.out.println("\n is lastClick == currClick? " + (lastCardClicked.getValue() == currentCardClicked.getValue() ));

            //compares players currClick to lastClick
            if (lastCardClicked.getValue() == currentCardClicked.getValue()){
                //checks to see if the clicked card has already been clicked
                if (!hashMap.get(player.getCurrClick()).isPlacedOnMatch()){
                    //sets card as clicked
                    hashMap.get(player.getCurrClick()).setPlacedOnMatch(true);

                    player.setCurrClick("");
                    player.setLastClick("");
                    return true;
                }
            }
            if (comparePlayerClicks(player, player2)) return true;
            if (comparePlayerClicks(player, player1)) return true;
        }
        return false;
    }

    private boolean comparePlayerClicks(Player player, Player otherPlayer) {
        if (!otherPlayer.getCurrClick().equals("")){
            if (hashMap.get(player.getCurrClick()).getValue() == hashMap.get(otherPlayer.getCurrClick()).getValue() && !clientMessage.getClientName().equals(otherPlayer.getName())){
                if (!hashMap.get(player.getCurrClick()).isPlacedOnMatch()){
                    //sets card as clicked
                    hashMap.get(player.getCurrClick()).setPlacedOnMatch(true);
                    player.setCurrClick("");
                    player.setLastClick("");
                    otherPlayer.setCurrClick("");
                    return true;
                }
            }
        }
        return false;
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }
}
