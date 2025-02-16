package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class RegistrationModel {
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

}
