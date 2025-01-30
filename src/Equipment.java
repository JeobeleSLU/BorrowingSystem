/**
 * This is the base class of the equipment
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Equipment implements XMLTemplate {
    String name;
    String type;
    AtomicInteger quantity; // Not sure for atomicity
    String id;
    boolean isAvailable;

    public Equipment(String name, String type, AtomicInteger quantity, String id, boolean isAvailable) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.id = id;
        this.isAvailable = isAvailable;

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
}
