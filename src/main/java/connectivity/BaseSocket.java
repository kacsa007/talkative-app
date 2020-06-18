package connectivity;

import player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/* This class is an abstract class for the client and server classes*/
// TODO: 2020. 06. 16. leiras

public abstract class BaseSocket {
    

    Socket socket = null;
    Player player = null;
    Player initiator = null;

    abstract void letsTalk(Player player, ObjectInputStream is, ObjectOutputStream os) throws IOException, ClassCastException, ClassNotFoundException;

    // TODO: 2020. 06. 16. best way to exit an appllication???  -- try catch after this mate
    protected void exitGracefully() throws IOException {
        System.out.println("Exiting the game Gracefully - limit is reached");
        System.out.println("Socket closed successfully");



    }


    protected void easyPrint(String output) {
        System.out.println(output);
    }


}
