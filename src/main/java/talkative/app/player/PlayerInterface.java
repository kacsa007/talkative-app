package talkative.app.player;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface PlayerInterface {

    <T extends Sender> void putMessage(T dispatcher) throws InterruptedException, JsonProcessingException;

    void takeMessage() throws InterruptedException;
}
