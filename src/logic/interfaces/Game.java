package logic.interfaces;

import domain.Classes.Tile;

import java.util.List;

public interface Game {
    int rollDice();
    void movePawn(int pawnId);
    List<Tile> getTiles();


    //Lobbystuff
    void createLobbyView();
    void updateLobbyView();
    void deleteLobbyView();
    void joinLobby(int lobbyId);
    void readyUp();
    void addAI();
    void leaveLobby();
    void startGame();
}
