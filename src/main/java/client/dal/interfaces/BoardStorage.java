package client.dal.interfaces;


import client.domain.classes.Pawn;
import client.domain.classes.Tile;
import client.domain.enums.GameMode;

import java.util.List;

public interface BoardStorage {

    void init(GameMode gameMode);
    List<Tile> getTilesAsList();
    Tile[] getTiles();
    Pawn[] getPlayerPawns(int playerId);
    Pawn[] getPawns();
    int getTileAmountOf(String id);
    void movePawn(String pawnId, String tileId);
    Pawn getPawn(String id);
    Tile getTile(String id);
}
