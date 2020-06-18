package talkative.app;

import talkative.app.dir.Declarations;
import talkative.app.player.*;
import talkative.app.socket.server.client.ClientInitiator;
import talkative.app.socket.server.client.PlayerClient;
import talkative.app.socket.server.server.ServerSock;

import java.io.IOException;

import java.util.concurrent.CountDownLatch;

/**
 *
 *
 *   Main entry point of the application with 2 different games depending on the args input.
 *
 *   {@link #playSocket} will start a game simulating two client communicating over a network with sockets
 *
 *   {@link #} will start a game of two simple Thread communicating with queues
 *
 *    Both games anyway are based on a count down that will stop after required condition is satisfied for the initiator.
 *
 *    {@link CountDownLatch} will help to wait for the condition to be completed
 */



// TODO: 2020. 06. 18. refactoring
public class Start extends StartMain {

    private static Start startInstance = new Start();

    public void play(ClientInitiator initiator, PlayerClient playerX, CountDownLatch latch) throws InterruptedException, IOException
    {
        System.out.println("Game started ...");

        SocketSender initiatorDispatcher = new SocketSender();
        initiatorDispatcher.setPlayer(PlayerEnum.INITIATOR);
        initiatorDispatcher.setSocket(initiator.getSocketString());

        initiatorDispatcher.setMessage("Message number 1 form InitiatorClient : Hello world!");

        initiator.send(initiatorDispatcher);

        latch.await();

        System.out.println("Game over");

    }


    public void play(Initiator initiator, PlayerConstructor playerX, CountDownLatch latch) throws InterruptedException {
        System.out.println("Game started ...");

        /**
         * Initiator send first message
         */
        Sender dispatcher = new Sender();
        dispatcher.setPlayer(PlayerEnum.INITIATOR);
        dispatcher.setMessage("Message number 1 from Initiator : Hello world!");
        initiator.putMessage(dispatcher);

        /**
         * talkative.app.Start threads and wait until count down latch goes to zero
         */
        initiator.setDaemon(true);
        initiator.start();

        playerX.setDaemon(true);
        playerX.start();

        latch.await();

        System.out.println(PlayerEnum.INITIATOR.getDescription() + " terminated the game ...");

        System.out.println("... Game over!");

    }


    private void startThread() throws InterruptedException {
        Initiator inititator = new Initiator(getInitiatorQueue(), getCountDown(), getPlayerXQueue(), getLatch());
        PlayerConstructor playerConstructor = new PlayerConstructor(getPlayerXQueue(), getInitiatorQueue());

        play(inititator, playerConstructor, getLatch());
    }

    public void startServer() throws IOException
    {

        new ServerSock(Declarations.port);

    }

    private void playSocket() throws Exception
    {

        ClientInitiator clientInitiator = new ClientInitiator(Declarations.hostname, Declarations.port, getLatch(), getCountDown());

        play(clientInitiator, new PlayerClient(Declarations.hostname, Declarations.port), getLatch());
    }


    public static void main(String[] args) throws Exception {
        if ("THREAD".equals(args[0])) {
            startInstance.startThread();
        }


        if ("SOCKET".equals(args[0]))
        {

            startInstance.startServer();
            startInstance.playSocket();
        }



    }
}
