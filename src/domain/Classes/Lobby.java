package domain.Classes;


import domain.Enums.GameMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Lobby {

    private GameMode gameMode;
    boolean isOnline;
    private List<Player> players;
    private List<LobbyMessage> messages;


    private Lobby(boolean isOnline ) {
        players = new ArrayList<>();
        messages = new ArrayList<>();
        this.isOnline = isOnline;
    }

    public static Lobby getOfflineLobby() {


        return new Lobby(false);
    }

    public static Lobby getOnlineLobby(Player host) {

        Lobby lobby = new Lobby(true);
        lobby.players.add(host);
        return lobby;
    }

    public List<LobbyMessage> getMessages() {

        return messages;

    }

    public void addMessage(Player player, String message) {

        messages.add(new LobbyMessage(player, message, LocalDateTime.now()));
    }

    public void playerJoin(Player player) {
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
}
