package shared.rest;

import client.domain.classes.LobbyView;

import java.util.List;

public class LobbyResponse {
    // Indicates whether REST call was successful
    private boolean success;

    // List of pets
    private List<LobbyView> lobbies;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<LobbyView> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<LobbyView> lobbies) {
        this.lobbies = lobbies;
    }
}
