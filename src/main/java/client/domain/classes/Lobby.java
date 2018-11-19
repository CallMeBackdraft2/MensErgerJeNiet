package client.domain.classes;


import client.domain.enums.GameMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Lobby {

    private GameMode gameMode;
    private boolean isOnline;
    private List<Player> players;
    private List<LobbyMessage> messages;


    public Lobby( ) {
        players = new ArrayList<>();
        messages = new ArrayList<>();
        gameMode = GameMode.FOURPLAYERBOARD;
    }

   public Lobby(Player host) {
       players = new ArrayList<>();
       messages = new ArrayList<>();
       gameMode = GameMode.FOURPLAYERBOARD;
        players.add(host);
   }

    public List<LobbyMessage> getMessages() {

        return messages;

    }

    public void addMessage(Player player, String message) {

        messages.add(new LobbyMessage(player, message, LocalDateTime.now()));
    }

    public void playerJoin(Player player) {
        if (gameMode.equals(GameMode.FOURPLAYERBOARD)) {
            if (players.size() + 1 >= 5){
                throw new IllegalArgumentException("Maximal of 4 players allowed");
            }
        }
        else {
            if (players.size() + 1 >= 7){
                throw new IllegalArgumentException("Maximal of 6 players allowed");
            }
        }
        players.add(player);
    }

    public void playerLeave(Player player) {
        players.remove(player);
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public Lobby setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Lobby setOnline(boolean online) {
        isOnline = online;
        return this;
    }
}
