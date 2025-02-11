package Server.Model;

/**
 * This is the base class for the user
 */
public class User implements XMLTemplate {
    FileLogger logger = new FileLogger(User.class);
    String name;
    String lastName;
    String mail; //not sure
    String idNumber;
    //If admin or student
    String userType;
    int failedAttempts;
    String password;


    public User(String mail, int failedAttempts, String id, String lastName, String userType, String name, String password) {
        this.mail = mail;
        this.failedAttempts = failedAttempts;
        this.idNumber = id;
        this.lastName = lastName;
        this.userType = userType;
        this.name = name;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    @Override
    public String getAllValues() {
        StringBuilder builder = new StringBuilder();
        return
                builder.append("Name:").append(this.name).append(",")
                .append("UserType:").append(this.userType).append(",")
                .append("Id:").append(this.idNumber).append(",")
                .append("LastName:").append(this.lastName).append(",")
                .append("Password:").append(this.password).append(",")
                .append("FailedAttempts:").append(this.failedAttempts).append(",")
                .append("Mail:").append(this.mail).append(",")
                .toString();
    }

    //Return this to get all the data members
    @Override
    public String[] getDataMembers() {
        return new String[]{"UserType", "Id", "Name", "LastName","Password","Mail", "FailedAttempts" };

    }
}
