package talkative.app.socket.server.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import talkative.app.player.SocketSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *  Basic socket app which is listening to a new connection, and messaging is going to be performed between players.
 * */

public class ServerSock
{
    private Map<String, ConnectionToClient> clientList;
    private LinkedBlockingQueue<SocketSender> messages;
    private ServerSocket serverSocket;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    /**
     * @throws IOException
     */
    public ServerSock(int port) throws IOException
    {
        clientList = new HashMap<>();
        messages = new LinkedBlockingQueue<>();
        serverSocket = new ServerSocket(port);

        Thread accept = new Thread()
        {
            public void run()
            {
                while (true)
                {
                    try
                    {
                        System.out.println("Server listening on port: " + port);

                        Socket s = serverSocket.accept();
                        clientList.put(s.getInetAddress().toString() + s.getPort() + s.getLocalPort(), new ConnectionToClient(s));

                        System.out.println("Connected clients ... " + clientList.size());
                    }
                    catch (IOException e)
                    {
                        logger.log(Level.SEVERE
                                , String.format("Something bad happened...{%s}.")
                                , e.getCause());
                    }
                }
            }
        };

        accept.setDaemon(true);
        accept.start();

        Thread messageHandling = new Thread()
        {
            public void run()
            {
                while (true)
                {
                    try
                    {
                        SocketSender message = messages.take();

                        System.out.println("Server says ... Received from : " + message.getPlayer().getDescription() + " , message : "
                                + message.getMessage());

                        sendMessageToAll(message);
                    }
                    catch (InterruptedException | JsonProcessingException e)
                    {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();

    }


    class ConnectionToClient
    {
        BufferedReader in;
        PrintWriter out;
        Socket socket;

        ConnectionToClient(Socket socket) throws IOException
        {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread read = new Thread()
            {
                public void run()
                {
                    while (true)
                    {
                        try
                        {
                            String obj = in.readLine();
                            messages.put(objectMapper.readValue(obj, SocketSender.class));
                        }
                        catch (IOException | InterruptedException e)
                        {
                            logger.log(Level.SEVERE
                                    , String.format("Something bad happened...{%s}.")
                                    , e.getCause());
                        }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        public void write(SocketSender obj) throws JsonProcessingException
        {

            out.println(objectMapper.writeValueAsString(obj));
        }
    }

    public void sendMessageToAll(SocketSender message) throws JsonProcessingException
    {
        for (Map.Entry<String, ConnectionToClient> client : clientList.entrySet())
            if (client.getKey().compareTo(message.getSocket()) != 0)
                client.getValue().write(message);
    }
}