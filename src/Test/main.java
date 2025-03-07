package Test;

import Client.Model.EquipmentManager;
import Common.Factories.SingletonEquipmentFactory;
import Common.Factories.SingletonTransactionFactory;
import Common.Model.Transaction;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;
import Server.Controller.TransactionController;
import Server.Model.Equipment;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class main {
    public static void main(String[] args) {
        FileHandler handler = new FileHandler();
        XMLCreator creator = new XMLCreator();
        XMLParser parser = new XMLParser();

        Equipment equipment =
                new Equipment(true, new AtomicInteger(3),"CiscoServer","11","Server");
        creator.createXML(equipment,"Equipment");
        ArrayList<Equipment> eq= parser.parse(SingletonEquipmentFactory.getInstance(),handler.getXMLFile("Equipment"));

//        <Equipment>
//        <isAvailable>true</isAvailable>
//        <quantity>3</quantity>
//        <name>JeobeleNewEquipment</name>
//        <id>66969</id>
//        <type>Fiber</type>
//    </Equipment>
//        Equipment equipment1 = new Equipment(true,new AtomicInteger(1),"asd","66969","fiber");
//        EquipmentManager manager = new EquipmentManager();
//        manager.transact(equipment1);
//        manager.transact(equipment1);
//        manager.transact(equipment1);
//        manager.transact(equipment1);
//
//        creator.createXML(new Transaction(1,"dildo","12:14","10","22","sad"),"Transaction");
//
//




        Transaction transaction = new Transaction(4,"name","date","time","10","eqID",true);
        creator.createXML(transaction,"Transaction");
        ArrayList<Transaction>transactopns  = parser.parse(SingletonTransactionFactory.getInstance(),handler.getXMLFile("Transaction"));
        transactopns.forEach(e-> System.out.println(e.getAllValues()));
        TransactionController controller = new TransactionController();
        controller.getUserTransaction("10").forEach(e-> System.out.println(e.getAllValues()));
    }

}
