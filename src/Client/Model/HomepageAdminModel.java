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
}
