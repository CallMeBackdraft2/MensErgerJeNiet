package domain;

import java.util.List;

public class LobbyView {

    private String Name;
    private String Password;
    private List<String> PlayerNames;
    private String CreatorName;

    public LobbyView(String name, String creatorName) {
        Name = name;
        this.CreatorName = creatorName;
    }

    public String getName() {
        return Name;
    }

    public LobbyView setName(String name) {
        Name = name;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public LobbyView setPassword(String password) {
        Password = password;
        return this;
    }

    public List<String> getPlayerNames() {
        return PlayerNames;
    }

    public LobbyView setPlayerNames(List<String> playerNames) {
        PlayerNames = playerNames;
        return this;
    }

    public String getCreatorName() {
        return CreatorName;
    }
}
