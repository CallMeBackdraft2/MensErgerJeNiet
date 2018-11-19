package client.domain.Classes;


import javafx.util.Pair;


public class Tile {
    // fields
    private String id;
    private String type;
    private String color;
    private int xLoc;
    private int yLoc;
    private Pawn pawn;

    // Constructors
    public Tile(String fullId, String color, int xLoc, int yLoc) {
        this.id = fullId;
        this.type = fullId.substring(0, 3);
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.color = color;

    }

    // Properties
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return this.pawn;
    }

    public String getColor() {
        return this.color;
    }

    public String getFullId() {
        return id;
    }

    public int getNummerId() {
        return Integer.parseInt(id.substring(3, 5));
    }

    public String getType() {
        return type;
    }


    // Methods
    public Pair<Integer, Integer> getLocation() {

        return new Pair<>(xLoc, yLoc);
    }

    public void removePawn() {
        pawn = null;
    }


    @Override
    public String toString() {
        String ending = "";
        if (this.color != null) {
            ending = " Belongs to: " + this.color;
        }
        return "Tiletype: " + this.type + " TileID: " + this.id + ending;
    }
}
