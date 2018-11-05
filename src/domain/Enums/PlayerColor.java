package domain.Enums;

import javafx.scene.paint.Color;

public enum PlayerColor {
    RED(0),
    GREEN(1),
    BLUE(2),
    YELLOW(3);

    private int value;

    PlayerColor(int value) {
        this.value = value;
    }

    public double getHue(){

      double d =  PlayerColor.values()[value].toColor().getHue();
      return d;

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

    public Color toColor() {

        switch (value) {

            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;

        }
        return Color.GREY;
    }

    public Color toColorAccent() {

        switch (value) {

            case 0:
                return Color.DARKRED;
            case 1:
                return Color.DARKGREEN;
            case 2:
                return Color.DARKBLUE;
            case 3:
                return Color.rgb(209, 177, 0);

        }
        return Color.BLACK;
    }

    public int getValue() {
        return value;
    }
}
