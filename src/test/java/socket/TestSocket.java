package socket;

import org.junit.jupiter.api.Test;
import talkative.app.Start;
import talkative.app.StartMain;
import talkative.app.dir.Declarations;
import talkative.app.socket.server.client.ClientInitiator;
import talkative.app.socket.server.client.PlayerClient;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*
Test for socket class. This is going to test the countdown method to be sure the game exists after a limit is
reached.

* */

public class TestSocket extends StartMain
{

    private Start main = new Start();

    @Test
    public void testSocketGame() throws Exception
    {

        main.startServer();

        ClientInitiator initiatorClient = new ClientInitiator("127.0.0.1", Declarations.port, getLatch(), getCountDown());
        main.play(initiatorClient, new PlayerClient("127.0.0.1", Declarations.port), getLatch());

        assertEquals(0, getCountDown().get());

    }

}