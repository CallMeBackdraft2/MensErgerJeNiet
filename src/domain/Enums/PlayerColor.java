package domain.Enums;

public enum PlayerColor {
    RED(0),
    GREEN(1),
    BLUE(2),
    YELLOW(3);

    private int value;

    PlayerColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
