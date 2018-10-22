package dal.interfaces;


import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

public interface BoardStorage {

    void setGameMode(GameMode gameMode);
    Tile[] getTiles();
    Pawn[] getPlayerPawns(int PlayerId);
    void setTilePawn(int id, Pawn pawn);
    Tile getTile(int id);
}
