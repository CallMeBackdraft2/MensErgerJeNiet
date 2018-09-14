package classes;


public class Tile {
    private int id;
    private String type;
    private String color;
    private boolean hasPawn;

    public Tile(int id, String type, String color){
        this.id = id;
        this.type = type;
        this.color = color;
    }

    public Tile(int id, String type){
        this.id = id;
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean hasPawn() {
        return hasPawn;
    }

    public void setPawn(boolean hasPawn) {
        this.hasPawn = hasPawn;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getColor(){
        return this.color;
    }

    @Override
    public String toString() {
        String ending = "";
        if (this.color != null){
            ending = " Belongs to: " + this.color;
        }
        return "Tiletype: " + this.type + " TileID: " + this.id + ending;
    }
}
