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
public class XMLDataProcessor {
    private static File filePath; // filepath of xml
    private String image; // Image filepath of the item
    private String itemName; // Name of the item
    private int qty;         // Quantity of the item
    private String status;   // Status of the item

    // Constructor
    public XMLDataProcessor(File filePath, String image, String itemName, int qty, String status) {
        XMLDataProcessor.filePath = filePath;
        this.image = image;
        this.itemName = itemName;
        this.qty = qty;
        this.status = status;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        XMLDataProcessor.filePath = filePath;
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


    public static ArrayList<String> parse() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            document.getDocumentElement().normalize();

            NodeList equipmentList = document.getElementsByTagName("Equipment");

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < equipmentList.getLength(); i++) {
                Node equipmentNode = equipmentList.item(i);

                if (equipmentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element equipmentElement = (Element) equipmentNode;
                    //TODO: change tagName if needed

                    String id = equipmentElement.getAttribute("Id");

                    String image = equipmentElement.getElementsByTagName("Image").item(0).getTextContent();
                    String itemName = equipmentElement.getElementsByTagName("itemName").item(0).getTextContent();
                    String qty = equipmentElement.getElementsByTagName("Qty").item(0).getTextContent();
                    String status = equipmentElement.getElementsByTagName("Status").item(0).getTextContent();

                    list.addAll(Arrays.asList(id, image, itemName, qty, status));

                }
                return list;
            }
        } catch(SAXException | IOException | ParserConfigurationException e){
            throw new RuntimeException(e);
        }
        //TODO: Error handling if null
        return null;
    }

    public Equipment parseXMLEquipment(){
        return null;
    }

}