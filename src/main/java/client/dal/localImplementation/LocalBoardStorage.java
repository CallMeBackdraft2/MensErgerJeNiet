package client.dal.localImplementation;

import client.dal.interfaces.BoardStorage;
import client.domain.Classes.GameBoard;
import client.domain.Classes.Pawn;
import client.domain.Classes.Tile;
import client.domain.Enums.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class LocalBoardStorage implements BoardStorage {

    private GameBoard gameBoard;

    @Override
    public void init(GameMode gameMode) {

        gameBoard = new GameBoard(gameMode);
        BoardReader.load("Boards/"+ gameMode.name() + ".map",gameBoard.getPlayingField());
    }

    @Override
    public List<Tile> getTiles() {
        return gameBoard.getPlayingField().getTiles();
    }



    @Override
    public Pawn[] getPlayerPawns(int playerId) {
       return Arrays.copyOf( gameBoard.getPlayingField().getPawns()
                .stream().filter(p -> p.getPlayerColor().getValue() == playerId).toArray(),4,Pawn[].class);
    }

    @Override
    public List<Pawn> getPawns() {
        return gameBoard.getPlayingField().getPawns();
    }

    @Override
    public int getTileAmountOf(String id) {
        return (int)getTiles().stream().filter(t -> t.getType().equals(id)).count();
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