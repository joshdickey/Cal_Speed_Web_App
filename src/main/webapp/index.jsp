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
  </head>
  <body>
  <script src="scripts/payload.js"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


    <form align="center">

      <label for="textMessage" id="nameLabel">Player Name: </label><input id="textMessage" type="text">
      <input id="btnJoinGame" onclick="addPlayer()" value="Join Game" type="button">
    </form>
  <br>
    <div style="width: 100%; overflow: hidden;">
      <div style="width: 40%; float: left;" align="center">
        <%--<div align="center"><h2><%=Game.getPlayer1().getName()%></h2></div>--%>
        <div id="playerName1" align="center"><h2>Player 1</h2></div>
        <br>
        <div class="btn-group" style="width:100%">
          <button id="btn0" onclick="playOnMatch(this)" value=""></button>
          <button id="btn1"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn2" onclick="playOnMatch(this)" value=""></button>
          <button id="btn3"  onclick="playOnMatch(this)" value=""></button>
        </div>
        <br><br>
        <div class="btn-group" style="width:100%">
          <button id="btnHand1" style="width:25%" onclick="drawFromHand(this)" value="4">Hand</button>
          <button id="btnDeal1" style="width:25%" onclick="deal();">Deal</button>
        </div>
      </div>
      <div style="width: 40%; float: right;" align="center">
        <div id="playerName2" align="center"><h2>Player 2</h2></div>
        <br>
        <div class="btn-group" style="width:100%">
          <button id="btn4"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn5"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn6"  onclick="playOnMatch(this)" value=""></button>
          <button id="btn7"  onclick="playOnMatch(this)" value=""></button>
        </div>
        <br><br>
        <div class="btn-group" style="width:100%">
          <button id="btnDeal2" style="width:25%" onclick="deal()">Deal</button>
          <button id="btnHand2" style="width:25%" onclick="drawFromHand(this)" value="4">Hand</button>
        </div>
      </div>
    </div>
    <br><br>
    <div align="center">
      <textarea id="textBox" rows="10" cols="50" ></textarea>
    </div>

  </body>

</html>
