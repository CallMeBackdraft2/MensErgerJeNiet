package logic.interfaces;

import domain.Classes.Pawn;
import domain.Classes.Player;
import domain.Classes.Tile;

import java.util.List;

public interface Game {
    boolean isDiceRolled();

    int rollDice();
    void movePawn(String pawnId);
    List<Tile> getTiles();
    Tile getPossibleMove(Pawn pawn);
    boolean isYourTurn();
    Pawn getPawn(String homeTileID);
    List<Pawn> getPawns();

    int getCurrentPlayerId();
    List<Player> getPlayers();


    void sendMessage(String message);
    List<String> getMessages();
    //Lobbystuff voor multiplayer iteratie
    void createLobbyView();
    void updateLobbyView();
    void deleteLobbyView();
    void joinLobby(int lobbyId);
    void readyUp();
    void addAI();
    void leaveLobby();
    void startGame();
}
