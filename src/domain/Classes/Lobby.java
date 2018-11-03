package domain.Classes;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Lobby {

    private List<Player> players;
    private List<LobbyMessage> messages;

    public Lobby(Player host) {
        players = new ArrayList<>();
        messages = new ArrayList<>();
        players.add(host);
    }

    public List<LobbyMessage> getMessages(){

       return messages;

    }

    public void addMessage(Player player, String message){

        messages.add(new LobbyMessage(player,message, LocalDateTime.now()));
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


}
