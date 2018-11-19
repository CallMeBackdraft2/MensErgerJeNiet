package client.domain.enums;

import javafx.scene.paint.Color;

public enum PlayerColor {
    RED(0),
    BLUE(1),
    YELLOW(2),
    GREEN(3),
    BLACK(4),
    PURPLE(5);

    private int value;

    PlayerColor(int value) {
        this.value = value;
    }

    public double getHue(){
          return PlayerColor.values()[value].toColor().getHue();
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
            case "BC":
                return PlayerColor.BLACK;
            case "PU":
                return PlayerColor.PURPLE;
            default:
                System.out.println("ERROR");
                return PlayerColor.RED;
        }
    }

    public Color toColor() {

        switch (value) {

            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLACK;
            case 5:
                return Color.PURPLE;
            default:
                return Color.GREY;

        }
    }

    public Color toColorAccent() {

        switch (value) {

            case 0:
                return Color.DARKRED;
            case 2:
                return Color.rgb(209, 177, 0);
            case 1:
                return Color.DARKBLUE;
            case 3:
                return Color.DARKGREEN;
            case 4:
                return Color.rgb(64, 64, 64);
            case 5:
                return Color.rgb(100,23,98);
            default:
                return Color.BLACK;
        }
    }

    public int getValue() {
        return value;
    }
}
