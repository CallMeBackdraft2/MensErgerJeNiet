package client.domain.classes;

import client.domain.enums.GameMode;
import client.domain.enums.LobbyType;

import java.util.List;

public class LobbySettings {

    private String name;
    private String password;
    private List<String> playerNames;
    private String creatorName;
    private GameMode gameMode;
    private LobbyType lobbyType;

    public LobbySettings(String name, String creatorName) {
        this.name = name;
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public LobbySettings setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LobbySettings setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public LobbySettings setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public LobbyType getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(LobbyType lobbyType) {
        this.lobbyType = lobbyType;
    }

}
