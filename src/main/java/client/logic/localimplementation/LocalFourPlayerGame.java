package client.logic.localimplementation;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.*;
import client.domain.enums.GameMode;
import client.domain.enums.PlayerColor;
import client.logic.interfaces.Game;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalFourPlayerGame implements Game {

    private Dice dice;
    Lobby lobby;
    BoardStorage boardStorage;
    int currentTurn = 0;
    private boolean diceRolled;
    private boolean debugMode;
    private boolean isDone;

    public LocalFourPlayerGame(boolean debugMode) {
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);

        lobby = new Lobby();
        lobby.playerJoin(new Player(0, "TestHuman"));
        lobby.playerJoin(new Player(1, "TestAI1"));
        lobby.playerJoin(new Player(2, "TestAI2"));
        lobby.playerJoin(new Player(3, "TestAI3"));
        this.debugMode = debugMode;
        dice = new Dice();
    }

    public LocalFourPlayerGame(Lobby lobby){
        this.lobby = lobby;
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(lobby.getGameMode());
        this.dice = new Dice();
    }

    @Override
    public boolean isDiceRolled() {
        return diceRolled;
    }

    @Override
    public void skipTurn(){
        switchTurn();
        diceRolled=false;
    }

    @Override
    public int rollDice() {
        if (diceRolled && !debugMode) {
            throw new IllegalArgumentException("move your pawn");
        }
        dice.rollDice();
        boolean canMove = false;
        Pawn[] pawns = boardStorage.getPlayerPawns(currentTurn);
        for (Pawn pawn : pawns) {
            if (getPossibleMove(pawn) != null) {
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

    @Override
    public void movePawn(String pawnId) {

        if (diceRolled || debugMode) {
            Pawn pawn = getPawn(pawnId);

            if (pawn.getPlayerColor().getValue() == currentTurn) {

                Tile possibleMove = getPossibleMove(pawn);

                if (possibleMove != null) {

                    boardStorage.getTile(pawn.getPawnTileId()).removePawn();
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

                diceRolled = false;

                if (checkWincondition()) {
                    isDone=true;
                    throw new IllegalArgumentException("Player " + PlayerColor.values()[ getCurrentPlayerId()] + " has won");
                }

                if (dice.getLastRolled() != 6) {
                    switchTurn();
                }
            } else{
                throw new IllegalArgumentException("Not your turn");
            }

        } else {
            throw new IllegalArgumentException("Roll the dice");
        }


    }


    private boolean checkWincondition() {


        for(int i=0;i<4;i++){
            Pawn[] pawns =  boardStorage.getPlayerPawns(i);
            boolean won= true;
            for (Pawn pawn : pawns) {
                if (pawn.getPawnTileId().charAt(2) != 'H') {
                    won = false;
                }
            }
            if(won){
                return true;
            }
        }
        return false;
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
    public int getCurrentPlayerId() {
        return currentTurn;
    }

    @Override
    public List<Player> getPlayers() {
        return lobby.getPlayers();
    }

    @Override
    public void sendMessage(String message) {
        lobby.addMessage(lobby.getPlayerByIndex(0), message);
    }

    @Override
    public List<String> getMessages() {

        List<String> messages = new ArrayList<>();
        for (LobbyMessage message : lobby.getMessages()) {
            messages.add(message.getPlayer().getName() + ": " + message.getMessage()
                    + " [" + message.getLocalDateTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
                    + "]");

        }
        return messages;
    }

    @Override
    public Tile getPossibleMove(Pawn pawn) {


        Tile curTile = boardStorage.getTile(pawn.getPawnTileId());
        if (curTile.getType().charAt(2) == 'P') {

            if (dice.getLastRolled() == 6) {

                Stream<Tile> stream = boardStorage.getTiles().stream()
                        .filter(t -> t.getColor().equals(pawn.getPlayerColor().toString()) && t.getType().equals("WLK"));

                return stream.findFirst().get();
            }
            return null;
        }

        Tile possibleMove;
        possibleMove = getWalkingTilePossibleMove(pawn, curTile);

        if (pawn.getPawnTileId().equals((pawn.getFullId().substring(0, 2) + "H04"))) {
            return null;
        }
        if (pawn.getStepsTaken() + dice.getLastRolled() >= boardStorage.getTileAmountOf("WLK")) {

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


            Tile tile =  boardStorage.getTile((pawn.getFullId().substring(0, 2) + "H0" + t));
            if(tile.getPawn()!=null){
               if( tile.getPawn().getPlayerColor() == pawn.getPlayerColor()){
                   return null;
               }
            }

            return tile;
        }

        return possibleMove;
    }

    private Tile getWalkingTilePossibleMove(Pawn pawn, Tile curTile) {
        Tile possibleMove;
        List<Tile> walkables = getTiles().stream()
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateLobbyView() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteLobbyView() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinLobby(int lobbyId) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void readyUp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leaveLobby() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startGame() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }
}
