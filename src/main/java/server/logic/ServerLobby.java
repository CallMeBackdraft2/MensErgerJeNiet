package server.logic;

import client.domain.classes.LobbyMessage;
import com.google.gson.Gson;
import shared.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServerLobby {

    List<ServerPlayer> serverPlayers = new ArrayList<>();
    private ServerPlayer host;
    private List<LobbyMessage> messages;
    private MultiplayerFourPlayerGame game;
    private boolean isDebugMode;

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
    }

    public void sendToAll(Message message) {
        for (ServerPlayer player : serverPlayers) {
            player.getSession().getAsyncRemote().sendText(message.toJson());
        }
    }

    private void startGame() {
        game = new MultiplayerFourPlayerGame(this,isDebugMode);
    }

    public void update() {
        for (ServerPlayer player : serverPlayers) {
            Message message = new Message("update");
            player.getSession().getAsyncRemote().sendText(message.toJson());
        }

    }

    public void sendChatMessage(ServerPlayer player, String message) {
        messages.add(new LobbyMessage(player.toPlayer(), message, LocalDateTime.now()));
    }

    void handleMessage(ServerPlayer player, Message message) {

        String methodName = message.getName();
        for (Method method : game.getClass().getMethods()) {
            if (method.getName().equals(methodName)) {
                try {
                    Object returned = method.invoke(game, message.getData());
                        player.getSession().getAsyncRemote().sendText(new Message(methodName, returned).toJson());

                } catch (Exception e) {
                    Message exceptionMessage = new Message("Exception",e);
                    player.getSession().getAsyncRemote().sendText(exceptionMessage.toJson());
                }
            }
        }

//        update();

    }

}
