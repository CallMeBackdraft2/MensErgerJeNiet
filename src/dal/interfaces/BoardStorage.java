package dal.interfaces;


import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

import java.util.List;

public interface BoardStorage {

    void init(GameMode gameMode);
    List<Tile> getTiles();
    Pawn[] getPlayerPawns(int PlayerId);
    void movePawn(int pawnId, int tileId);
    Pawn getPawn(int id);
    Tile getTile(int id);
}
