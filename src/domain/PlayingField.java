package domain;


import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Tile> tiles;


    public PlayingField() {
        tiles = new ArrayList<>();
    }

    public void addToTileList(Tile tile) {
        tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile findTileinList(int id, String type) {
        for (Tile tile : tiles) {
            if (tile.getId() == id && tile.getType().equals(type)) {
                return tile;
            }
        }
        return null;
    }
}
