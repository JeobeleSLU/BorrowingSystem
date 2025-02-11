package Server.Network;

import Server.Model.Authenticator;
import Client.Model.EquipmentManager;

import java.io.*;
import java.net.Socket;

/**
 * This is what will handle the client and will have a shared resources which would be the
 * equipment manager
 *
 * this would take in a a equipment manager as an argument to simulate shared resources
 * Psuedo Code(){
 *     parese the string that the user will send
 *      have a loop that checks if the User input != terminate
 *      ex "RETRIEVE EQUIPMENT"
 *      it should send a bytestream to the user or the client
 *      thats all thank you tinatamad pa ko
 *
 * }
 */
public class ClientHandler implements Runnable {

    Socket socket;
    EquipmentManager equipmentManager;
    Authenticator auth;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket client, EquipmentManager equipmentManager, Authenticator authenticator)  {
        this.socket = client;
        this.equipmentManager = equipmentManager;
        this.auth = authenticator;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String request =  "";
        while ((request = getRequest()) != null && !request.equalsIgnoreCase("Exit")) {
            respondToRequest(request);
        }
        closeResources();
    }

    private void respondToRequest(String request) {
        ObjectOutputStream outputStream;
        if (request.equals("LOGIN")) {
            String[] creds = getUserCredentials();
             reply(auth.authenticate(creds));

        }

    }

    private void reply(Object response) {
        try {
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }

    private String[] getUserCredentials() {
        try {

            return  (String[]) inputStream.readObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRequest() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeResources() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

}
