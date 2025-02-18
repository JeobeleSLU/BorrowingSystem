package Client.Controller;
import Client.Model.RegistrationModel;
import Client.Service.SingletonSocketService;
import Client.View.signUpPage;
public class RegistrationController {
    signUpPage view;
    RegistrationModel model;
    SingletonSocketService server;

    public RegistrationController() {
            this.view = new signUpPage();
            this.model = new RegistrationModel();
            server = SingletonSocketService.getInstance();
            view.show();
            view.getSignUpBtn().addActionListener(e->{
                requestSignUp();
                showResult();
            });
    }

    private void showResult() {
        String response = model.parse();
        if (response.equals("1")){
            view.showValidUser();
            view.dispose();
        } else if (response.equals("-1")) {
            view.showErrorMessage();
            view.dispose();
        }
        new LoginController();
    }

    private void requestSignUp() {
        String firstName = view.getFirstNameField().getText();
        String id = view.getUnField().getText();
        String pass = view.getPassField().getText();
        String email = view.getEmailField().getText();
        String lastName = view.getLastNameField().getText();
        server.sendXMLToServer(model.createRequestXML(firstName,lastName,id,pass,email));
    }
}
