package client.domain.enums;

public enum GameMode {

    FOURPLAYERBOARD(4),
    SIXPLAYERBOARD(6);

    private final int playerCount;

    GameMode(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
