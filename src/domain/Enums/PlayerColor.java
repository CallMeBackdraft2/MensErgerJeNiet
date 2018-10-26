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

    public static PlayerColor fromId(String id) {

        switch (id.substring(0, 2)) {
            case "RE":
                return PlayerColor.RED;
            case "GR":
                return PlayerColor.GREEN;
            case "BL":
                return PlayerColor.BLUE;
            case "YE":
                return PlayerColor.YELLOW;

        }

        System.out.println("ERROR");
        return PlayerColor.RED;

    }

    public int getValue() {
        return value;
    }
}
