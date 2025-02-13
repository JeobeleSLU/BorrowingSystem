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


    }
}
