package Common.Factories;

import Common.Model.Transaction;

public class SingletonTransactionFactory implements Factory {

    /*

     */
    @Override
    public Transaction createObject(String[] members) {
//        userId:eqID,equipmentId:date,equipmentName:name,time:id,date:time,qty:qty
       for (int i = 0; i < members.length; i++){
           System.out.println("Unique: " + members[i]);
           System.out.println(i);
       }
       /*
       Unique: qty
                0
                Unique: name
                1
                Unique: time
                2
                Unique: id
                3
                Unique: eqID
                4
                Unique: date
                5
        */
//        String qty,String equipmentName,String date,String time, String userId,String equipmentId

        String name = members[1];
        int qty = Integer.parseInt(members[0]);
        String eqID = members[4];
        String date = members[5];
        String userID = members[3];
        String time = members[2];

        return new Transaction(qty,name,date,time,userID,eqID,true);
    }

    @Override
    public String getClassName() {
        return "Transaction";
    }

    @Override
    public String[] getDataMembers() {
        return new String[] {
                "qty",
                "equipmentName",
                "time",
                "userId",
                "equipmentId",
                "date",
                "isBorrowed"
        };
    }
    private SingletonTransactionFactory(){}

    private static class SingletonHelper{
        private static final SingletonTransactionFactory INSTANCE= new SingletonTransactionFactory();
    }
    public static SingletonTransactionFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }
}

