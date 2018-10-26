package logic.localImplementation;

import dal.interfaces.BoardStorage;
import dalFactories.DALFactory;
import domain.Classes.*;
import domain.Enums.GameMode;
import domain.Enums.PawnState;
import logic.interfaces.Game;

import java.util.List;

public class LocalFourPlayerGame implements Game {

    Dice dice;
    boolean diceRolled;
    BoardStorage boardStorage;

    public LocalFourPlayerGame(){
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
    }

    @Override
    public int rollDice() {
        dice = new Dice();
        diceRolled = true;
        return dice.rollDice();
    }

    @Override
    public void movePawn(String pawnId) {
        if (diceRolled){
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
            }
            else if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() != 6) {
                //TODO Throw errorMessage saying 6 was not rolled by the dice
                throw new IllegalArgumentException("YOu just can't");
            }

            // TODO When a Pawn is already in play

            // TODO When a Pawn is in HomeBase
        }
        else {
            //TODO throw errorMessage when the dice hasn't been rolled yet
            throw new IllegalArgumentException("YOu just can't");
        }
    }

    @Override
    public List<Tile> getTiles() {
        return boardStorage.getTiles();
    }

    @Override
    public List<Tile> getPossibleMoves(Pawn pawn) {
        return boardStorage.getTiles();
    }

    @Override
    public boolean isYourTurn() {
        return true;
    }

    @Override
    public Pawn getPawn(String homeTileID) {
        return boardStorage.getPawn(homeTileID);
    }

    private boolean smashPawn(String smashingPawnId, String tileId) {

        Pawn smashedPawn = boardStorage.getTile(tileId).getPawn();
        Pawn smashingPawn = boardStorage.getPawn(smashingPawnId);

        if (smashedPawn.getPlayerId() != smashingPawn.getPlayerId()) {
            boardStorage.getTile(tileId).removePawn();
            return true;
        }
        else {
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
