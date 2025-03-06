
package Server.Network;

import Client.Model.EquipmentManager;
import Server.Controller.TransactionController;
import Server.Model.Authenticator;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This class is where the clients should connect into
 */
public class Server {

    private static boolean running = true;
    public static void main(String[] args) throws IOException {

        String[] ip = String.valueOf(Inet4Address.getLocalHost()).split("/",2);
        JOptionPane.showMessageDialog(null,"Connect to: " + ip[1], "Warning", 1);

        TransactionController controller = new TransactionController();
        EquipmentManager equipmentManager = new EquipmentManager();
        Authenticator authenticator = new Authenticator();
        ServerSocket server = new ServerSocket(6969);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        // Start a separate thread to listen for shutdown command
        Thread shutdownThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                while (running) {
                    String command = reader.readLine();
                    if ("stop".equalsIgnoreCase(command)) {
                        System.out.println("Shutting down the server...");
                        running = false;
                        threadPool.shutdown();
                        server.close();
                        System.exit(0);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        shutdownThread.start();

        System.out.println("Server is running. Type 'stop' to shut down.");

        while (running) {
            try {
                Socket client = server.accept();
                threadPool.execute(new ClientHandler(client, equipmentManager, authenticator, controller));
            } catch (IOException e) {
                if (!running) {
                    System.out.println("Server has been stopped.");
                    break;
                }
                e.printStackTrace();
            }
        }
    }
}

