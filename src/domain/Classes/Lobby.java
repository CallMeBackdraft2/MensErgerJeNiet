package domain.Classes;


import domain.Enums.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private GameBoard gameBoard;
    private List<Player> players;

    public Lobby(Player host) {
        gameBoard = new GameBoard(GameMode.FOURPLAYERBOARD);
        players = new ArrayList<>();
        players.add(host);
    }

    public void playerJoin(Player player) {
        players.add(player);
    }

    public void playerLeave(Player player) {
        players.remove(player);
    }

    //Public getters
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }


}
