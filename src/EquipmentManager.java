import java.io.File;
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
        equipmentArrayList = retrieveData();
        handler = new FileHandler();
        equipmentXML = new File(handler.getFilePath("Equipment"));
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
}
