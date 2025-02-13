import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Use this for centralized file IO operations
 */
public class FileHandler {
    FileLogger logger = new FileLogger(FileHandler.class);

    public FileHandler(){
    }
    public String getFilePathForXML(String type) {
        switch (type) {
            case "Equipment":
                return "./res/Server/Equipment/Equipment.xml";
            case "User":
                return "./res/Server/User/User.xml";
            case "Transaction":
                return "./res/Server/Transaction/Transaction.xml";
            default:
                return null;
        }
    }
    public File getOrCreateFile(String path) {
        File file = new File(path);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
              logger.info("Creating Directory");
            } else {
                logger.info("There's an existing Directory");
            }
        }

        return file;
    }

    public boolean saveXML(Transformer transformer, DOMSource domSource, StreamResult result) {
        try {
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(domSource,result);
            return true;
        } catch (TransformerException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }
    public boolean exists(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
    public File retrieveXML(String type){
        String path = getFilePathForXML(type);
        return new File(path);
    }
}
