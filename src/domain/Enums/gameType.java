package domain.Enums;

public enum gameType {
    FOURPLAYER(3),
    SIXPLAYER(5);

    private int value;

    gameType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
