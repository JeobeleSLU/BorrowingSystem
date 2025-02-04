import java.util.logging.Logger;

public class Authenticator {
    /**
     * This class will authenticate each session that wil be logged in to the system
     * It will parse xml file and will return true or false it passed the authentication
     * it will check through the directory of the user if the user exists
     * creating new user accopunt will be here
     */
    UserManager manager;
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
        else return 1;
            //Todo: Log me!
    }
    private boolean isCorrectPassword(User user) {
        //some long ass method chain to get the user password and match it
        return manager.getUserLogin(user.getIdNumber()).getPassword().equals(user.getPassword());
    }
}
