import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This will facilitate and handle the creation of the xml files
 */
public class XMLCreator {
    private XMLCreator() {
    }

    public <T extends XMLTemplate> boolean createXML(T object, String type) {
        HashMap<String, String> elements = getAllVariables(object.getAllValues());
        return buildXML(elements, type);
    }

    //Todo:Create a Log if theres an exception for easier debugging
    /*
    Todo: Handle use case where xml already exists and if the item already exist so that u just append on it
     */
    private boolean buildXML(HashMap<String, String> elements, String type) {

        try {
            DOMSource domSource= buildDOMSource(elements,type);
            if (domSource == null){
                return false;
            }
            String path = FileHandler.getFilePath(type.concat(type).concat(".xml"));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new File(path));
            return FileHandler.saveXML(transformer,domSource,result);
        } catch (ParserConfigurationException | TransformerException e) {
           return false;
        }
    }

    private DOMSource buildDOMSource(HashMap<String, String> elements, String type) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element root = doc.createElement(type);
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

        types.setAttribute("id", firstEntry.getValue());

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Element temp = doc.createElement(entry.getKey());
            temp.appendChild(doc.createTextNode(entry.getValue()));
            types.appendChild(temp);
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
