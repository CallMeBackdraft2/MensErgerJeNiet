package logic.interfaces;

public interface Game {
    int rollDice();
    void movePawn(int pawnId);

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
