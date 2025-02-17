import Client.Model.EquipmentManager;
import Common.Factories.SingletonEquipmentFactory;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;
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
        ArrayList<Equipment> eq= parser.parse(SingletonEquipmentFactory.getInstance(),handler.getXMLFile("Equipment"));

//        <Equipment>
//        <isAvailable>true</isAvailable>
//        <quantity>3</quantity>
//        <name>JeobeleNewEquipment</name>
//        <id>66969</id>
//        <type>Fiber</type>
//    </Equipment>
        Equipment equipment1 = new Equipment(true,new AtomicInteger(1),"asd","66969","fiber");
        EquipmentManager manager = new EquipmentManager();
        manager.transact(equipment1);
        manager.transact(equipment1);
        manager.transact(equipment1);
        manager.transact(equipment1);

    }
}
