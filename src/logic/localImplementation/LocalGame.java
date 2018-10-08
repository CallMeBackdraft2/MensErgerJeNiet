package logic.localImplementation;

import domain.Enums.GameMode;
import domain.Enums.LobbyType;
import logic.interfaces.Game;

public class LocalGame implements Game {
    @Override
    public int rollDice() {
        return 0;
    }

    @Override
    public void movePawn(int pawnId) {

    }

    @Override
    public void joinLobby(int lobbyId) {

    }

    @Override
    public void createLobby() {

    }

    @Override
    public void setLobbyName(String lobbyName) {

    }

    @Override
    public void setLobbyType(LobbyType lobbyType) {

    }

    @Override
    public void setGameMode(GameMode gameMode) {

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
}
