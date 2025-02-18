package Server.Network;

import Client.Model.EquipmentManager;
import Server.Controller.TransactionController;
import Server.Model.Authenticator;
import Server.Network.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This class is where the clients should connect into
 */
public class Server {
    public static void main(String[] args) throws IOException {
        TransactionController controller = new TransactionController();
        EquipmentManager equipmentManager = new EquipmentManager();
        Authenticator authenticator = new Authenticator();
        ServerSocket server= new ServerSocket(6969);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        while (true) {
            Socket client = server.accept();
            threadPool.execute(new ClientHandler(client, equipmentManager, authenticator,controller));
        }
    }
}