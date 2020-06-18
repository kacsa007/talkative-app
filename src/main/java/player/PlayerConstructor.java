package player;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerConstructor {
    private String playerName;
    private String message;
    private AtomicInteger messageCounter = new AtomicInteger(0);

    public PlayerConstructor setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;

    }

    // TODO: 2020. 06. 16. Why though??? 
    public PlayerConstructor setmessageCounter(int messageCounter) {
        this.messageCounter.set(messageCounter);
        // TODO: 2020. 06. 16.  this.messageCounter.set(messageCounter);
        return this;
    }

    public Player getPlayer() {
        Player player = new Player(playerName, messageCounter, message);
        return player;
    }

    public PlayerConstructor messageInitializer() {
        this.message = "Hey there";
        return this;
    }

}

