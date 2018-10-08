package domain.Classes;

import domain.Enums.GameMode;
import domain.Enums.LobbyType;

import java.util.List;

public class LobbySettings {

    private String Name;
    private String Password;
    private List<String> PlayerNames;
    private String CreatorName;

    private GameMode gameMode;
    private LobbyType lobbyType;

    public LobbySettings(String name, String creatorName) {
        Name = name;
        this.CreatorName = creatorName;
    }

    public String getName() {
        return Name;
    }

    public LobbySettings setName(String name) {
        Name = name;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public LobbySettings setPassword(String password) {
        Password = password;
        return this;
    }

    public List<String> getPlayerNames() {
        return PlayerNames;
    }

    public LobbySettings setPlayerNames(List<String> playerNames) {
        PlayerNames = playerNames;
        return this;
    }

    public String getCreatorName() {
        return CreatorName;
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
