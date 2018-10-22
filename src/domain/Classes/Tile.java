package domain.Classes;


import javafx.util.Pair;

public class Tile {
    // fields
    private int id;
    private String type;
    private String color;
    private int xLoc;
    private int yLoc;
    private Pawn pawn;

    // Constructors
    public Tile(int id, String type, int xLoc, int yLoc, String color) {
        this.id = id;
        this.type = type;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.color = color;
    }

    public Tile(int id, String type, int xLoc, int yLoc) {
        this.id = id;
        this.type = type;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    // Properties
    public void AddPawn(Pawn pawn) { this.pawn = pawn; }
    public Pawn getPawn() {return this.pawn; }

    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return this.color;
    }

    public int getId() {
        return id;
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
