import java.util.ArrayList;

/**
 * This will facilitate and handle the creation of the xml files
 */
public class XMLCreator {
    private XMLCreator() {
    }

    public boolean createXML(Equipment equipment){
        String[] string = getAllVariables(equipment.getAllValues());
        return false;
    }

    private String[] getAllVariables(String allValues){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] members =allValues.split(",");
        for (int i = 0; i < members.length; i++){
             String[] temp= members[i].split(":");
             arrayList.add(temp[0]);
             arrayList.add(temp[1]);
        }
        return arrayList.toArray(new String[0]);
    }
}
