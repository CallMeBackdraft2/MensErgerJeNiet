package dal.localImplementation;

import dal.interfaces.BoardStorage;
import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;

import java.util.Arrays;
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
       return Arrays.copyOf( gameBoard.getPlayingField().getPawns()
                .stream().filter(p -> p.getPlayerColor().getValue() == PlayerId).toArray(),4,Pawn[].class);
    }

    @Override
    public List<Pawn> getPawns() {
        return gameBoard.getPlayingField().getPawns();
    }

    @Override
    public void movePawn(String pawnId, String tileId) {

        Pawn pawn = getPawn(pawnId);
        pawn.setPawnTileId(tileId);
    }

    @Override
    public Pawn getPawn(String id) {

        Optional<Pawn> optional = gameBoard.getPlayingField().getPawns()
                .stream().filter(p -> p.getFullId().equals(id)).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("No pawn found with the id = " + id);
        }
        return optional.get();
    }

    @Override
    public Tile getTile(String id) {
        Optional<Tile> optional = gameBoard.getPlayingField().getTiles()
                .stream().filter(t -> t.getFullId().equals(id)).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("No tile found with the id = " + id);
        }
        return optional.get();
    }
}
