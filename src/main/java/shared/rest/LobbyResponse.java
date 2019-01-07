package shared.rest;

import client.domain.classes.Lobby;

import java.util.List;

public class LobbyResponse {
    // Indicates whether REST call was successful
    private boolean success;

    // List of pets
    private List<Lobby> lobbies;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<Lobby> lobbies) {
        this.lobbies = lobbies;
    }
}
