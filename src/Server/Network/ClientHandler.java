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
    File responseFilePath;

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
            System.out.println("Conenction Lost");
        }
    }

    @Override
    public void run() {
      while (!socket.isClosed()){
          try {
              if (inputStream.available() > 0){
                  System.out.println(inputStream.available());
                  receiveRequest();
                  respondToRequest();
              }
          } catch (IOException e) {
              System.out.println("Cannot receive Request");
          }
      }
    }

    /**
     * This would response to the resquest that the client will do
     */

    private void respondToRequest() {
        if (!saveFile.exists() || saveFile.length() == 0) {
            System.out.println("Request file is empty or missing.");
            return;
        }

        String request = RequestUtility.getRequest(saveFile);
        System.out.println("Client Request:" + request);
        if (request.equals("AUTH")){
            System.out.println("Authenticating");
           authenticateUser();
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
       ArrayList<String> creationResponse = new ArrayList<>();

       sendResponseXML(RequestUtility.createXMLResponse(creationResponse,auth.getCreationNodeResponse(),saveFile));
    }

    /**
     *
     * @return Arraylist of Response
     * First get the content of the xml files and will authenticate
     * the user using the authenticator which then put the response inside the
     * arraylist based on the login and then returns the user type
     */
    private void authenticateUser() {
       ArrayList<String> result= auth.authenticate(RequestUtility.getContent(saveFile, auth.getLoginAttributes()));
       sendToStream(result, auth.getResponseAttributes());
    }


    /**
     *
     * @param res
     * @param responseAttributes
     * build an xml response based on the response attributes and will be saved inside the
     * response file Path
     */
    private void sendToStream(ArrayList<String> res, String[] responseAttributes) {
        System.out.println("Creating xml ");
        File file  = RequestUtility.createXMLResponse(res, responseAttributes, responseFilePath);
        sendResponseXML(file);
    }

    /**
     *
     * @param file
     * Sends the xml to the file input stream byte by byte
     */
    private void sendResponseXML(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                System.out.println("sending");
            }
            System.out.println("Sent");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error while sending XML file", e);
        }
    }


    /**
     * Will reeceive request from the client by saving the xml file
     * the xml would be saved inside the cache folder and and the file name would be the random userID
     */
    private void receiveRequest() {
        try {
            saveFile = new File(handler.getFilePath("Cache") + "ClientRequest.xml");
            responseFilePath = new File(handler.getFilePath("Cache") + sessionID + "Response.xml");

            try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                boolean receivedData = false;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    System.out.println("reading");
                    fileOutputStream.write(buffer, 0, bytesRead);
                    receivedData = true;
                    break;
                }
                if (!receivedData) {
                    System.out.println("No data received from client.");
                }
                System.out.println("Done receiving");
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
