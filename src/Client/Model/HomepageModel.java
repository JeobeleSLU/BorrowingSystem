package Client.Model;

import Client.Service.SingletonRequestService;
import Server.Controller.TransactionController;
import Server.Model.Equipment;

import java.io.File;
import java.util.ArrayList;

public class HomepageModel {

    public HomepageModel() {
    }
    ArrayList<String>retrieveFilters(){
        return null;
    }

    public File requestEquipment(String request) {
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

    public TransactionController stroreTransacToMem() {
        return new TransactionController("./Client/Cache/response.xml");
    }

    public File request(String search, String search1) {
        String requestNode[] = new String[]{
          search
        };
        ArrayList<String> node = new ArrayList<>();
        node.add(search1);
        return SingletonRequestService.createXMLRequest("SEARCH",node,requestNode);
    }

//    public File reqestTransaction(Equipment equipmentToBeBorrowed, String startTime, String endTime, String mont) {
//        /*
//         <Equipment>
//        <isAvailable>true</isAvailable>
//        <quantity>3</quantity>
//        <name>newnewnew</name>
//        <id>988</id>
//        <type>Switch</type>
//    </Equipment>
//         */
//        String[] node  = {
//                "isAvailable","quantity","name","id","type,","
//        }
//        return SingletonRequestService.createXMLRequest("TRANSACT")
//    }
}
