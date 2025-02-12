package Client.Model;

import Common.Utilities.FileLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageHandler {
    FileLogger logger = new FileLogger(ImageHandler.class);
    private ImageHandler(){
    }
    public static String encodeImageToBase64(String imagePath)  {
        try{
            File file = new File(imagePath);
        FileInputStream imageInFile = new FileInputStream(file);
        byte[] imageData = new byte[(int) file.length()];
        imageInFile.read(imageData);
        imageInFile.close();
        return Base64.getEncoder().encodeToString(imageData);
    }catch (IOException e){
         return null;
        }
    }

    public BufferedImage base64ToImage(String encoded) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(encoded);

        // Convert byte array to BufferedImage
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }
}
