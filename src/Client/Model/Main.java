package Client.Model;

import Common.Factories.SingletonTransactionFactory;
import Common.Model.Transaction;
import Common.Utilities.FileHandler;
import Common.Utilities.FileLogger;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileLogger logger = new FileLogger(Main.class);
        FileHandler handler = new FileHandler();
        System.out.println("Hello world!");
//
//        User user1 = new User("alice.smith@example.com", 1, "1001", "Smith", "Admin", "Alice", "password123");
//        User user2 = new User("bob.johnson@example.com", 0, "1002", "Johnson", "User", "Bob", "securePass");
//        User user3 = new User("charlie.brown@example.com", 2, "1003", "Brown", "Moderator", "Charlie", "modPass!");
//        User user4 = new User("dana.white@example.com", 3, "1004", "White", "Guest", "Dana", "guestPass");
//        User user5 = new User("evan.lee@example.com", 0, "1005", "Lee", "Admin", "Evan", "adminSecure99");
//        User user6 = new User("frank.miller@example.com", 1, "1006", "Miller", "User", "Frank", "userPass123");
//        User user8 = new User("henry.taylor@example.com", 2, "1008", "Taylor", "User", "Henry", "henryPass");
//        User user9 = new User("isabel.martin@example.com", 0, "1009", "Martin", "Admin", "Isabel", "isabelSecure");
//        User user10 = new User("jackson.clark@example.com", 1, "1010", "Clark", "Guest", "Jackson", "guestPass2024");

        XMLCreator xmlCreator = new XMLCreator();
//        boolean success = xmlCreator.createXML(user10, "User");
//       xmlCreator.createXML(user9, "User");
//         xmlCreator.createXML(user2, "User");
//         xmlCreator.createXML(user8, "User");
////  xmlCreator.createXML(user1, "User");
////         xmlCreator.createXML(user2, "User");
////         xmlCreator.createXML(user3, "User");
//        User user = new User("john.doe@example.com", 0, "12345", "Doe", "Admin", "John", "securePass");
//
//
//        if (success) {
//            System.out.println("XML file successfully created.");
//        } else {
//            System.out.println("Failed to create XML file.");
//        }
//        Equipment equipment = new Equipment(
//                "Laptop",                // type
//                "EQ-001",                // id
//                "Dell Inspiron",         // name
//                new AtomicInteger(5),    // quantity
//                "",     // imagePath
//                true                     // isAvailable
//        );

//        boolean trial = xmlCreator.createXML(equipment, "Equipment");
//        System.out.println(trial);
        File file = new File(handler.getFilePath("User")+"User.xml");

        XMLParser parser = new XMLParser();
//        ArrayList<User> users = parser.parse(SingletonUserFactory.getInstance(), file);
//        users.forEach(e-> System.out.println(e.getAllValues()));

        Transaction transaction = new Transaction(3,"Cisco Switch", "13 40","2241","2242");
        File transactionFile = new File("Transaction");
        boolean hello = xmlCreator.createXML(transaction, "Transaction");

        File trans = new File(handler.getFilePath("Transaction")+"Transaction.xml");
        ArrayList<Transaction> transactions =
                parser.parse(SingletonTransactionFactory.getInstance(),trans);

        System.out.println(hello);

        transactions.forEach(e-> System.out.println( e.getAllValues()));
    }
}