//package connectivity;
//
//import dir.Declarations;
//import logging.LogThis;
//import player.Player;
//import player.PlayerConstructor;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server extends BaseSocket {
//
//    ServerSocket serverSocket;
//    private static Socket client;
//
//
//    public Server() {
//
//        super();
//        try (ServerSocket serverSocket = new ServerSocket(Declarations.port);
//             Socket socket = serverSocket.accept();
//             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream is = new ObjectInputStream(socket.getInputStream());) {
//
//            player = new PlayerConstructor().setPlayerName(Declarations.player1).getPlayer();
//
//            easyPrint("The server is initialized");
//
//            letsTalk(player, is, os);
//
//        } catch (IOException | ClassNotFoundException e) {
//            LogThis.logStuff(e.getMessage(), e);
//
//        }
//    }
//    /* *
//    If input is received from client side, a message is going to be shown, and a reply is going to happen.
//    After the message has been printed out, a check is performed, if the message limit is reached, the program is going to exit
//    Chatting is going on until this limit isn't reached.
//    * */
//
//    void letsTalk(Player player, ObjectInputStream is, ObjectOutputStream os) throws IOException, ClassNotFoundException {
//        while ((initiator = (Player) is.readObject()) != null) {
//            player.resPonseRetriever(initiator);
//            easyPrint("Sending from source(Server): " + player.getMessage());
//            os.reset();
//            os.writeObject(player);
//            if (initiator.getMessageCounter().intValue() == Declarations.messageCount && player.getMessageCounter().intValue() == Declarations.messageCount) {
//                exitGracefully();
//                socket.close();
//            }
//
//        }
//
//
//    }
//}
