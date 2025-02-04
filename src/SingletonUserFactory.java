public class SingletonUserFactory<T> implements Factory {
    FileLogger logger = new FileLogger(SingletonUserFactory.class);

    private SingletonUserFactory(){
    }

    @Override
    public User createObject(String[] members) {
        try {
            /*
               <FailedAttempts id="0"/>
    <Id>12345</Id>
    <LastName>Doe</LastName>
    <UserType>Admin</UserType>
    <Name>John</Name>
    <Password>securePass</Password>
             */
            return new User(members[0],Integer.parseInt(members[1]),members[2],members[3],members[4],members[5],(members[6])) ;
        }catch (NumberFormatException e){
            logger.severe("Failed to manufacture user"+e.getMessage());
            return null;
        }
    }

    @Override
    public String getClassName() {
        return "User";
    }

    @Override
    public String[] getDataMembers() {
        //"UserType", "Id", "Name", "LastName","Password" "FailedAttempts",mail
        return new String[]{"Mail", "FailedAttempts", "Id", "LastName", "UserType", "Name", "Password"};
    }

    private static class SingletonHelper{
        private static final SingletonUserFactory INSTANCE = new SingletonUserFactory();
    }
    public static SingletonUserFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
//public class BillPughSingleton {
//
//    private BillPughSingleton(){}
//
//    private static class SingletonHelper {
//        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
//    }
//
//    public static BillPughSingleton getInstance() {
//        return SingletonHelper.INSTANCE;
//    }
//}