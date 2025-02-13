package Server.Model; /**
 * This is the base class of the equipment
 */

import Client.Model.ImageHandler;
import Common.Utilities.FileLogger;
import Common.Utilities.XMLTemplate;

import java.util.concurrent.atomic.AtomicInteger;

public class Equipment implements XMLTemplate {
    FileLogger logger = new FileLogger(XMLTemplate.class);
    String name;
    String type;
    AtomicInteger quantity; // Not sure for atomicity
    String id;
    boolean isAvailable;
    String imagePath;

    //           "type",
    //                "id",
    //                "name",
    //                "quantity"
    //                ,"image",
    //                "isAvailable"
    public Equipment( boolean isAvailable, AtomicInteger quantity, String name, String id, String type) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.id = id;
        this.isAvailable = isAvailable;
        this.imagePath = imagePath;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AtomicInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(AtomicInteger quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String getAllValues() {
        return new StringBuilder()
                .append("type:").append(this.type).append(",")
                .append("id:").append(this.id).append(",")
                .append("name:").append(this.name).append(",")
                .append("quantity:").append(this.quantity).append(",")
                .append("isAvailable:").append(this.isAvailable)
                .toString();
    }

    @Override
    public String[] getDataMembers() {
        return new String[]{
                "type",
                "id",
                "name",
                "quantity"
                ,"image",
                "isAvailable"
        };
    }
}
