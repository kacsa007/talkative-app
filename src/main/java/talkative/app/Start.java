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
 *   The start class is going to start the game based on the user args input. SOCKE/THREAD
 *
 *   Socket will start a game simulating two players ( clients ) talking through a socket connection.
 *
 *   Thread will start threading between players and the communication is performed via queues.
 *
 *
 *   Condition is going to exit the game. If this is reached a message is going to be printed.
 */


public class Start extends StartMain {

    private static Start startInstance = new Start();

    public void play(ClientInitiator initiator, PlayerClient playerX, CountDownLatch latch) throws InterruptedException, IOException
    {
        System.out.println("Game started successfully ...");

        SocketSender initiatorDispatcher = new SocketSender();
        initiatorDispatcher.setPlayer(PlayerEnum.INITIATOR);
        initiatorDispatcher.setSocket(initiator.getSocketString());

        initiatorDispatcher.setMessage("Message number 1 received form ClientInitiator : Hello There...I'm Listening!");

        initiator.send(initiatorDispatcher);

        latch.await();

        System.out.println("Game over");

    }


    public void play(Initiator initiator, PlayerConstructor playerReady, CountDownLatch latch) throws InterruptedException {
        System.out.println("Game started successfully...");

        /**
         * Initiator send first message
         */
        Sender dispatcher = new Sender();
        dispatcher.setPlayer(PlayerEnum.INITIATOR);
        dispatcher.setMessage("Message number 1 from Initiator : Hello there player!");
        initiator.putMessage(dispatcher);

        /**
         * talkative.app.Start threads and wait until count down latch goes to zero
         */
        initiator.setDaemon(true);
        initiator.start();

        playerReady.setDaemon(true);
        playerReady.start();

        latch.await();

        System.out.println(PlayerEnum.INITIATOR.getDescription() + " terminated the game limit is reached :( ...");

        System.out.println("... See you soon :) Have a nice day!");

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
