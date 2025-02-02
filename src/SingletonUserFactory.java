public class SingletonUserFactory<T> implements Factory {

    private SingletonUserFactory(){
    }

    @Override
    public User createObject(String[] members) {
        try {
            return new User(members[0],members[1],members[2],members[3],members[4],members[5],Integer.parseInt(members[6])) ;
        }catch (NumberFormatException e){
            FileLogger.severe("Failed to manufacture user"+e.getMessage());
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
        return new String[]{"UserType", "Id", "Name", "LastName","Password","Mail", "FailedAttempts" };

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