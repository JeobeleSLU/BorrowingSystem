import java.util.concurrent.atomic.AtomicInteger;

public class SingletonEquipmentFactory implements Getter,Factory {
    private SingletonEquipmentFactory(){}

    @Override
    public Equipment createObject(String[] members) {
        //todo: continue this
//        return new Equipment(members[0],members[1],members[2], ),members[4]);
        return null;
    }


    @Override
    public String[] getDataMembers() {
        return new String[]{
                "type",
                "id",
                "name",
                "quantity"
                ,"image",
                "isAvailable"
        };
    }

    private static class SingletonHelper{
        private static final SingletonEquipmentFactory INSTANCE = new SingletonEquipmentFactory();
    }
    public static SingletonEquipmentFactory getInstance(){
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