package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class AddItemModel {
    public static final String[] NODES_TO_SEND = new String[] {
        "type",
                "id",
                "name",
                "quantity"
    };
    private static final String[] NODE_TO_RECEIVE = new String[]{
            "Result"
    };

    public AddItemModel() {
    }

    public File sendEquipmentToServer(String name, String qty, String id, String type) {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(type);
        attributes.add(id);
        attributes.add(name);
        attributes.add(qty);
        return SingletonRequestService.createXMLRequest("ADD_EQUIPMENT",attributes,NODES_TO_SEND);
    }

    public String parseServerResponse() {
       ArrayList<String> response = new ArrayList<>();
        File file = new File("./Client/Cache/response.xml");
        response.addAll(SingletonRequestService.getContent(file,NODE_TO_RECEIVE));
        return response.get(0);
    }
}
