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

    public File reqestTransaction(Equipment equipmentToBeBorrowed, String startTime, String endTime, String month,String day) {

        String[] node  = {
                "isAvailable","quantity","name","id","type","startTime","EndTime","Date"
        };
        String date = month + "colon" + day;
        ArrayList<String> response= new ArrayList<>();
        response.add("TRUE");
        response.add("1");
        response.add(equipmentToBeBorrowed.getName().replace(":", "-"));
        response.add(equipmentToBeBorrowed.getId().replace(":", "-"));
        response.add(equipmentToBeBorrowed.getType().replace(":", "-"));
        response.add(startTime.replace(":", "-"));
        response.add(endTime.replace(":", "-"));
        response.add(date.replace(":", "-"));

        response.forEach(e-> System.out.println(e));
        return SingletonRequestService.createXMLRequest("TRANSACT",response,node);
    }
}
