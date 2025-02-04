import java.util.logging.Logger;

public class Authenticator {
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
        //when registering and reseting password (admin) refetch database
    }

    /**
     *
     * @return
     * 0 = incorrect password
     * 1 = correct password
     * -1 No account
     */
    //For temporary it will get a user but maybe use a byteStream?
    public int authenticate(User user){

        if (!manager.userExists(user.getIdNumber())){
            return -1;
            //Todo: Log me!

        }else if (!isCorrectPassword(user)){
            FileLogger.warning("Incorrect Password, Try again");
            return 0;
        }
        else{
            FileLogger.info("Successfully Log in");
            return 1;
        }

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
            if (manager.writeUserToXml(user)){
                return 1;
            }
        }return 0; // For some odd reason that even God doesn't knows
        //todo: How would you even log this ?
    }
    private boolean isCorrectPassword(User user) {
        //some long ass method chain to get the user password and match it
        return manager.getUserLogin(user.getIdNumber()).getPassword().equals(user.getPassword());
    }
}
