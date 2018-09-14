package classes;


import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Tile> tiles;


    public PlayingField() {
        tiles = new ArrayList<>();
    }

    public void addToTileList(Tile tile){
        tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
