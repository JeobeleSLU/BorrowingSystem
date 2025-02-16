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
}
