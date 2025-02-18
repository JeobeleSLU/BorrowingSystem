package Client.Model;

import Common.Factories.SingletonEquipmentFactory;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;
import Server.Model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class will facilitate the transactions
 */
public class EquipmentManager {

    XMLParser parser;
    XMLCreator writer;
    ArrayList<Equipment> equipmentArrayList;
    FileHandler handler;
    File equipmentXML;
    /*
     <Equipment>
        <isAvailable>true</isAvailable>
        <quantity>3</quantity>
        <name>CiscoServer</name>
        <id>11</id>
        <type>Server</type>
    </Equipment>
     */

    public EquipmentManager() {
        initComponents();

        equipmentXML = handler.getXMLFile("Equipment");
        equipmentArrayList = retrieveData();
    }
   public EquipmentManager(String file) {
        initComponents();
        equipmentXML = new File(file);

        equipmentXML = handler.getXMLFile("Equipment");
        equipmentArrayList = retrieveData();
    }

    private void initComponents() {
        writer = new XMLCreator();
        parser = new XMLParser();
        handler = new FileHandler();
    }

    private ArrayList<Equipment> retrieveData() {
        return parser.parse(SingletonEquipmentFactory.getInstance(),equipmentXML);
    }

    ArrayList<String> getFilters(){
        //for unique
        HashSet<String> uniqueTypes = new HashSet<>();
        equipmentArrayList.forEach(e -> uniqueTypes.add(e.getType()));

        return new ArrayList<>(uniqueTypes);
    }
    ArrayList<Equipment>filterItems(String filter){
        return new ArrayList<>( equipmentArrayList.stream()
                .filter(e-> e.getType()
                        .equals(filter)).toList());
    }
    public String addEquipment(ArrayList<String> attributes){
        /*
           "type",
                "id",
                "name",
                "quantity"
         */
        for (int i = 0; i < attributes.size(); i++){
            System.out.println(attributes);
            System.out.println(i);
        }
        AtomicInteger quantity = new AtomicInteger(Integer.parseInt(attributes.get(3))) ;

        Equipment equipmentToAdd = new Equipment(true,quantity,attributes.get(2),attributes.get(1),attributes.get(0) );
        if (writer.createXML(equipmentToAdd,"Equipment")){
            equipmentArrayList.clear();
            equipmentArrayList.addAll( retrieveData());
            return "1";
        }else {
            return "-1";
        }

    }



    public ArrayList<Equipment> getEquipmentArrayList() {
        return equipmentArrayList;
    }

    /**
     *
     * @param equipment
     * @return
     *   -1  for no equipment left
     *   0 for the successful
     * pseudocode :
     *         check if the equipment that the user will borrow has a quantity > 1
     *         if the user borrow the equipment bind the transaction to the id
     *         log it to check the transaction
     *
     */
    public synchronized boolean transact(Equipment equipment){

            Equipment equipment1 = equipmentArrayList.stream().
                    filter(e-> e.getId()
                            .equals(equipment.getId()))
              .findFirst()
              .stream()
              .toList()
              .get(0);
      if (equipment1.getQuantity().get() < 1){
          System.out.println("no more equipment");
          return false;
      }else equipment1.getQuantity().getAndDecrement();

      equipmentArrayList.forEach(e-> System.out.println(e.getAllValues()));
        return true;


    }

    public String[] getNodes() {
        return new String[]{
                "type",
                "id",
                "name",
                "quantity"
        };
    }

    public String getResponse(boolean result) {
        if (result){
            return "1";
        }else
            return "-1";
    }
}
