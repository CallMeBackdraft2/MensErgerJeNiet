package dal.localImplementation;

import dal.interfaces.BoardStorage;
import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

public class LocalBoardStorage implements BoardStorage {

    GameBoard gameBoard;

    @Override
    public void setGameMode(GameMode gameMode) {

        gameBoard=  new GameBoard(gameMode);
    }

    @Override
    public Tile[] getTiles() {
        return new Tile[0];
    }

    @Override
    public Pawn[] getPlayerPawns(int PlayerId) {
        return new Pawn[0];
    }

    @Override
    public void setTilePawn(int id, Pawn pawn) {

    }

    @Override
    public Tile getTile(int id) {
        return null;
    }
}
