package player;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * As per Effective Java Book the usage of Serializable proxies is more safe*/

/* Having a domain model here - we are using a Serializable class which makes storing and sending objects way more easyre*/

public class Player implements Serializable {

    private final String playerName;
    /*AtomicInteger uses combination of volatile & CAS (compare and swap)
    to achieve thread-safety for Integer Counter. It is non-blocking in nature and
    thus highly usable in writing high throughput concurrent data structures that can be used under
    low to moderate thread contention.*/
    private AtomicInteger messageCounter;
    private String message;

    public Player(String playerName, AtomicInteger messageCounter, String message) {
        this.playerName = playerName;
        this.messageCounter = messageCounter;
        this.message = message;
    }

    private static final long serialVersionUID = 234098243823485285L;


    private String getName() {
        return playerName;

    }

    public AtomicInteger getMessageCounter() {
        return messageCounter;

    }

    public String getMessage() {
        return message;

    }

    public void setMessage(String message) {
        this.message = message;

    }

    public void setIncrementMessageCounter() {
        this.messageCounter.incrementAndGet();

    }

    public void resPonseRetriever(Player sender) {
        String messageItem = sender.getMessage() + " " + this.getMessageCounter();
        setIncrementMessageCounter();
        this.setMessage(messageItem);
    }


}
