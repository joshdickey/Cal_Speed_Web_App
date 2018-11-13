package speed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;


@ServerEndpoint("/websocketServer")
public class WebsocketServer{
    private Game game = Game.getGame();
    private ClientMessage clientMessage;
    private GsonBuilder builder = new GsonBuilder();
    Gson gson;

    @OnOpen
    public void handleOpen(Session session){
        builder.serializeNulls();
        gson = builder.create();
        System.out.println("Client is connected to server..." );
        clientMessage =  game.getGameStats();

        game.join(session);
    }

    @OnClose
    public void handleClose(Session session){
        System.out.println("Client is disconnected from server...");
        game.leave(session);
    }

    //message recieved from the client
    @OnMessage
    public void onmessage(Session session, String message) throws IOException {
       // System.out.println("onmessage: Session properties: " + session.getUserProperties());
      /*  Type collectionType = new TypeToken<Collection<ClientMessage>>(){}.getType();
        Collection<ClientMessage> clientMessage = gson.fromJson(message, collectionType);*/
        clientMessage = gson.fromJson(message, ClientMessage.class);



        Map<String, Object> properties = session.getUserProperties();
        if (clientMessage.messageType.equals("JOIN")){

            String name = clientMessage.getClientName();
            properties.put("playerName", name);

           game.addPlayer(clientMessage);
        }

        //player cannot find a match and wants to deal new cards from his hand
        if (clientMessage.messageType.equals("DEAL") && clientMessage.deal){

            //sets the current player to the clientName
            clientMessage.setClientName(properties.get("playerName").toString());

            //sets players boolean as true to indicate that he is ready to deal new cards.
            game.setPlayerReadyToDeal(clientMessage);
        }

        //Player drew from his hand to place his card
        if (clientMessage.messageType.equals("DRAW")){
            //sets the current player to the clientName

            clientMessage.setClientName(properties.get("playerName").toString());

            game.match(clientMessage);
           // game.checkForMatch(clientMessage);
        }

        //Both players hit reset game
        if (clientMessage.messageType.equals("RESET") && clientMessage.reset) {
            clientMessage.setClientName(properties.get("playerName").toString());
            game.setPlayerReadyToReset(clientMessage);
        }


    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

}