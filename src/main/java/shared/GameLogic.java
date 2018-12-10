package shared;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.Dice;
import client.domain.classes.Pawn;
import client.domain.classes.Tile;
import client.domain.enums.GameMode;
import client.domain.enums.PlayerColor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameLogic {
    private Dice dice;
    private boolean debugMode;
    private BoardStorage boardStorage;
    private int currentTurn = 0;
    private boolean diceRolled;
    private boolean isDone;
    private int callerId;

    public GameLogic() {
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
        dice = new Dice();

    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public GameLogic setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    public void skipTurn() {
        switchTurn();
        diceRolled = false;

    }

    public int rollDice() throws Exception {
        if (diceRolled && !debugMode) {
            throw new IllegalArgumentException("move your pawn");
        }
        dice.rollDice();
        boolean canMove = false;
        Pawn[] pawns = boardStorage.getPlayerPawns(currentTurn);
        for (Pawn pawn : pawns) {
            if (getPossibleMove(pawn.getFullId()) != null) {
                canMove = true;

            }
        }
        if (!canMove) {

            diceRolled = false;
            switchTurn();

        } else {

            diceRolled = true;
        }
        return dice.getLastRolled();
    }

    private void switchTurn() {
        if (currentTurn == 3) {
            currentTurn = 0;
        } else {
            currentTurn++;
        }
    }

    public void movePawn(String pawnId) throws Exception {

        if (diceRolled || debugMode) {
            Pawn pawn = getPawn(pawnId);

            if (pawn.getPlayerColor().getValue() == currentTurn) {

                Tile possibleMove = getPossibleMove(pawn.getFullId());

                if (possibleMove != null) {

                    boardStorage.getTile(pawn.getPawnTileId()).removePawn();
                    checkPawnSmash(pawn, possibleMove);
                }

                diceRolled = false;

                checkWinCondition();

                if (dice.getLastRolled() != 6) {
                    switchTurn();
                }
            } else {
                throw new IllegalArgumentException("Not your turn");
            }

        } else {
            throw new IllegalArgumentException("Roll the dice");
        }


    }

    private void checkWinCondition() {
        if (checkWincondition()) {
            isDone = true;
            throw new IllegalArgumentException("Player " + PlayerColor.values()[getCurrentPlayerId()] + " has won");
        }
    }

    private void checkPawnSmash(Pawn pawn, Tile possibleMove) {
        Pawn smashedPawn = possibleMove.getPawn();

        if (smashedPawn != null) {

            // Places the smashed pawn to the starting tile
            smashedPawn.setPawnTileId(smashedPawn.getFullId());
            smashedPawn.setStepsTaken(0);
        }
        if (!pawn.getPawnTileId().equals(pawn.getFullId())) {
            pawn.setStepsTaken(pawn.getStepsTaken() + dice.getLastRolled());
        }
        pawn.setPawnTileId(possibleMove.getFullId());

        possibleMove.setPawn(pawn);
    }

    private boolean checkWincondition() {


        for (int i = 0; i < 4; i++) {
            Pawn[] pawns = boardStorage.getPlayerPawns(i);
            boolean won = true;
            for (Pawn pawn : pawns) {
                if (pawn.getPawnTileId().charAt(2) != 'H') {
                    won = false;
                }
            }
            if (won) {
                return true;
            }
        }
        return false;
    }

    public Tile[] getTiles() {
        return boardStorage.getTiles();
    }

    public Pawn[] getPawns() {
        return boardStorage.getPawns();
    }

    public int getCurrentPlayerId() {
        return currentTurn;
    }

    public Tile getPossibleMove(String pawnId) {

        Pawn pawn = boardStorage.getPawn(pawnId);
        Tile curTile = boardStorage.getTile(pawn.getPawnTileId());
        if (curTile.getType().charAt(2) == 'P') {

            if (dice.getLastRolled() == 6) {

                Stream<Tile> stream = boardStorage.getTilesAsList().stream()
                        .filter(t -> t.getColor().equals(pawn.getPlayerColor().toString()) && t.getType().equals("WLK"));

                Optional<Tile> tile = stream.findFirst();
                if (tile.isPresent()) {

                    return tile.get();
                }
            }
            return null;
        }

        Tile possibleMove;
        possibleMove = getWalkingTilePossibleMove(pawn, curTile);

        if (pawn.getPawnTileId().equals((pawn.getFullId().substring(0, 2) + "H04"))) {
            return null;
        }
        if (pawn.getStepsTaken() + dice.getLastRolled() >= boardStorage.getTileAmountOf("WLK")) {

            if(boardStorage.getTile(pawn.getPawnTileId()).getType().endsWith("H") && getHomeBaseLockedOf(pawn.getFullId().substring(0,2),curTile.getNummerId())){
                return null;
            }

            int amountToWalk = pawn.getStepsTaken() + dice.getLastRolled() - boardStorage.getTileAmountOf("WLK");
            int t = 1;
            int dir = 1;
            amountToWalk = amountToWalk % 6;
            while (amountToWalk > 0) {
                if (t == 4) {
                    dir = -1;
                } else if (t == 1) {
                    dir = 1;
                }
                t += dir;
                amountToWalk--;
            }


            Tile tile = boardStorage.getTile((pawn.getFullId().substring(0, 2) + "H0" + t));
            if (tile.getPawn() != null) {
                if (tile.getPawn().getPlayerColor() == pawn.getPlayerColor()) {
                    return null;
                }
            }

            return tile;
        }

        return possibleMove;
    }

    private boolean getHomeBaseLockedOf(String tileColor, int base) {
        if (boardStorage.getTile(tileColor + "H0" +base).getPawn() !=null) {
                    if(base==4) {
                        return true;
                    }else
                        return getHomeBaseLockedOf(tileColor,base+1);
        }
        return false;
    }


    private Tile getWalkingTilePossibleMove(Pawn pawn, Tile curTile) {
        Tile possibleMove;
        List<Tile> walkables = boardStorage.getTilesAsList().stream()
                .filter(t -> t.getType().equals("WLK")).collect(Collectors.toList());
        int i = walkables.indexOf(curTile);
        i += dice.getLastRolled();


        if (i >= boardStorage.getTileAmountOf("WLK")) {
            i -= boardStorage.getTileAmountOf("WLK");
        }
        possibleMove = walkables.get(i);
        if (possibleMove.getPawn() != null) {
            if (possibleMove.getPawn().getPlayerColor() == pawn.getPlayerColor()) {
            }
        }
        return possibleMove;
    }

    public int getDiceRolled() {

        return dice.getLastRolled();

    }

    public int getDiceAmountRolled() {
        return dice.getAmountRolled();
    }

    public boolean isYourTurn() {
        return true;
    }

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

    public boolean getIsDone() {
        return isDone;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }
}
