package dal.interfaces;


import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

import java.util.List;

public interface BoardStorage {

    void init(GameMode gameMode);
    List<Tile> getTiles();
    Pawn[] getPlayerPawns(int PlayerId);
    List<Pawn> getPawns();
    int getTileAmountOf(String id);
    void movePawn(String pawnId, String tileId);
    Pawn getPawn(String id);
    Tile getTile(String id);
}
