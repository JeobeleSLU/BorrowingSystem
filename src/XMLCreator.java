import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This will facilitate and handle the creation of the xml files
 */
public class XMLCreator {
    FileHandler fileHandler;
    public XMLCreator() {
        fileHandler = new FileHandler();
    }

    public <T extends XMLTemplate> boolean createXML(T object, String type) {
        HashMap<String, String> elements = getAllVariables(object.getAllValues());
        System.out.println(elements);
        return buildXML(elements, type);
    }

    //Todo:Create a Log if theres an exception for easier debugging
    //Todo: Pacheck FileLogger
    /*
    Todo: Handle use case where xml already exists and if the item already exist so that u just append on it
     */
    private boolean buildXML(HashMap<String, String> elements, String type) {

        try {
            if (type == null) {
                FileLogger.severe("Type is null!");
                return false;
            }

            DOMSource domSource = buildDOMSource(elements, type);
            if (domSource == null) {
                FileLogger.warning("domSource is null");
                return false;
            }

            // Get file path
            String path = fileHandler.getFilePath(type)+type+".xml";

            // Debugging output
            System.out.println("Generated File Path: " + path);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new File
                    (fileHandler.getFilePath(type)+type)+".xml");

            System.out.println((fileHandler.getFilePath(type)+type)+".xml");
            // LOg successful creation
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource,result);
            FileLogger.info("Successfully created "+ type+".xml");
//            fileHandler.saveXML(transformer,domSource,result);
            return true;
        } catch (ParserConfigurationException | TransformerException e) {
            FileLogger.severe(e.getMessage());
           return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private DOMSource buildDOMSource(HashMap<String, String> elements, String type) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc;
        Element root;
        if (!fileHandler.exists(fileHandler.getFilePath(type))) {
            doc = docBuilder.newDocument();
            root = doc.createElement(type);
        } else {
            File file = new File(fileHandler.getFilePath(type));
            if (file.length() == 0) { // if file is empty
                doc = docBuilder.newDocument();
                root = doc.createElement(type);
            } else {
                doc = docBuilder.parse(file); //  only if not empty
                root = doc.getDocumentElement();
            }
        }

        doc.appendChild(root);

        if (elements.isEmpty()) {
            return null;
        }

        //Hashmap first
        Iterator<Map.Entry<String, String>> iterator = elements.entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        // The first entry becomes the main element
        Map.Entry<String, String> firstEntry = iterator.next();
        Element types = doc.createElement(firstEntry.getKey());
        root.appendChild(types);

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Element temp = doc.createElement(entry.getKey());
            temp.appendChild(doc.createTextNode(entry.getValue()));
            root.appendChild(temp);
        }

        return new DOMSource(doc);
    }

    private HashMap<String, String> getAllVariables(String allValues) {
        HashMap<String, String> variables = new HashMap<>();
        String[] members = allValues.split(",");

        for (String member : members) {
            String[] temp = member.split(":");
            if (temp.length == 2) {
                variables.put(temp[0].trim(), temp[1].trim());
            }
        }

        return variables;
    }
}
