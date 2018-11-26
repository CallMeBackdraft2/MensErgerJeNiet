package client.logic.localimplementation;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.*;
import client.domain.enums.GameMode;
import client.domain.enums.PawnState;
import client.logic.interfaces.Game;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalSixPlayerGame implements Game {

    Dice dice;
    boolean diceRolled;
    Lobby lobby;
    BoardStorage boardStorage;

    public LocalSixPlayerGame() {
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.SIXPLAYERBOARD);
        lobby = new Lobby(new Player(0, "TestHuman"));
        lobby.playerJoin(new Player(1, "TestAI1"));
        lobby.playerJoin(new Player(2, "TestAI2"));
        lobby.playerJoin(new Player(3, "TestAI3"));
        dice = new Dice();
    }

    @Override
    public boolean isDiceRolled() {
        return false;
    }

    @Override
    public void skipTurn() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int rollDice() {

        diceRolled = true;
        return dice.rollDice();
    }


    @Override
    public void movePawn(String pawnId) {
        if (diceRolled) {
            // get the pawn that is given
            Pawn selectedPawn = boardStorage.getPawn(pawnId);

            // if the selected pawn still hasn't been played
            if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() == 6) {
                selectedPawn.movePawnIntoPlay();

                // Depending on the player the pawn gets placed in the correct starting position
                switch (selectedPawn.getPlayerId()) {
                    case 1:
                        boardStorage.movePawn(pawnId, "WL01");
                    case 2:
                        boardStorage.movePawn(pawnId, "WL11");
                    case 3:
                        boardStorage.movePawn(pawnId, "WL21");
                    case 4:
                        boardStorage.movePawn(pawnId, "WL31");
                    default:
                        return;
                }

            } else if (selectedPawn.getPawnState() == PawnState.STARTPOSITION && dice.getLastRolled() != 6) {
                throw new IllegalArgumentException("You just can't");
            }


            Pawn pawn = getPawn(pawnId);
            Tile tile = getPossibleMove(pawn);

            if (tile != null) {

                boardStorage.getTile(pawn.getPawnTileId()).removePawn();

                Pawn smashedPawn = tile.getPawn();
                if (smashedPawn != null) {

                    smashedPawn.setPawnTileId(smashedPawn.getFullId());
                }

                pawn.setPawnTileId(tile.getFullId());
                tile.setPawn(pawn);

            }

        } else {
            throw new IllegalArgumentException("You just can't");
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
        return 0;
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
        if(result.getPawn() != null && result.getPawn().getPlayerColor() == pawn.getPlayerColor()){
            return null;
        }

        return walkables.get(i);
    }

    @Override
    public boolean isYourTurn() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pawn getPawn(String id) {
        return boardStorage.getPawn(id);
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
        throw new UnsupportedOperationException();
    }
}
