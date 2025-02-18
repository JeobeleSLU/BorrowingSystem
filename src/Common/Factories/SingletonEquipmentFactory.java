package Common.Factories;

import Server.Model.Equipment;
import Common.Utilities.FileLogger;
import Common.Utilities.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public class SingletonEquipmentFactory implements Getter, Factory {
    FileLogger logger = new FileLogger(SingletonEquipmentFactory.class);
    private SingletonEquipmentFactory(){}

    @Override
    public Equipment createObject(String[] members) {
        //todo: continue this
        int quantity = Integer.parseInt(members[3]);
        boolean flag = quantity > 1;

        return new Equipment(flag,new AtomicInteger(quantity),members[2],members[1],members[0]);
    }

    @Override
    public String getClassName() {
        return "Equipment";
    }


    @Override
    public String[] getDataMembers() {
        return new String[]{
                "type",
                "id",
                "name",
                "quantity",
                "isAvailable"
        };

    }
    public String[] getRequestMember() {
        return new String[]{
                "type",
                "id",
                "name",
                "quantity",
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