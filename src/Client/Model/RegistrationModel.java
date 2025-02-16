package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class RegistrationModel {
    String[] requestNode = {
            "Result"
    };
    ArrayList<String>response;
    public File createRequestXML(String firstName, String lasName, String id, String pass, String email) {
        String[] fields = {
                "mail","id","lastName","firstName","password"
        };
        ArrayList<String> fieldValues = new ArrayList<>();
        fieldValues.add(email);
        fieldValues.add(id);
        fieldValues.add(lasName);
        fieldValues.add(firstName);
        fieldValues.add(pass);
        return SingletonRequestService.createXMLRequest("SIGNUP", fieldValues, fields);
    }

    public String parse() {
        response = new ArrayList<>();
        File file = new File("./Client/Cache/response.xml");
         response.addAll(SingletonRequestService.getContent(file,requestNode));
         return response.get(0);
    }
}
