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
            view.getSignUpBtn().addActionListener(e->{
                requestSignUp();
            });
    }

    private void requestSignUp() {
        String firstName = view.getfNameLbl().getText();
        String id = view.getUnField().getText();
        String pass = view.getPassField().getText();
        String email = view.getEmailField().getText();
        String lastName = "";
        server.sendXMLToServer(model.createRequestXML(firstName,lastName,id,pass,email));

    }
}
