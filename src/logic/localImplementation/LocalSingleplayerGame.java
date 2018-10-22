package logic.localImplementation;

import dal.interfaces.BoardStorage;
import dalFactories.DALFactory;
import domain.Classes.Dice;
import domain.Classes.GameBoard;
import domain.Classes.Pawn;
import domain.Enums.GameMode;
import domain.Enums.LobbyType;
import domain.Enums.PawnState;
import logic.interfaces.Game;

public class LocalSingleplayerGame implements Game {
    Dice dice;
    boolean diceRolled;
    BoardStorage boardStorage;

    public LocalSingleplayerGame(){
        boardStorage = DALFactory.getLocalBoardStorage();
    }

    @Override
    public int rollDice() {
        dice = new Dice();
        diceRolled = true;
        return dice.rollDice();
    }

    @Override
    public void movePawn(int pawnId) {
        if (diceRolled){
            // get the pawn that is given
            Pawn selectedPawn = boardStorage.getPawn(pawnId);

            // if the selected pawn still hasn't been played
            if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() == 6) {
                selectedPawn.movePawnIntoPlay();
                return;
            }
            else if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() != 6) {
                //TODO Throw errorMessage saying 6 was not rolled by the dice
                throw new IllegalArgumentException("YOu just can't");
            }

            // Check if the pawn is in play
            if (selectedPawn.getPawnState() == PawnState.INPLAY) {
                // Current steps taken + rolled dice value
                int newStepsTaken = selectedPawn.getStepsTaken() + dice.getLastRolled();

                if (newStepsTaken > 44){
                    int temp = newStepsTaken - 44;
                    temp = 44 - temp;
                    selectedPawn.setStepsTaken(temp);
                }
            }
        }
        else {
            //TODO throw errorMessage when the dice hasnt been rolled yet
            throw new IllegalArgumentException("YOu just can't");
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
