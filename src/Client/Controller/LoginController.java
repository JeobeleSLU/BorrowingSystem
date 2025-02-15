package Client.Controller;

import Client.Model.LoginModel;
import Client.Service.SingletonSocketService;
import Client.View.login_page;

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
    }


    public static void main(String[] args) {
        LoginController controller = new LoginController();
    }
}
