import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    XMLParser parser;
    private ConcurrentHashMap<String, User> users;

    private final File filePath = new File("res/Server/User");
    public UserManager(){
        //user path
        users = new ConcurrentHashMap<>();
        parser = new XMLParser(filePath);
        fetchUserData();
    }
    public synchronized void  fetchUserData() {
        users.clear();
        ArrayList<User> temp = parser.parse(SingletonUserFactory.getInstance());
        temp.forEach(e -> users.put(e.getIdNumber(),e));
        temp.clear();
    }

    boolean userExists(String idNumber){
        return users.containsKey(idNumber);
    }
    User getUserLogin(String idNumber){
        return users.get(idNumber);
    }
}
