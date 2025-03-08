package Client.Model;

import Common.Factories.SingletonEquipmentFactory;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;
import Server.Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
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
    /*
     <Equipment>
        <isAvailable>true</isAvailable>
        <quantity>3</quantity>
        <name>CiscoServer</name>
        <id>11</id>
        <type>Server</type>
    </Equipment>
     */
static int transactions = 0;
    public EquipmentManager() {
        initComponents();

        equipmentXML = handler.getXMLFile("Equipment");
        if (handler.getXMLFile("Equipment").exists()){
            equipmentArrayList = retrieveData();
        }
    }
   public EquipmentManager(String file) {
        initComponents();
        equipmentXML = new File(file);

//        equipmentXML = handler.getXMLFile("Equipment");
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
    public synchronized boolean transact(Equipment equipment) {
        System.out.println("Equipment to transact: " + equipment.getId());

        Equipment equipment1 = equipmentArrayList.stream()
                .filter(e -> e.getId().equals(equipment.getId()))
                .findFirst()
                .orElse(null);

        if (equipment1 == null) {
            System.out.println("Equipment not found.");
            return false;
        }

        if (equipment1.getQuantity().get() < 1) {
            System.out.println(equipment1.getQuantity());
            System.out.println("No more equipment available.");

            return false;
        }

        // Decrement quantity
        int newQuantity = equipment1.getQuantity().decrementAndGet();
        System.out.println("New quantity: " + newQuantity);
        transactions++;
        System.out.println("Transactions: "+ transactions);
        reWriteXML();
        equipmentArrayList.clear();
        equipmentArrayList.addAll(retrieveData());

        return true;
    }


    private void reWriteXML() {
        File file = handler.getXMLFile("Equipment");

        if (file.exists()) {
            // **Delete the file first to prevent duplication**
            if (file.delete()) {
                System.out.println("Existing XML file deleted successfully.");
            } else {
                System.out.println("Failed to delete the existing XML file.");
                return;
            }
        }

        // **Write all equipment again**
        equipmentArrayList.forEach(e -> writer.createXML(e, "Equipment"));

        System.out.println("Rewriting XML with updated equipment list.");
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
            return "-1";
        }else
            return "1";
    }

    public ArrayList<Equipment> getSearch(String s) {
        return (ArrayList<Equipment>) equipmentArrayList.stream().filter(e-> e.getName().contains(s)).collect(Collectors.toList());
    }

    public void clear() {
        this.equipmentArrayList.clear();
    }

    public String searchAndRemove(String s) {
        Equipment equipmentToRemove = equipmentArrayList.stream()
                .filter(e -> e.getName().equals(s))
                .findFirst()
                .orElse(null);

        if (equipmentToRemove == null) {
            return "-1`";
        }

        equipmentArrayList.remove(equipmentToRemove);
        reWriteXML(); // Update XML to reflect removal

        return "1";
    }

    public static void updateEquipment(String equipmentName, String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList equipmentList = doc.getElementsByTagName("Equipment");

            for (int i = 0; i < equipmentList.getLength(); i++) {
                Node node = equipmentList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element equipment = (Element) node;
                    String name = equipment.getElementsByTagName("name").item(0).getTextContent();
                    System.out.println("TESING KUNG AABOT BA DITO UNG RUN: " + equipmentList.getLength() + name + equipmentName);
                    if (name.equalsIgnoreCase(equipmentName)) {
                        // Get and increment quantity
                        Element quantityElement = (Element) equipment.getElementsByTagName("quantity").item(0);

                        int quantity = Integer.parseInt(quantityElement.getTextContent());
                        quantityElement.setTextContent(String.valueOf(quantity + 1));

                        // Update availability if it was previously 0
                        Element availabilityElement = (Element) equipment.getElementsByTagName("isAvailable").item(0);
                        if (quantity == 0) {
                            availabilityElement.setTextContent("true");
                        }

                        // Save changes
                        saveXMLChanges(doc, xmlFile);

                        System.out.println("Equipment updated successfully!");
                        return;
                    }
                }
            }

            System.out.println("Equipment not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void saveXMLChanges(Document doc, File xmlFile) throws TransformerException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

}
