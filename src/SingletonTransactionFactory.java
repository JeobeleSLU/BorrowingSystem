public class SingletonTransactionFactory implements Factory {

    /*
    <qty>2</qty>
        <equipmentName>Cisco</equipmentName>
        <time>220</time>
        <userId>223</userId>
        <equipmentId>224</equipmentId>
     */
    @Override
    public Transaction createObject(String[] members) {

        return new Transaction(Integer.parseInt(members[0]),members[1], members[2], members[3],members[4]);
    }

    @Override
    public String getClassName() {
        return "Transaction";
    }

    @Override
    public String[] getDataMembers() {
        return new String[] {
                "userId",
                "equipmentId",
                "equipmentName",
                "qty",
                "time"
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

