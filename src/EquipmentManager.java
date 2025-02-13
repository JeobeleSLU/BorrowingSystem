import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * This class will facilitate the transactions
 */
public class EquipmentManager {

    XMLParser parser;
    XMLCreator writer;
    ArrayList<Equipment> equipmentArrayList;
    FileHandler handler;
    File equipmentXML;

    public EquipmentManager() {
        writer = new XMLCreator();
        parser = new XMLParser();
        handler = new FileHandler();
        equipmentXML = new File("./res/Server/Equipment/Equipment.xml");
        equipmentArrayList = retrieveData();
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



    public ArrayList<Equipment> getEquipmentArrayList() {
        return equipmentArrayList;
    }

    /**
     *
     * @param equipment
     * @return
     *   -1  for no equipment left
     *   0 for the successful
     *
     */
    public synchronized int transact(Equipment equipment){
        /*
        Todo: Code this
        pseudocode :
        check if the equipment that the user will borrow has a quantity > 1
        if the user borrow the equipment bind the transaction to the id
        log it to check the transaction
         */
        return  -1;

    }
}
