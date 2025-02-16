package Client.Service;

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

/**
 * This class will facilitate creating and parsing xml file fron the server
 */
public class SingletonRequestService {

    /**
     *
     * @param requstType
     * @param requests
     * @param nodes
     * @return xml file
     * Will take in the requestType based on that request type will save it on the client side
     * via xml
     *   /*
     *         Type of Requests:
     *         AUTH - authenticating
     *         SIGNUP - creating Accounts
     *         EQUIPMENT- getting the equipments;
     *         TRANSACT: "Transaction of the user" (WIP)
     *         DISCONNECT: "disconnects the users" (WIP)
     *         --- EOL
     *          */

    static public File createXMLRequest(String requstType,ArrayList<String> requests, String[] nodes ) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        File file = new File("./Client/Cache/Request"+requstType+".xml");
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("Root");
            Element requestHeader  = doc.createElement("Request");
            requestHeader.appendChild(doc.createTextNode(requstType));

            root.appendChild(requestHeader);
            doc.appendChild(root);
            for (int i = 0; i < nodes.length; i++) {
                Element element = doc.createElement(nodes[i]);
                element.appendChild(doc.createTextNode(requests.get(i)));
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
}
//public class BillPughSingleton {
//
//    private BillPughSingleton(){}
//
//    private static class SingletonHelper {
//        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
//    }
//
//    public static BillPughSingleton getInstance() {
//        return SingletonHelper.INSTANCE;
//    }
//}