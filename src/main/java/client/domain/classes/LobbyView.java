package client.domain.classes;


import client.domain.enums.GameMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class LobbyView {

    private GameMode gameMode;
    private boolean isOnline;
    private List<Player> players;
    private List<LobbyMessage> messages;
    private int lobbyId;
    private String name;

    public LobbyView() {
        name = "Unnamed Lobby";
        players = new ArrayList<>();
        messages = new ArrayList<>();
        gameMode = GameMode.FOURPLAYERBOARD;
    }
    public LobbyView(int lobbyId) {
        this.lobbyId = lobbyId;
        players = new ArrayList<>();
        messages = new ArrayList<>();
        gameMode = GameMode.FOURPLAYERBOARD;
    }
    public LobbyView(Player host) {
        players = new ArrayList<>();
        messages = new ArrayList<>();
        gameMode = GameMode.FOURPLAYERBOARD;
        players.add(host);
    }



    public List<LobbyMessage> getMessages() {

        return messages;

    }

    public String getName(){
        return name;
    }

    public LobbyView setMessages(List<LobbyMessage> messages) {
        this.messages = messages;
        return this;
    }

    public void addMessage(Player player, String message) {

        messages.add(new LobbyMessage(player, message, LocalDateTime.now()));
    }

    public void playerJoin(Player player) {
        if (gameMode.equals(GameMode.FOURPLAYERBOARD)) {
            if (players.size() + 1 >= 5) {
                throw new IllegalArgumentException("Maximal of 4 players allowed");
            }
        } else {
            if (players.size() + 1 >= 7) {
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

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;

    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {

        return name + " (player count = " + players.size() + ")";

    }

    public void setName(String name) {
    this.name = name;
    }

    public void setPlayers(List<Player> players) {
        this.players=players;
    }
}
