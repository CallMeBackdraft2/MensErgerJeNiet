package logic.interfaces;

import domain.Classes.Pawn;
import domain.Classes.Tile;

import java.util.List;

public interface Game {
    int rollDice();
    void movePawn(String pawnId);
    List<Tile> getTiles();
    Tile getPossibleMove(Pawn pawn);
    boolean isYourTurn();
    Pawn getPawn(String homeTileID);
    List<Pawn> getPawns();

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
