package talkative.app.socket.server.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import talkative.app.player.PlayerEnum;
import talkative.app.player.PlayerInterface;
import talkative.app.player.Sender;
import talkative.app.player.SocketSender;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientInitiator extends PlayerConstructorClient implements PlayerInterface {

    private AtomicInteger countDown;
    private CountDownLatch latch;
    private int sentMessages = 1;

    @Override
    public <T extends Sender> void putMessage(T dispatcher) throws JsonProcessingException {
        dispatcher.setMessage("Initiator is sending message number " + ++sentMessages);

        send((SocketSender) dispatcher);

    }

    @Override
    public void takeMessage() throws InterruptedException {
        SocketSender message = getMessages().take();

        System.out.println("Initiator says ... Message Received: " + message.getMessage());

        countDown.decrementAndGet();
        latch.countDown();

        Thread.sleep(1000);


    }

    public ClientInitiator(String IPAddress, int port, CountDownLatch latchIn, AtomicInteger countDownIn) throws Exception
    {
        setSocket(new Socket(IPAddress, port));
        setMessages(new LinkedBlockingQueue<SocketSender>());
        setServer(new SettingUpConnectionToServer(getSocket()));

        this.latch = latchIn;
        this.countDown = countDownIn;

        SocketSender SenderInitiator = new SocketSender();
        SenderInitiator.setPlayer(PlayerEnum.INITIATOR);
        SenderInitiator.setSocket(getSocketString());

        Thread messageHandling = new Thread()
        {
            public void run()
            {
                while (countDown.get() > 0)
                {
                    try
                    {
                        takeMessage();

                        putMessage(SenderInitiator);
                    }
                    catch (InterruptedException | JsonProcessingException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }

        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }


}
