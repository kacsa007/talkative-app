
package thread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import talkative.app.StartMain;
import talkative.app.Start;
import talkative.app.player.Initiator;
import talkative.app.player.PlayerConstructor;

/**
 *  This is intended for testing the thread game. This tests the counter down to zero, and mocks some thread using mockito spy( some warning are going to be generated after running this mockito.spy.
 *  This mock data finally is going to perform a unittest.
 *
 *
 *
 */


@ExtendWith(MockitoExtension.class)
public class TestThread extends StartMain
{

    private Start main = new Start();

    @Test
    public void testThread() throws InterruptedException
    {

        Initiator initiator = Mockito.spy(new Initiator(getInitiatorQueue(), getCountDown(), getPlayerXQueue(), getLatch()));

        PlayerConstructor playerX = Mockito.spy(new PlayerConstructor(getPlayerXQueue(), getInitiatorQueue()));

        main.play(initiator, playerX, getLatch());

        assertEquals(0, getCountDown().get());

    }
}