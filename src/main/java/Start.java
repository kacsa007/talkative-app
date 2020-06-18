import connectivity.Client;
import connectivity.Server;

import java.net.ConnectException;

public class Start {

    public  static void startServer() {
        System.out.println("Server not found");
        System.out.print("Initializing a Server");
        new Server();
    }


    public static void startClient() throws ConnectException {

        System.out.println("Looking for a server to connect");

        try {

            new Client();

        } catch (ConnectException e) {
            throw new ConnectException();
        }

    }


    public static void main(String[] args) {
        try {
            startClient();
        } catch (ConnectException e) {
            startServer();
        }
    }


}
