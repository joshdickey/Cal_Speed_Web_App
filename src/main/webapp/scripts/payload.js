var btnDeal1, btnDeal2;
var textBox = null;
var textMessage = null;
var payload = "";
var webSocket = null;
var name = "";
var btnjoingame;

var playerName1;
var playerName2;
var nameLabel;
var currentPlayer = [];

var btnHand1, btnHand2;

// change to false when we upload to azure
var TESTING = true;

window.onload = function (ev)  {
    console.log("windows on load");
    textMessage = document.getElementById("textMessage");
    btnjoingame = document.getElementById("btnJoinGame");
    console.log("windows on load");
    btnDeal1 = document.getElementById("btnDeal");
    btnDeal2 = document.getElementById("btnDeal2");
    console.log("Deal buttons loaded");
    textBox = document.getElementById("textBox");

    nameLabel = document.getElementById("nameLabel");

    btnDeal1 = document.getElementById("btnDeal");
    btnDeal2 = document.getElementById("btnDeal2");
    btnHand1 = document.getElementById("btnHand1");
    btnHand2 = document.getElementById("btnHand2");


    playerName1 = document.getElementById("playerName1");
    playerName2 = document.getElementById("playerName2");

    //hide Hand and deal buttons
    btnDeal2.style.visibility = "hidden";
    btnDeal1.style.visibility = "hidden";

    btnHand1.style.visibility = "hidden";
    btnHand2.style.visibility = "hidden";

};

function deal() {
    console.log("deal(): player cannot find match");

    messageObj.messageType = "DEAL";
    messageObj.deal = "true";
    // var payload = { messageType: 'DEAL', deal: 'true' };
    webSocket.send(JSON.stringify(messageObj));
}

function addPlayer(){

    name = textMessage.value.trim();


    if(name === ''){ name = "Guest";}


    textMessage.value = '';
    textMessage.style.visibility = "hidden";
    btnjoingame.style.visibility = "hidden";
    nameLabel.style.visibility = "hidden";

    if(webSocket == null){
        console.log("Creating socket");

        if (TESTING) {
            webSocket = new WebSocket("ws://"+ window.location.host +"/websocketServer");
        }
        else {
            webSocket = new WebSocket("wss://webapp-181015200915.azurewebsites.net/websocketServer");
        }

        webSocket.onopen = function (ev) { processOpen(ev) };
        webSocket.onmessage = function (ev) { processMessage(ev) };
        webSocket.onclose = function (ev) { processClose(ev) };
        webSocket.onerror = function (ev) { processError(ev) };
    }

}

function processOpen(message) {

    var player = { messageType: "JOIN", clientName: name };
    webSocket.send(JSON.stringify(player));
    console.log("processOpen: " + name);
}

function processMessage(message) {

    //convert JSON to an Object
    messageObj = JSON.parse(message.data);
    console.log(JSON.stringify(message.data));
    console.log("processMessage: player1: " + messageObj.playerName1);
    console.log("processMessage: player2: " + messageObj.playerName2);


  //  currentPlayer[1] =  messageObj.playerName2;

 //   console.log(messageObj.cardsOnBoard1);
 //   console.log(messageObj.cardsOnBoard2);

    var playerName = messageObj.clientName;

    if (messageObj.messageType === "JOIN" ){

        textBox.value += playerName + " has joined the game.\n";
        setNames(messageObj);
        if (messageObj.playerCount === 1) {
            textBox.value += "Waiting on Player 2 to join.\n";
        }
    }
   if (messageObj.messageType === "PLACED" && messageObj.playerCount > 1 ) {
       textBox.value += playerName + " placed a card\n";
   }
    if (messageObj.messageType === "MATCH" && messageObj.playerCount > 1){
        textBox.value += playerName + " completed a Match\n";
    }
   if (messageObj.messageType === "DEAL"){
       textBox.value +=  messageObj.clientName +" Cannot find match.\n";
       //console.log(messageObj.clientName + " cannot find matching cards.");
   }
    if (messageObj.messageType === "REJECT" && messageObj.playerCount > 1) {
        textBox.value += playerName + " tried to place a card but was rejected\n";
    }

   console.log(messageObj);
   if (messageObj.playerCount > 1){
       showHideHand(messageObj);
       setNames(messageObj);
       updateBoard(messageObj);
   }
}

function showHideHand(message) {
    console.log("Name: " + name + " Playername1: " + message.playerName1 + " Playername2: " + message.playerName2);
    if (name === message.playerName1){
        btnHand1.style.visibility = "visible";
        btnDeal1.style.visibility = "visible";
    }

    if (name === message.playerName2){
        btnHand2.style.visibility = "visible";
        btnDeal2.style.visibility = "visible";
    }
}

function setNames(message) {
    playerName1.innerHTML = "<h2>" + message.playerName1 + "<h2>";
    playerName2.innerHTML = "<h2>" + message.playerName2 + "<h2>";

}

function updateBoard(message) {
    document.getElementById("btn0").innerText = message.cardsOnBoard1[0].value;
    document.getElementById("btn0").value = message.cardsOnBoard1[0];
    document.getElementById("btn1").innerText =  message.cardsOnBoard1[1].value;
    document.getElementById("btn2").innerText =  message.cardsOnBoard1[2].value;
    document.getElementById("btn3").innerText =  message.cardsOnBoard1[3].value;

    document.getElementById("btn4").innerText = message.cardsOnBoard2[0].value;
    document.getElementById("btn5").innerText =  message.cardsOnBoard2[1].value;
    document.getElementById("btn6").innerText =  message.cardsOnBoard2[2].value;
    document.getElementById("btn7").innerText =  message.cardsOnBoard2[3].value;
}

function processClose(message) {
    webSocket.send("client disconnected...");

    textBox.value += "Server Disconnected..."+"\n";
}

function processError(message) {
    textBox.value += "error..."+"\n";
}

/*function cardPut(button) {
    var cardValue = button.value;
    payload += " " + cardValue + " " + button.id;
    webSocket.send(payload);
    payload = "";
}*/

function drawFromHand(button) {
    messageObj.messageType = "DRAW";
    //console.log(messageObj);
}

function playOnMatch(button) {
    if (messageObj.messageType === "DRAW"){
        if (messageObj.clientName === messageObj.playerName1) {

            for (var i = 0;i < 4;i++) {
                console.log("In hand: "+ messageObj.topCard1.value + " : " + messageObj.cardsOnBoard1[i].value + " " + messageObj.cardsOnBoard2[i].value);
            }
        }
        if (messageObj.clientName === messageObj.playerName2) {

            for (var i = 0;i < 4;i++) {
                console.log("In hand: "+ messageObj.topCard2.value + " : " + messageObj.cardsOnBoard1[i].value + " " + messageObj.cardsOnBoard2[i].value);
            }
        }
        messageObj.placedOnCard = button.id;
        messageObj.placedOnValue = button.innerText;

        webSocket.send(JSON.stringify(messageObj));
    }
    
    messageObj.messageType = "WAIT";
}

