package dal.interfaces;


import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

public interface BoardStorage {

    void init(GameMode gameMode);
    Tile[] getTiles();
    Pawn[] getPlayerPawns(int PlayerId);
    void movePawn(int pawnId, int tileId);
    Pawn getPawn(int id);
    Tile getTile(int id);
}
