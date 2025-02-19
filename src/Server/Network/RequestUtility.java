package Server.Network;

import Common.Utilities.XMLTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestUtility {

    public static String getRequest(File file){
        String[] node = {"Request"};
                ArrayList<String> req= getContent(file,node);
               return req.get(0);
    }

    public static ArrayList<String> getContent(File file, String[] nodesToGet) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.normalizeDocument();
            for (String node : nodesToGet) {
                NodeList list = document.getElementsByTagName(node);

                if (list.getLength() > 0 && list.item(0) != null) {
                    arrayList.add(list.item(0).getTextContent().trim());
                } else {
                    //TODO: MalfromedReqyest
                    System.err.println("Warning: Node '" + node + "' not found in the XML.");
                }
            }
            arrayList.forEach(e-> System.out.println(e));
            return arrayList;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param response
     * @param nodes
     * @param file
     */
    static public File createXMLResponse(ArrayList<String> response, String[] nodes,File file ) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringElementContentWhitespace(true);

                try {
                    DocumentBuilder docBuilder = factory.newDocumentBuilder();
                    Document doc = docBuilder.newDocument();
                    Element root = doc.createElement("Response");
            doc.appendChild(root);
            for (int i = 0; i < nodes.length; i++) {
                Element element = doc.createElement(nodes[i]);
                element.appendChild(doc.createTextNode(response.get(i)));
                root.appendChild(element);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            return file;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends XMLTemplate> boolean buildObjectXML(T object, String type, File file) {
        HashMap<String, String> elements = getAllVariables(object.getAllValues());
        return buildXML(elements, type,file);
    }


    /*
    Todo: Handle use case where xml already exists and if the item already exist so that u just append on it
     */
    private static boolean buildXML(HashMap<String, String> elements, String type,File file) {

        try {

            if (type == null) {
                return false;
            }

            DOMSource domSource = buildDOMSource(elements, type,file);
            if (domSource == null) {
                return false;
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(file);

            // LOg successful creation
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource,result);
//            fileHandler.saveXML(transformer,domSource,result);
            return true;
        } catch (ParserConfigurationException | TransformerException e) {
            return false;
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static DOMSource buildDOMSource(HashMap<String, String> elements, String type, File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc;
        Element root;

        doc = docBuilder.newDocument();
        root = doc.createElement(type+"s");
        doc.appendChild(root);

        if (!file.exists() || file.length() == 0) { //file doesn't exist or is empty, create new
            doc = docBuilder.newDocument();
            root = doc.createElement(type+"s");
            doc.appendChild(root);
        } else {
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();
            removeWhitespaceNodes(doc.getDocumentElement());
            root = doc.getDocumentElement(); // use existing root
        }

        Element tempRoot = doc.createElement(type);
        root.appendChild(tempRoot);

        if (elements.isEmpty()) {
            return null;
        }
        //Hashmap first
        Iterator<Map.Entry<String, String>> iterator = elements.entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        // The first entry becomes the main element

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Element temp = doc.createElement(entry.getKey());
            temp.appendChild(doc.createTextNode(entry.getValue()));
            tempRoot.appendChild(temp);
        }

        return new DOMSource(doc);
    }

    private static HashMap<String, String> getAllVariables(String allValues) {
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
    private static void removeWhitespaceNodes(Node node) {
        for (int i = node.getChildNodes().getLength() - 1; i >= 0; i--) {
            Node child = node.getChildNodes().item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()) {
                node.removeChild(child);
            } else if (child.hasChildNodes()) {
                removeWhitespaceNodes(child);
            }
        }
    }

}
