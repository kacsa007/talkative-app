package talkative.app.socket.server.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import talkative.app.player.PlayerEnum;
import talkative.app.player.PlayerInterface;
import talkative.app.player.Sender;
import talkative.app.player.SocketSender;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayerClient extends PlayerConstructorClient implements PlayerInterface {
    private int sentMessages = 0;


    public PlayerClient(String IPAddress, int port) throws Exception
    {
        setSocket(new Socket(IPAddress, port));
        setMessages(new LinkedBlockingQueue<>());
        setServer(new SettingUpConnectionToServer(getSocket()));

        SocketSender dispatcherInitiator = new SocketSender();
        dispatcherInitiator.setPlayer(PlayerEnum.PLAYERX);
        dispatcherInitiator.setSocket(getSocketString());

        Thread messageHandling = new Thread()
        {
            public void run()
            {
                while (true)
                {
                    try
                    {
                        takeMessage();

                        putMessage(dispatcherInitiator);
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



    @Override
    public <T extends Sender> void putMessage(T sender) throws InterruptedException, JsonProcessingException
    {
        sender.setMessage("PlayerX is sending message number " + ++ sentMessages);

        send((SocketSender) sender);

    }

    @Override
    public void takeMessage() throws InterruptedException
    {
        SocketSender message = getMessages().take();

        System.out.println("Player - John says ... Message Received: " + message.getMessage());

    }

}
