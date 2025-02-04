import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        FileHandler handler = new FileHandler();
        File file = new File(handler.getFilePath("User")+"User.xml");
        System.out.println("Hello world!");
        User user = new User("john.doe@example.com", 0, "12345", "Doe", "Admin", "John", "securePass");

        XMLCreator xmlCreator = new XMLCreator();
        boolean success = xmlCreator.createXML(user, "User");

        if (success) {
            System.out.println("XML file successfully created.");
        } else {
            System.out.println("Failed to create XML file.");
        }
        Equipment equipment = new Equipment(
                "Laptop",                // type
                "EQ-001",                // id
                "Dell Inspiron",         // name
                new AtomicInteger(5),    // quantity
                "",     // imagePath
                true                     // isAvailable
        );

        boolean trial = xmlCreator.createXML(equipment, "Equipment");
        System.out.println(trial);

        XMLParser parser = new XMLParser();
        ArrayList<User> users = parser.parse(SingletonUserFactory.getInstance(), file);
        users.forEach(e-> System.out.println(e.getAllValues()));
    }
}