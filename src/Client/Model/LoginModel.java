package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class LoginModel {
    public LoginModel() {
    }


   void createServerRequest(){

    }

    public File createRequest(String username, String password) {
        String request = "AUTH";
        /**
         *  "id","password"
         */
        String[] fields = {
                "id","password"
        };
        ArrayList<String> nodes =  new ArrayList<>();
        nodes.add(username);
        nodes.add(password);
        return SingletonRequestService.createXMLRequest(request,nodes,fields);

    }
}
