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
//import java.net.ConnectException;
//import java.net.Socket;
//
//public class Client extends BaseSocket {
//
//    public Client() throws ConnectException {
//        super();
//
//        try (Socket socket = new Socket(Declarations.hostname, Declarations.port);
//             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream is = new ObjectInputStream(socket.getInputStream());) {
//
////            player = new PlayerConstructor().setPlayerName(Declarations.player2).messageInitializer().getPlayer();
//
//
//            easyPrint("Server found");
//            easyPrint("Initializing Client");
//            easyPrint("Client Connected!");
//
//            player.resPonseRetriever(player);
//            easyPrint("Sending From Client: " + player.getMessage());
//            os.writeObject(player);
//
//
//            letsTalk(player, is, os);
//        } catch (ConnectException e) {
//            throw new ConnectException();
//        } catch (IOException | ClassNotFoundException e) {
//            LogThis.logStuff(e.getMessage(), e);
//        }
//    }
//
//
//    void letsTalk(Player player, ObjectInputStream is, ObjectOutputStream os) throws IOException, ClassNotFoundException {
//        while ((initiator = (Player) is.readObject()) != null) {
//            if (initiator.getMessageCounter().intValue() == Declarations.messageCount && player.getMessageCounter().intValue() == Declarations.messageCount) {
//                super.exitGracefully();
//            }
//            player.resPonseRetriever(initiator);
//            easyPrint("Sending From Client: " + player.getMessage());
//            os.reset();
//            os.writeObject(player);
//        }
//    }
//}
