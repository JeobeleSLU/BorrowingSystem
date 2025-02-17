package Client.Service;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * This class will maintain the connections and will create a connection to the server
 * will facilitate in sending files and receiving.
 */
public class SingletonSocketService {
    private Socket socket;
    DataInputStream inputStream ;
    DataOutputStream outputStream;
    File saveFile;

    private SingletonSocketService(){
        establishConnection();
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Can't create input stream");
        }
    }

    private void establishConnection() {
        try {
            socket = new Socket("localhost",6969);

        } catch (IOException e) {
            System.out.println("Can't establish connection");
            JOptionPane.showMessageDialog(null,
                    "Can't Connect to server", "Warning", 2);
        }
    }

    public void sendXMLToServer (File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            receiveResponse();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Can't Connect to server", "Warning", 2);
            System.out.println("Error Reading file");
        }
    }

    /**
     * Receive the XML file from the stream and saves it
     * inside the cache folder and saves it inside the response.xml
     */
    private void receiveResponse() {
        System.out.println("waiting for response");

        try {
            saveFile = new File("./Client/Cache/response.xml");

            try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                    break;
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Can't Connect to server", "Warning", 2);
            System.err.println("Error receiving file: " + e.getMessage());
        }
    }
    private static class SingletonHelper{
        private static final SingletonSocketService INSTANCE = new SingletonSocketService();
    }
    public static SingletonSocketService getInstance(){
        return SingletonHelper.INSTANCE;
    }
}

//public class BillPughSingleton {
//
//    private BillPughSingleton(){}
//
//    private static class SingletonHelper {
//        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
//    }
//
//    public static BillPughSingleton getInstance() {
//        return SingletonHelper.INSTANCE;
//    }
//}
