package server.logic;

import client.domain.classes.LobbyView;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {
    private List<LobbyView> lobbies;
    private static LobbyManager instance;

    public LobbyManager(){
        lobbies = new ArrayList<>();
    }

    public void setLobbies(List<LobbyView> lobbies) {
        this.lobbies = lobbies;
    }

    public List<LobbyView> getLobbies() {
        return lobbies;
    }

    public static LobbyManager getInstance(){
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }
}
