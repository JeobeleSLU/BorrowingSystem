package Server.Network;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
            return arrayList;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
    static public void sendResponse(ArrayList<String> response,String[] nodes,String responsepath ){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("Response");
            doc.appendChild(root);
            for (int i = 0; i < nodes.length; i++){
                Element element = doc.createElement(nodes[i]);
                element.appendChild(doc.createTextNode(response.get(i)));
                root.appendChild(element);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }
}
