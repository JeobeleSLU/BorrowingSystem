import java.util.concurrent.atomic.AtomicInteger;

public class Equipment {
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
}
