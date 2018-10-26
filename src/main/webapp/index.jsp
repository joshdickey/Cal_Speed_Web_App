<%--
  Created by IntelliJ IDEA.
  User: jdickey
  Date: 10/17/2018
  Time: 10:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Websocket test</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <script src="scripts/payload.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  </head>
  <body>
    <form id="submitForm" align="center">
      <label for="textMessage" id="nameLabel">Player Name: </label><input id="textMessage" type="text">
      <input id="btnJoinGame" onclick="addPlayer()" value="Join Game" type="button">
    </form>
    <br>
    <div id="messageDiv" align="center">
        <textarea id="textBox" rows="10" cols="50" ></textarea>
    </div>
    <div id="row" class="container">
        <div id="p1Side" class="column playerDiv">
            <div id="playerName1" class="playerName" align="center">
                <h2>Player 1</h2>
            </div>
            <div class="playerButtonContainer">
                <button id="btnHand1" class="card" onclick="drawFromHand(this)"></button>
            </div>
            <div class="playerButtonContainer">
                <button id="btnDeal1" class="playerButton" onclick="deal()">Deal</button>
            </div>
        </div>
        <div id="cardColumn" class="column">
            <div class="btn-group">
                <button id="btn0" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn1" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn2" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn3" class="card" onclick="playOnMatch(this)"></button>
            </div>
            <div class="btn-group">
                <button id="btn4" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn5" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn6" class="card" onclick="playOnMatch(this)"></button>
                <button id="btn7" class="card" onclick="playOnMatch(this)"></button>
            </div>
        </div>
        <div id="p2Side" class="column playerDiv">
            <div id="playerName2" class="playerName" align="center">
                <h2>Player 2</h2>
            </div>
            <div class="playerButtonContainer">
                <button id="btnHand2" class="card" onclick="drawFromHand(this)"></button>
            </div>
            <div class="playerButtonContainer">
                <button id="btnDeal2" class="playerButton" onclick="deal()">Deal</button>
            </div>

        </div>
    </div>
    <!--
    <div id="container">
      <div id="p1Side" class="playerDiv" align="center">
        <%--<div align="center"><h2><%=Game.getPlayer1().getName()%></h2></div>--%>
        <div id="playerName1" align="center"><h2>Player 1</h2></div>
        <br>
        <div class="btn-group">
          <button id="btn0" onclick="playOnMatch(this)" value=""></button>
          <button id="btn1"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn2" onclick="playOnMatch(this)" value=""></button>
          <button id="btn3"  onclick="playOnMatch(this)" value=""></button>
        </div>
        <br><br>
        <div class="btn-group">
          <button id="btnHand1" class="playerButtons" onclick="drawFromHand(this)" value="4">Hand</button>
          <button id="btnDeal" class="playerButtons" onclick="deal();">Deal</button>
        </div>
      </div>
      <div id="p2Side" class="playerDiv" align="center">
        <div id="playerName2" align="center"><h2>Player 2</h2></div>
        <br>
        <div class="btn-group">
          <button id="btn4"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn5"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn6"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn7"  onclick="playOnMatch(this)" value=""></button>
        </div>
        <br><br>
        <div class="btn-group">
          <button id="btnDeal2" class="playerButtons" onclick="deal()">Deal</button>
          <button id="btnHand2" class="playerButtons" onclick="drawFromHand(this)" value="4">Hand</button>
        </div>
      </div>
    </div>
    -->
    <br><br>


  </body>

</html>
