package domain.Classes;


import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Tile> tiles;
    private List<Pawn> pawns;


    public List<Pawn> getPawns(){
        return pawns;
    }

    public void addPawn(Pawn pawn){

    }

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
