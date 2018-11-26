package client.logic.localimplementation;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.*;
import client.domain.enums.GameMode;
import client.logic.interfaces.Game;
import client.websockets.CommunicatorWebSocket;

import java.util.List;

public class MultiplayerFourPlayerGame implements Game {

    public Dice dice;
    Lobby lobby;
    BoardStorage boardStorage;
    int currentTurn = 0;
    private boolean diceRolled;
    private boolean debugMode;
    private boolean isDone;

    private CommunicatorWebSocket communicator;

    public MultiplayerFourPlayerGame(){
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
        communicator = new CommunicatorWebSocket(this);

        Thread communicatorThread = new Thread(communicator);
        communicatorThread.run();
    }

    @Override
    public boolean isDiceRolled() {
        return false;
    }

    @Override
    public void skipTurn() {

    }

    @Override
    public int rollDice() {
        return 0;
    }

    @Override
    public void movePawn(String pawnId) {

    }

    @Override
    public List<Tile> getTiles() {
        return null;
    }

    @Override
    public Tile getPossibleMove(Pawn pawn) {
        return null;
    }

    @Override
    public boolean isYourTurn() {
        return false;
    }

    @Override
    public Pawn getPawn(String homeTileID) {
        return null;
    }

    @Override
    public List<Pawn> getPawns() {
        return null;
    }

    @Override
    public int getCurrentPlayerId() {
        return 0;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public List<String> getMessages() {
        return null;
    }

    @Override
    public void createLobbyView() {

    }

    @Override
    public void updateLobbyView() {

    }

    @Override
    public void deleteLobbyView() {

    }

    @Override
    public void joinLobby(int lobbyId) {

    }

    @Override
    public void readyUp() {

    }

    @Override
    public void addAI() {

    }

    @Override
    public void leaveLobby() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public boolean getIsDone() {
        return false;
    }

    public void movePawn(String pawnId, String tileId){
        boardStorage.movePawn(pawnId, tileId);
    }
}
