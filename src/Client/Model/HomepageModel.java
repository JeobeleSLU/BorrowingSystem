package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class HomepageModel {

    public HomepageModel() {
    }
    ArrayList<String>retrieveFilters(){
        return null;
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
}
