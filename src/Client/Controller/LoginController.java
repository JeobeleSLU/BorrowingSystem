package Client.Controller;

import Client.Model.LoginModel;
import Client.Service.SingletonSocketService;
import Client.View.login_page;

import java.util.ArrayList;

public class LoginController {
    login_page view;
    LoginModel model;
    SingletonSocketService server;


    public LoginController() {
        this.model = new LoginModel();
        this.view = new login_page();
        server = SingletonSocketService.getInstance();
        view.getLoginBtn().addActionListener(e-> {
            requestLogin();
        });
    }

    /**
     * Using the model create the xml and then send a request to the server
     *
     */
    private void requestLogin() {
        String username = view.getUnField().getText();
        String password = view.getPassField().getText();
        server.sendXMLToServer(model.createRequest(username,password));
        viewLogin();
    }

    /*
     * 0 = incorrect password
     * 1 = correct password
     * -1 No account
     */
    private void viewLogin() {
        ArrayList<String> response = model.getResponse();
        String result = response.get(1);
        String role = response.get(0);
        if (result.equals("1")){
            if (role.equals("Student")){
                new HomepageController();
            }else if (role.equals("Admin")){
                new HomepageAdminController();
            }
        }else if (result.equals("0")){
            view.showWrongPasswordPrompt();
        }else if (result.equals("-1")){
            view.showNoUSerPrompt();
        }
    }


    public static void main(String[] args) {
        LoginController controller = new LoginController();
    }
}
