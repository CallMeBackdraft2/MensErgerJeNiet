package dal.localImplementation;

import dal.interfaces.BoardStorage;
import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

import java.util.List;
import java.util.Optional;


public class LocalBoardStorage implements BoardStorage {

    private GameBoard gameBoard;

    @Override
    public void init(GameMode gameMode) {

        gameBoard = new GameBoard(gameMode);
        BoardReader.Load("Boards/"+ gameMode.name() + ".map",gameBoard.getPlayingField());
    }

    @Override
    public List<Tile> getTiles() {
        List<Tile> tiles = gameBoard.getPlayingField().getTiles();
        return tiles;
    }

    @Override
    public Pawn[] getPlayerPawns(int PlayerId) {
        return (Pawn[]) gameBoard.getPlayingField().getPawns()
                .stream().filter(p -> p.getPlayerId() == PlayerId).toArray();
    }

    @Override
    public void movePawn(int pawnId, int tileId) {

        Pawn pawn = getPawn(pawnId);
        pawn.setPawnTileId(tileId);
    }

    @Override
    public Pawn getPawn(int id) {

        Optional<Pawn> optional = gameBoard.getPlayingField().getPawns()
                .stream().filter(p -> p.getId() == id).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("No pawn found with the id = " + id);
        }
        return optional.get();
    }

    @Override
    public Tile getTile(int id) {
        Optional<Tile> optional = gameBoard.getPlayingField().getTiles()
                .stream().filter(t -> t.getId() == id).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("No tile found with the id = " + id);
        }
        return optional.get();
    }
}
