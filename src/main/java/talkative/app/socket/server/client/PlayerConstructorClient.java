package talkative.app.socket.server.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import talkative.app.player.SocketSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PlayerConstructorClient
{
    private SettingUpConnectionToServer server;
    private LinkedBlockingQueue<SocketSender> messages;
    private Socket socket;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    class SettingUpConnectionToServer
    {
        BufferedReader in;
        PrintWriter out;
        Socket socket;

        SettingUpConnectionToServer(Socket socket) throws IOException
        {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);

            Thread read = new Thread()
            {
                public void run()
                {
                    while (true)
                    {
                        try
                        {
                            SocketSender obj = objectMapper.readValue(in.readLine(), SocketSender.class);
                            messages.put(obj);
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

        private void write(SocketSender obj) throws JsonProcessingException
        {
            out.println(objectMapper.writeValueAsString(obj));
        }

    }

    public void send(SocketSender obj) throws JsonProcessingException
    {
        server.write(obj);
    }

    public String getSocketString()
    {
        return socket.getInetAddress().toString() + socket.getLocalPort() + socket.getPort();
    }

    public SettingUpConnectionToServer getServer()
    {
        return server;
    }

    public LinkedBlockingQueue<SocketSender> getMessages()
    {
        return messages;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setServer(SettingUpConnectionToServer server)
    {
        this.server = server;
    }

    public void setMessages(LinkedBlockingQueue<SocketSender> messages)
    {
        this.messages = messages;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
}
