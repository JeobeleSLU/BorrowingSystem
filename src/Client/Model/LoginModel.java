package Client.Model;

import Client.Service.SingletonRequestService;

import java.io.File;
import java.util.ArrayList;

public class LoginModel {
    public static final String requst = "AUTH";
    public static final String[] responseNode = new String[]{
            "Role","Result"
    };
    ArrayList<String> response;

    public LoginModel() {
        response = new ArrayList<>();
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
    void parseXML(){
        response.addAll(SingletonRequestService.getContent(new File("./Client/Cache/response.xml"),responseNode));
    }


    public ArrayList<String> getResponse() {
        if (!response.isEmpty()){
            response.clear();
        }
        parseXML();
        return response;
    }

    public void setUserSessionID(String id) {
        SingletonRequestService.setUserID(id);
    }
}
