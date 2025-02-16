package Server.Model;

import Common.Factories.SingletonUserFactory;
import Common.Model.User;
import Common.Utilities.FileLogger;
import Common.Utilities.XMLParser;

import java.util.ArrayList;

public class Authenticator{
    FileLogger logger = new FileLogger(Authenticator.class);
    /**
     * This class will authenticate each session that wil be logged in to the system
     * It will parse xml file and will return true or false it passed the authentication
     * it will check through the directory of the user if the user exists
     * creating new user accopunt will be here
     */
    UserManager manager;
    XMLParser parse;
    public Authenticator() {
        manager = new UserManager();
        logger = new FileLogger(this.getClass());
        //when registering and reseting password (admin) refetch database
    }
    public String[] getLoginAttributes(){
        return new String []{
                "id","password"
        };
    }
    public String[] getResponseAttributes(){
        return new String[]{
         "Role", "Result"
        };
    }

    /**
     *
     * @return
     * 0 = incorrect password
     * 1 = correct password
     * -1 No account
     * returns an arraylist that would contain the id number and the userType
     */
    //For temporary it will get a user but maybe use a byteStream?
    public ArrayList<String> authenticate(ArrayList<String> user){
        ArrayList<String> arrayList = new ArrayList<>();
        String id = user.get(0);
        String password = user.get(1);
        String response = "";

        if (!manager.userExists(id)){
            logger.warning("Create an account first");
            response  ="-1";
            arrayList.add("UNKNOWN");


        }else if (!isCorrectPassword(id,password)){
            logger.warning("Incorrect Password, Try again");
            response = "0";
            arrayList.add("UNKNOWN");
        }
        else{
            logger.info("Successfully Log in");
            User userInfo = getCredentials(id);
            arrayList.add(userInfo.getUserType());
            response = "1";
        }

        arrayList.add(response);
        return arrayList;
            //Todo: Log me!
    }
    /*
    1 for correct
    0 for already existing
     */
    public synchronized int createUseAccount(User user){

        if (manager.userExists(user.getIdNumber())){
            return -1;
            //Todo: Log me pls
        }else {
            // if (checkIfValid(User user)) todo: Validation vodoo
            if (manager.writeUserToXml(user)){
                logger.info("Account Successfully Created");
                //When creating account refetch the database to log the person
                manager.fetchUserData();
                return 1;
            }
            logger.warning("This account already existed");
        }return 0; // For some odd reason that even God doesn't knows
        //todo: How would you even log this ?
    }

    private boolean isCorrectPassword(String id, String password) {
        //some long ass method chain to get the user password and match it
        return manager.getUserLogin(id).getPassword().equals(password);
    }
    public User getCredentials(String id){
        return manager.getUserLogin(id);
    }

    public String[] getSingUpAttributes() {

        return new String[]{
                "mail","id","lastName","firstName","password"
        };
    }

    public int createUser(String [] credentials){
        String userType = "Student";
        /*
        return -1 for invalid transactions
         */
        if (credentials.length !=5){
            return -1;
        }
        /*
        email, failed,id,ln,utype,name,pass
         */
       User user = new User(credentials[0],0,credentials[1],credentials[2],userType,credentials[3],credentials[4]);
       return createUseAccount(user);
    }
    public String[] getCreationNodeResponse(){
        return new String[] {
                "Result"
        };
    }
}
