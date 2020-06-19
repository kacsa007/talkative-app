package talkative.app;

import talkative.app.dir.Declarations;
import talkative.app.player.Sender;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/** This small helper class is used for defining common objects for both play type. Helps out the tests as well*/

public class StartMain {

    private AtomicInteger countDown = new AtomicInteger(Declarations.messageCount);
    private CountDownLatch latch = new CountDownLatch(Declarations.messageCount);
    private BlockingQueue<Sender> initiatorQueue = new LinkedBlockingQueue<>(Declarations.messageCount);
    private BlockingQueue<Sender> playerConsQueue = new LinkedBlockingQueue<>(Declarations.messageCount);
    public AtomicInteger getCountDown()
    {
        return countDown;
    }

    public CountDownLatch getLatch()
    {
        return latch;
    }

    public BlockingQueue<Sender> getInitiatorQueue()
    {
        return initiatorQueue;
    }

    public BlockingQueue<Sender> getPlayerXQueue()
    {
        return playerConsQueue;
    }

}
