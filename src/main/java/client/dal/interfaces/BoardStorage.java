package client.dal.interfaces;


import client.domain.Classes.Pawn;
import client.domain.Classes.Tile;
import client.domain.Enums.GameMode;

import java.util.List;

public interface BoardStorage {

    void init(GameMode gameMode);
    List<Tile> getTiles();
    Pawn[] getPlayerPawns(int playerId);
    List<Pawn> getPawns();
    int getTileAmountOf(String id);
    void movePawn(String pawnId, String tileId);
    Pawn getPawn(String id);
    Tile getTile(String id);
}
