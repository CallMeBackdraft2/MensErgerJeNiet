package server.logic;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.Dice;
import server.websockets.WebsocketServerCommunicator;

import javax.websocket.Session;
import java.util.List;

public class MultiplayerFourPlayerGame {

    boolean started;
    int currentTurn;
    BoardStorage boardStorage;
    Dice dice;
    List<Session> sessions;

    public MultiplayerFourPlayerGame(){
        started=false;
        boardStorage = DALFactory.getLocalBoardStorage();
    }


    public void onMovePawn(int playerId, String pawnTileID, String tileID){

        boardStorage.movePawn(pawnTileID,tileID);
    }

    public void onRollDice(int playerId){

        dice.rollDice();
    }

    public void updatePawn(int pawnId, int tileId){
    }


}
