package Server.Network;

import Common.Utilities.FileHandler;
import Server.Model.Authenticator;
import Client.Model.EquipmentManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

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
    private InputStream inputStream;
    private OutputStream outputStream;
    String sessionID;
    String idNumber;
    FileHandler handler;
    File saveFile;
    String responseFilePath;

    public ClientHandler(Socket client, EquipmentManager equipmentManager, Authenticator authenticator)  {
        this.socket = client;
        this.equipmentManager = equipmentManager;
        this.auth = authenticator;
        idNumber = null;
        this.handler = new FileHandler();
        this.sessionID = UUID.randomUUID().toString();
        try {
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (!socket.isClosed()){
            receiveRequest();
            respondToRequest();
        }

    }
    /*
              The response arraylist is responsible for the text content
              the array of attributes is responsible for the node names
               */

    private void respondToRequest() {
        String request = RequestUtility.getRequest(saveFile);
        if (request.equals("AUTH")){
            ArrayList<String> res = authenticateUser();
            sendToStream(res,auth.getResponseAttributes());
        }else if (request.equals("SIGNUP")){
            createUser();
        }else if (request.equals("EQUIPMENT")){
            sendResponseXML(handler.getXMLFile("Equipment"));
        }else if (request.equals("TRANSACT")){
            transact();
        } else if (request.equals("DISCONNECT")) {
            closeResources();
        }
    }

    /**
     * TODO:
     * Transaction logic on how the equipment will be borrowed
     */
    private void transact() {
    }

    private void createUser() {
        ArrayList<String> attributes = RequestUtility.getContent(saveFile,auth.getSingUpAttributes());
       int response =  auth.createUser(attributes.toArray(new String[0]));


    }

    /**
     *
     * @return Arraylist of Response
     * First get the content of the xml files and will authenticate
     * the user using the authenticator which then put the response inside the
     * arraylist based on the login and then returns the user type
     */
    private ArrayList<String> authenticateUser() {

        return auth.authenticate(RequestUtility
                .getContent(saveFile, auth.getLoginAttributes()));
    }


    /**
     *
     * @param res
     * @param responseAttributes
     * First create an xml and the
     */
    private void sendToStream(ArrayList<String> res, String[] responseAttributes) {
        File file  = RequestUtility.createXMLResponse(res, responseAttributes, responseFilePath);
        sendResponseXML(file);
    }

    private void sendResponseXML(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error while sending XML file", e);
        }
    }


    private void receiveRequest() {
        try {
            saveFile = new File(handler.getXMLFile("Cache") + sessionID);
            responseFilePath = new File(handler.getXMLFile("Cache") + sessionID + "Response").getName();

            try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

        } catch (IOException e) {
            System.err.println("Error receiving file: " + e.getMessage());
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
