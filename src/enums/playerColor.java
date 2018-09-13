package enums;

public enum playerColor {
    RED(0),
    GREEN(1),
    BLUE(2),
    YELLOW(3);

    private int value;

    playerColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
