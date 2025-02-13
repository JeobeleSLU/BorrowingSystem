package Common.Utilities;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Path;

/**
 * Use this for centralized file IO operations
 */
public class FileHandler {
    FileLogger logger = new FileLogger(FileHandler.class);

    public FileHandler(){
    }
    public String getFilePath(String type) {
        String basePath = "./res/Server/";

        switch (type) {
            case "Equipment":
                basePath += "Equipment/";
                break;
            case "User":
                basePath += "User/";
                break;
            case "Transaction":
                basePath += "Transaction/";
                break;
            case "Cache":
                return basePath+="Cache/";
            default:
                return null;
        }

        // check directory
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs(); // create directory if it doesnt exist
        }

        return basePath;
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
   public File getXMLFile(String type){
        return new File(getFilePath(type)+type+".xml");
    }


}
