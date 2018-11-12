package logic.localImplementation;

import dal.interfaces.BoardStorage;
import dalFactories.DALFactory;
import domain.Classes.*;
import domain.Enums.GameMode;
import domain.Enums.PlayerColor;
import logic.interfaces.Game;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalFourPlayerGame implements Game {

    public Dice dice;
    boolean diceRolled;
    Lobby lobby;
    BoardStorage boardStorage;
    int currentTurn = 0;

    private boolean debugMode;

    public LocalFourPlayerGame(boolean debugMode) {
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);

        lobby = Lobby.getOfflineLobby();
        lobby.playerJoin(new Player(0, "TestHuman"));
        lobby.playerJoin(new Player(1, "TestAI1"));
        lobby.playerJoin(new Player(2, "TestAI2"));
        lobby.playerJoin(new Player(3, "TestAI3"));
        this.debugMode=debugMode;
        dice = new Dice();
    }

    @Override
    public int rollDice() {
        if (diceRolled && !debugMode){
            throw new IllegalArgumentException("move your pawn");
        }
        dice.rollDice();
        boolean canMove =false;
        Pawn[] pawns = boardStorage.getPlayerPawns(currentTurn);
        for (Pawn pawn : pawns) {
            if(getPossibleMove(pawn)!=null)
            {
                canMove = true;

            }
        }
        if(!canMove){

            diceRolled=false;
            switchTurn();

        } else {

            diceRolled = true;
        }
        return dice.getLastRolled();
    }

    private void switchTurn(){
        if (currentTurn == 3){
            currentTurn = 0;
        }
        else {
            currentTurn++;
        }
    }

    @Override
    public void movePawn(String pawnId) {

      if(diceRolled || debugMode) {
        Pawn pawn = getPawn(pawnId);

        if (pawn.getPlayerColor().getValue() == currentTurn) {

            Tile possibleMove = getPossibleMove(pawn);

            if (possibleMove != null) {

                boardStorage.getTile(pawn.getPawnTileId()).removePawn();
                Pawn smashedPawn = possibleMove.getPawn();

                if (smashedPawn != null) {

                    // Places the smashed pawn to the starting tile
                    smashedPawn.setPawnTileId(smashedPawn.getFullId());
                }
                if(!pawn.getPawnTileId().equals( pawn.getFullId())) {
                    pawn.setStepsTaken(pawn.getStepsTaken() + dice.getLastRolled());
                }
                pawn.setPawnTileId(possibleMove.getFullId());

                possibleMove.setPawn(pawn);
            }

            diceRolled = false;

            if(dice.getLastRolled() == 6){
                return;
            }
            else {
                switchTurn();
        }
            //todo throw exception no possible move
        }
        // todo exception not your turn
    }
    else {
          throw new IllegalArgumentException("Roll the dice");
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

            if(dice.getLastRolled()==6) {

                Stream<Tile> stream = boardStorage.getTiles().stream()
                        .filter(t -> t.getColor().equals(pawn.getPlayerColor().toString()) && t.getType().equals("WLK"));

                return stream.findFirst().get();
            }
            return null;
        }

        Tile possibleMove;
        possibleMove = GetWalkingTilePossibleMove(pawn, curTile);

        if(pawn.getPawnTileId().equals((pawn.getFullId().substring(0,2)+"H04"))) {
            return null;
        }
        if(pawn.getStepsTaken()+dice.getLastRolled() >= boardStorage.getTileAmountOf("WLK")) {

            int amountToWalk = pawn.getStepsTaken() + dice.getLastRolled() - boardStorage.getTileAmountOf("WLK") ;

            int t =1;
            int dir = 1;
            amountToWalk = amountToWalk % 6;
            while(amountToWalk>0){
                if(t==4){
                    dir=-1;
                } else if(t==1){
                    dir = 1;
                }
                t+= dir;
                amountToWalk--;
            }


            return boardStorage.getTile((pawn.getFullId().substring(0,2)+"H0"+t));

        }

        if (possibleMove == null) return null;

        return possibleMove;
    }

    private Tile GetWalkingTilePossibleMove(Pawn pawn, Tile curTile) {
        Tile possibleMove;
        List<Tile> walkables = getTiles().stream()
                .filter(t -> t.getType().equals("WLK")).collect(Collectors.toList());
        int i = walkables.indexOf(curTile);
        i += dice.getLastRolled();


        if (i >= boardStorage.getTileAmountOf("WLK")) {
            i -= boardStorage.getTileAmountOf("WLK");
        }
        possibleMove = walkables.get(i);
        if(possibleMove.getPawn()!=null){
            if(possibleMove.getPawn().getPlayerColor() == pawn.getPlayerColor()){
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
