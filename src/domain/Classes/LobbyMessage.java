package domain.Classes;

import java.time.LocalDateTime;

public class LobbyMessage{

    private Player player;
    private String message;
    private LocalDateTime localDateTime;

    public LobbyMessage(Player player, String message, LocalDateTime localDateTime) {
        this.player = player;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
