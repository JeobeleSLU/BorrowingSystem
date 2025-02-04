import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Use this for centralized file IO operations
 */
public class FileHandler {
    public FileHandler(){
    }
    public static String getFilePath(String type) {
        switch (type) {
            case "Equipment":
                return "res/Server/Equipment/";
            case "User":
                return "/res/Server/User";
            default:
                return null;
        }
    }
    public static File getOrCreateFile(String path) {
        File file = new File(path);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                //Log this
            } else {
                //log this
            }
        }

        return file;
    }

    public static boolean saveXML(Transformer transformer, DOMSource domSource, StreamResult result) {
        try {
            transformer.transform(domSource,result);
            return true;
        } catch (TransformerException e) {
            FileLogger.severe(e.getMessage());
            //log this
            return false;
        }
    }
}
