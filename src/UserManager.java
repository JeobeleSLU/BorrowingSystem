import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    FileLogger logger = new FileLogger(UserManager.class);

    private ConcurrentHashMap<String, User> users;
    XMLParser parser;
    XMLCreator writer;


    private final File filePath;
    public UserManager(){
        FileHandler handler = new FileHandler();
        filePath = new File(handler.getFilePath("User")+"User.xml");
        //user path
        users = new ConcurrentHashMap<>();
        parser = new XMLParser();
        writer = new XMLCreator();
        fetchUserData();
    }
    public synchronized void  fetchUserData() {
        users.clear();
        ArrayList<User> temp = parser.parse(SingletonUserFactory.getInstance(),filePath);
        temp.forEach(e -> users.put(e.getIdNumber(),e));
        temp.clear();
    }

    boolean writeUserToXml(User user){
        return writer.createXML(user, "User");
    }

    boolean userExists(String idNumber){
        return users.containsKey(idNumber);
    }
    User getUserLogin(String idNumber){
        return users.get(idNumber);
    }
}
