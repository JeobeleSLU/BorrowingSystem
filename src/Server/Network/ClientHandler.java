package Server.Network;

import Common.Factories.SingletonEquipmentFactory;
import Common.Factories.SingletonTransactionFactory;
import Common.Model.Transaction;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Server.Controller.TransactionController;
import Server.Model.Authenticator;
import Client.Model.EquipmentManager;
import Server.Model.Equipment;

import javax.swing.text.Style;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
    TransactionController transactionController;
    XMLCreator writer ;

    public ClientHandler(Socket client, EquipmentManager equipmentManager, Authenticator authenticator,TransactionController controller)  {
        this.socket = client;
        this.equipmentManager = equipmentManager;
        this.auth = authenticator;
        idNumber = null;
        this.transactionController = controller;
        this.handler = new FileHandler();
        this.sessionID = UUID.randomUUID().toString();
        this.writer = new XMLCreator();
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
            if ((handler.getXMLFile("Equipment").exists())){
                sendResponseXML(handler.getXMLFile("Equipment"));
            }
        }else if (request.equals("TRANSACT")) {
            transact();
        } else if (request.equals("ADD_EQUIPMENT")) {
            addEquipment();
         } else if (request.equals("DISCONNECT")) {
            closeResources();
        } else if (request.equals("TRANSACTION_HISTORY")) {
            sendHistory();
        } else if (request.equals("TRANSACTION_HISTORY_ADMIN")) {
            sendResponseXML(handler.getXMLFile("Transaction"));
        } else if (request.equals("SEARCH")) {
            searchEquipment();
        }else if (request.equals("REMOVE_EQUIPMENT")){
            removeEquipment();
        }else if (request.equals("RETURN_ITEM")){
            reutrnItem();
        }
    }

    private void reutrnItem() {

//        if (transactionController.canReturn())
    }

    private void removeEquipment() {
        String[] node = new String[]{
                "EQUIPMENT"
        };
        ArrayList<String> eqToRemove = RequestUtility.getContent(saveFile,node);
        String response = equipmentManager.searchAndRemove(eqToRemove.get(0));
        String[] nodeResult = {
                "Result"
        };
        ArrayList<String> temp = new ArrayList<>();
        temp.add(response);
        sendToStream(temp,nodeResult);
    }

    private void searchEquipment() {
        String[] node = new String[]{
          "SEARCH"
        };

        ArrayList<String> attri = RequestUtility.getContent(saveFile,node);
        ArrayList<Equipment> equipment = equipmentManager.getSearch(attri.get(0));
        equipment.forEach(e-> RequestUtility.buildObjectXML(e,"Equipment",responseFilePath));
        sendResponseXML(responseFilePath);
    }

    private void sendHistory() {
        ArrayList<Transaction> userTransactions=  transactionController.getUserTransaction(idNumber);
        clearResponseCache();
        userTransactions.forEach(e-> {
            RequestUtility.buildObjectXML(e, "Transaction",responseFilePath);
        });
        sendResponseXML(responseFilePath);
    }

    private void clearResponseCache() {
        if (responseFilePath.exists()){
            System.out.println("Deleting file");
            responseFilePath.delete();
        }
    }

    private void addEquipment() {
        String[] node = {
                "Result"
        };
        ArrayList<String>attributes = RequestUtility.getContent(saveFile,equipmentManager.getNodes());
        String response = equipmentManager.addEquipment(attributes);
        ArrayList<String> res = new ArrayList<>();
        res.add(response);

        sendToStream(res,node);
    }
    /**
     * TODO:
     * Transaction logic on how the equipment will be borrowed
     */
    private void transact() {
        String[] node  = new String[]{
                "result"
        };
//
        String[] borrowNode  = {
                "isAvailable", "quantity", "name", "id", "type", "startTime", "EndTime", "Date"
        };

        ArrayList<String> nodes = RequestUtility.getContent(saveFile,borrowNode);
        String startAndEnd = nodes.get(5)+"-"+nodes.get(6);
        nodes.stream().map(e-> e.replaceAll(":",";"));
        nodes.forEach(e-> System.out.println(e));
        for (int i = 0; i < nodes.size();i++){
            System.out.println(nodes.get(i));
            System.out.println("Index: "+i);
        }
        Equipment equipment = new Equipment(
                Boolean.parseBoolean(nodes.get(0)),new AtomicInteger(Integer.parseInt(nodes.get(1)))
                ,nodes.get(2),
                nodes.get(3),nodes.get(4));

        Transaction transaction = new Transaction(1,nodes.get(2),nodes.get(7),startAndEnd,idNumber,nodes.get(3),true);
//        Transaction transaction = new Transaction(4,"name","date","time","10","eqID");
       transactionController.writeUserToXml(transaction);

        boolean result= equipmentManager.transact(equipment);
        String response = equipmentManager.getResponse(result);
        ArrayList<String> resultNode = new ArrayList<>();
        resultNode.add(response);
        sendToStream(resultNode,node);
    }

    private void createUser() {
        ArrayList<String> attributes = RequestUtility.getContent(saveFile,auth.getSingUpAttributes());
        System.out.println("Attributes");
       int response =  auth.createUser(attributes.toArray(new String[0]));
       ArrayList<String> creationResponse = new ArrayList<>();
       creationResponse.add(String.valueOf(response));

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
        ArrayList<String> request=  RequestUtility.getContent(saveFile, auth.getLoginAttributes());
       ArrayList<String> result= auth.authenticate(request);
       if (result.get(1).equals("1")){
           this.idNumber = request.get(0);
           System.out.println("User ID Number = " + idNumber);
       }
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
            byte[] buffer = new byte[20000000];
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
            System.out.println("Waiting for client's request");
            saveFile = new File(handler.getFilePath("Cache") + sessionID+ "request.xml");
            responseFilePath = new File(handler.getFilePath("Cache") + sessionID + "Response.xml");

            try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                byte[] buffer = new byte[20000000];
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
