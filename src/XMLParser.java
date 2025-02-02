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


    // Constructor
    public XMLParser(File filePath) {
        this.filePath = filePath;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        XMLParser.filePath = filePath;
    }

    /**
     *
     * @param objectToBeCreated
     * @return T
     * @param <T>
     *     Pass in a factory  to create an object in runtime
     */
    public <T>ArrayList<T> parse(Factory<T> objectToBeCreated) {
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