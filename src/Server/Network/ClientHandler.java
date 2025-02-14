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
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    String sessionID;
    String idNumber;
    FileHandler handler;
    File saveFile;
    String response;

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

        while (true){
            recieveRequest();
            respondToRequest();
        }

    }

    private void respondToRequest() {
        String request = RequestUtility.getRequest(saveFile);
//FIXME!
        if (request.equals("AUTH")){
            //Get the nodes of the login attributes
            authenticateUser();
            /*
            The response arraylist is responsible for the text content
            the array of attributes is responsible for the node names
             */
            ArrayList<String> res = new ArrayList<>();
            res.add(response);
            sendToStream(res,auth.getResponseAttributes());
        }else if (request.equals("SIGNUP")){
            createUser();
        }
    }

    private void createUser() {
        ArrayList<String> attributes = RequestUtility.getContent(saveFile,auth.getSingUpAttributes());
       int response =  auth.createUser(attributes.toArray(new String[0]));

    }

    private void authenticateUser() {
        /*
        get the content of the xml files
         */
        String response = String.valueOf(auth.authenticate(RequestUtility
                .getContent(saveFile, auth.getLoginAttributes())));
    }


    void sendToStream(ArrayList<String> res, String[] responseAttributes) {
        RequestUtility.sendResponse(res,responseAttributes,response);
        File file = new File(response);
        long size = file.length();
         try {
             //Tell the client the size of the file
             outputStream.writeLong(size);
             sendResponseXML(file);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
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
            throw new RuntimeException("Error while sending file", e);
        }
    }


    private void recieveRequest() {
        try {
            String originalFileName = sessionID;
            long fileSize = inputStream.readLong();

             saveFile = new File(handler.getXMLFile("Cache")+sessionID);
             response = new File(handler.getXMLFile("Cache") + sessionID + "Response").getName();
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;

            //ensure right size
            while (totalRead < fileSize && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) > 0) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
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
