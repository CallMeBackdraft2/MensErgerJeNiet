package client.domain.classes;


import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Tile> tiles;
    private List<Pawn> pawns;

    public PlayingField() {
        tiles = new ArrayList<>();
        pawns = new ArrayList<>();

    }

    public List<Pawn> getPawns(){
        return pawns;
    }

    public void addPawn(Pawn pawn){

        pawns.add(pawn);
    }

    public void addToTileList(Tile tile) {
        tiles.add(tile);
    }

    public Tile[] getTiles() {
        return tiles.toArray(new Tile[0]);
    }

    public Tile findTileinList(int id, String type) {
        for (Tile tile : tiles) {
            if (tile.getNummerId() == id && tile.getType().equals(type)) {
                return tile;
            }
        }
        return null;
    }
}
