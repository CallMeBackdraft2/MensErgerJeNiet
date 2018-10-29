package logic.localImplementation;

import dal.interfaces.BoardStorage;
import dalFactories.DALFactory;
import domain.Classes.Dice;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.GameMode;
import domain.Enums.PawnState;
import domain.Enums.PlayerColor;
import logic.interfaces.Game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalFourPlayerGame implements Game {

    Dice dice;
    boolean diceRolled;
    BoardStorage boardStorage;

    public LocalFourPlayerGame() {
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
        dice = new Dice();
    }

    @Override
    public int rollDice() {

        diceRolled = true;
        return dice.rollDice();
    }


    @Override
    public void movePawn(String pawnId) {
      /*  if (diceRolled) {
            // get the pawn that is given
            Pawn selectedPawn = boardStorage.getPawn(pawnId);

            // if the selected pawn still hasn't been played
            if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() == 6) {
                selectedPawn.movePawnIntoPlay();

                // TODO What happens if another pawn is occupying the starting tiles
                // Depending on the player the pawn gets placed in the correct starting position
                if (selectedPawn.getPlayerId() == 1) {
                    boardStorage.movePawn(pawnId, "WL01");
                } else if (selectedPawn.getPlayerId() == 2) {
                    boardStorage.movePawn(pawnId, "WL11");
                } else if (selectedPawn.getPlayerId() == 3) {
                    boardStorage.movePawn(pawnId, "WL21");
                } else if (selectedPawn.getPlayerId() == 4) {
                    boardStorage.movePawn(pawnId, "WL31");
                }
                return;
            } else if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() != 6) {
                //TODO Throw errorMessage saying 6 was not rolled by the dice
                throw new IllegalArgumentException("YOu just can't");
            }

            // TODO When a Pawn is already in play

            // TODO When a Pawn is in HomeBase
        } else {
            //TODO throw errorMessage when the dice hasn't been rolled yet
            throw new IllegalArgumentException("YOu just can't");
        }
        */


      if(diceRolled) {
        Pawn pawn = getPawn(pawnId);
        Tile tile = getPossibleMove(pawn);

        if(tile!=null) {

            boardStorage.getTile( pawn.getPawnTileId()).removePawn();

            Pawn smashedPawn =  tile.getPawn();
            if(smashedPawn!=null){

                smashedPawn.setPawnTileId(smashedPawn.getFullId());
                int c=5;
            }


            pawn.setPawnTileId(tile.getFullId());
            tile.setPawn(pawn);

        }
    }
}

    @Override
    public List<Tile> getTiles() {
        return boardStorage.getTiles();
    }

    @Override
    public List<Pawn> getPawns() {
        return boardStorage.getPawns();
    }

    @Override
    public Tile getPossibleMove(Pawn pawn) {


        Tile curTile = boardStorage.getTile(pawn.getPawnTileId());
        if (curTile.getType().charAt(2) == 'P') {

            if(dice.getLastRolled()==6) {

                System.out.println(pawn.getPlayerColor().toString());
                Stream<Tile> stream = boardStorage.getTiles().stream()
                        .filter(t -> t.getColor().equals(pawn.getPlayerColor().toString()) && t.getType().equals("WLK"));


                return stream.findFirst().get();
            }
            return null;
        }

        List<Tile> walkables = getTiles().stream()
                .filter(t -> t.getType().equals("WLK")).collect(Collectors.toList());
        int i = walkables.indexOf(curTile);
        i += dice.getLastRolled();
        if (i >= 40) {
            i -= 40;
        }
        System.out.println(i);
        System.out.println(walkables.get(i));

        Tile result = walkables.get(i);
        if(result.getPawn()!=null){
            if(result.getPawn().getPlayerColor() == pawn.getPlayerColor()){
                return null;
            }
        }

        return walkables.get(i);
    }

    @Override
    public boolean isYourTurn() {
        return true;
    }

    @Override
    public Pawn getPawn(String id) {
        return boardStorage.getPawn(id);
    }

    private boolean smashPawn(String smashingPawnId, String tileId) {

        Pawn smashedPawn = boardStorage.getTile(tileId).getPawn();
        Pawn smashingPawn = boardStorage.getPawn(smashingPawnId);

        if (smashedPawn.getPlayerId() != smashingPawn.getPlayerId()) {
            boardStorage.getTile(tileId).removePawn();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createLobbyView() {

    }

    @Override
    public void updateLobbyView() {

    }

    @Override
    public void deleteLobbyView() {

    }

    @Override
    public void joinLobby(int lobbyId) {

    }


    @Override
    public void readyUp() {

    }

    @Override
    public void addAI() {

    }

    @Override
    public void leaveLobby() {

    }

    @Override
    public void startGame() {

    }
}
