package Client.Model;

import Client.Service.SingletonRequestService;
import Server.Controller.TransactionController;

import java.io.File;
import java.util.ArrayList;

public class HomepageAdminModel {
    public static final String[] nodes = {
        "type",
                "id",
                "name",
                "quantity"
    };
    public HomepageAdminModel() {
    }

    public File createRequest(String request) {
        String[] requestNode = new String[] {
                "equipment"
        };
        ArrayList<String> nodes = new ArrayList<>();
        nodes.add("equipment");
        return SingletonRequestService.createXMLRequest(request,nodes,requestNode);
    }

    public EquipmentManager storeEqToMemory() {
        return new EquipmentManager("./Client/Cache/response.xml");
    }

    public TransactionController storeTransactionsToMemory() {
        return new TransactionController("./Client/Cache/response.xml");
    }

    public File requestEquipment(String request) {
        String[] requestNode = new String[] {
                "equipment"
        };
        ArrayList<String> nodes = new ArrayList<>();
        nodes.add("equipment");
        return SingletonRequestService.createXMLRequest(request,nodes,requestNode);
    }


    public TransactionController stroreTransacToMem() {
        return new TransactionController("./Client/Cache/response.xml");
    }


    public File requestRemove(String itemToRemove) {
        String[] requestNode = new String[] {
                "EQUIPMENT"
        };
        ArrayList<String> nodes = new ArrayList<>();
        nodes.add(itemToRemove);
        return SingletonRequestService.createXMLRequest("REMOVE_EQUIPMENT",nodes,requestNode);
    }
    public String parseServerResponse() {
        String[] NODE_TO_RECEIVE = new String[]{
                "Result"
        };

        ArrayList<String> response = new ArrayList<>();
        File file = new File("./Client/Cache/response.xml");
        response.addAll(SingletonRequestService.getContent(file,NODE_TO_RECEIVE));
        return response.get(0);
    }
}
