import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class will parse xml into its corresponding object
 */
public class XMLParser{
    private static File filePath; // filepath of xml
    private String image; // Image filepath of the item
    private String itemName; // Name of the item
    private int qty;         // Quantity of the item
    private String status;   // Status of the item


    // Constructor
    public XMLParser(File filePath, String image, String itemName, int qty, String status) {
        XMLParser.filePath = filePath;
        this.image = image;
        this.itemName = itemName;
        this.qty = qty;
        this.status = status;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        XMLParser.filePath = filePath;
    }

    // Getters and Setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @param objectToBeCreated
     * @param path
     * @return T
     * @param <T>
     *     Pass in a factory  to create an object in runtime
     */
    public <T>ArrayList<T> parse(Factory<T> objectToBeCreated,String path) {
        ArrayList<T> arrayList = new ArrayList<>();
        String[] dataMembers = objectToBeCreated.getDataMembers();
        String[]  attributes= new String[objectToBeCreated.getDataMembers().length];
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            document.getDocumentElement().normalize();

            NodeList objectList = document.getElementsByTagName(objectToBeCreated.getClassName());

            for (int i = 0; i < objectList.getLength(); i++) {

                Node equipmentNode = objectList.item(i);

                if (equipmentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element objectElement = (Element) equipmentNode;
                    //TODO: change tagName if needed

                    /*
                    Loop through the array of data members to get the tags
                    and store all the content of the tag in an array of string attribute
                    create an object based on the factory
                     */
                    for (int j = 0; j < attributes.length; j++){
                        attributes[j] = objectElement.
                                getElementsByTagName(dataMembers[j]).item(0).getTextContent();
                    }
                 arrayList.add(objectToBeCreated.createObject(attributes));
                }
            }
            return arrayList;
        } catch(SAXException | IOException | ParserConfigurationException e){
            throw new RuntimeException(e);
        }
        //TODO: Error handling if null
    }

    public Equipment parseXMLEquipment(){
        return null;
    }

}