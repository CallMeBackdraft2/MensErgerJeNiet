package server.logic;

import client.domain.classes.LobbyMessage;
import client.domain.classes.LobbyView;
import client.domain.classes.Player;
import client.domain.enums.GameMode;
import server.websockets.Responder;
import shared.Message;
import shared.interfaces.Game;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServerLobby {

    public List<ServerPlayer> serverPlayers = new ArrayList<>();
    public Responder responder;
    private ServerPlayer host;
    private List<LobbyMessage> messages = new ArrayList<>();
    private MultiplayerFourPlayerGame game;
    private boolean isDebugMode;
    private String name;
    private boolean started;


    ServerLobby(ServerPlayer host, boolean isDebugMode) {
        this.host = host;
        serverPlayers.add(host);
        this.isDebugMode = isDebugMode;
        //TEMP

        startGame();
    }

    public String[] getMessages() {
        List<String> list = new ArrayList<>();
        for (LobbyMessage message : messages) {
            list.add(message.getPlayer().getName() + ": " + message.getMessage()
                    + " [" + message.getLocalDateTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
                    + "]");

        }
        return list.toArray(new String[0]);
    }

    public void addPlayer(ServerPlayer serverPlayer) {
        serverPlayer.setId(serverPlayers.size());
        serverPlayers.add(serverPlayer);
        update();
    }

    public void sendToAll(Message message) {
        for (ServerPlayer player : serverPlayers) {
            player.getSession().getAsyncRemote().sendText(message.toJson());
        }
    }

    private void startGame() {
        game = new MultiplayerFourPlayerGame(this, isDebugMode);
        responder = new Responder(game, Game.class);
        update();

    }

    public void update() {
        for (ServerPlayer player : serverPlayers) {
            Message message = new Message("update");
            player.getSession().getAsyncRemote().sendText(message.toJson());
        }
    }

    public void sendChatMessage(ServerPlayer player, String message) {
        messages.add(new LobbyMessage(player.toPlayer(), message, LocalDateTime.now()));
        update();

    }


    void newHandle(ServerPlayer player, Message message) {
        game.setCallerId(player.getId());
        responder.callAndRespond(message, player.getSession());
    }


    void handleMessage(ServerPlayer player, Message message) {

        String methodName = message.getName();
        game.setCallerId(player.getId());
        boolean found = false;
        for (Method method : game.getClass().getMethods()) {
            if (method.getName().equals(methodName)) {
                found = true;
                handle(game, player, message, methodName, method);
            }
        }
        if (!found) {
            for (Method method : this.getClass().getMethods()) {
                if (method.getName().equals(methodName)) {
                    found = true;
                    handle(this, player, message, methodName, method);

                }
            }
        }

//        update();

    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    private void handle(Object target, ServerPlayer player, Message message, String methodName, Method method) {
        try {
            Object returned = method.invoke(target, message.getData());
            player.getSession().getAsyncRemote().sendText(new Message(methodName, returned).toJson());

        } catch (InvocationTargetException e) {
            Message exceptionMessage = new Message("Exception", e.getTargetException());
            player.getSession().getAsyncRemote().sendText(exceptionMessage.toJson());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public LobbyView toLobbyView() {
        LobbyView lobbyView = new LobbyView();
        lobbyView.setGameMode(GameMode.FOURPLAYERBOARD);
        lobbyView.setName(name);
        List<Player> players = new ArrayList<>();
        for (ServerPlayer serverPlayer : serverPlayers) {
            players.add(serverPlayer.toPlayer());
        }
        lobbyView.setPlayers(players);
        return lobbyView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServerPlayer getHost() {
        return host;
    }
}
