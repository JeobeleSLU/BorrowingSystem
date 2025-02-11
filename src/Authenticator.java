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

    /**
     *
     * @return
     * 0 = incorrect password
     * 1 = correct password
     * -1 No account
     */
    //For temporary it will get a user but maybe use a byteStream?
    public int authenticate(String[] user){
        String id = user[0];
        String password = user[1];


        if (!manager.userExists(id)){
            logger.warning("Create an account first");
            return -1;

        }else if (!isCorrectPassword(id,password)){
            logger.warning("Incorrect Password, Try again");
            return 0;
        }
        else{
            logger.info("Successfully Log in");
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
            //if (checkIfValid(User user)) todo: Validation vodoo
            if (manager.writeUserToXml(user)){
                return 1;
            }
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
}
