/**
 * This is the base class for the user
 */
public class User implements XMLTemplate {
    String name;
    String lastName;
    String mail; //not sure
    String idNumber;
    //If admin or student
    String userType;
    int failedAttempts;

    public User(String name, String lastName, String mail, String idNumber, String userType, int failedAttempts) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.idNumber = idNumber;
        this.userType = userType;
        this.failedAttempts = failedAttempts;
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
        return builder.append("UserType:").append(this.userType).append(",")
                .append("Id:").append(this.idNumber).append(",")
                .append("Name:").append(this.name).append(",")
                .append("LastName:").append(this.lastName).append(",")
                .append("Failed Attempts:").append(this.failedAttempts).append(",").toString();
    }
}
