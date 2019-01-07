package server.logic;

import client.domain.classes.Lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {
    private List<Lobby> lobbies;
    private static LobbyManager instance;

    public LobbyManager(){
        lobbies = new ArrayList<>();
    }

    public void setLobbies(List<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public static LobbyManager getInstance(){
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }
}
