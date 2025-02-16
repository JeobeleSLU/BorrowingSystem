package Client.Controller;
import Client.Model.EquipmentManager;
import Client.Model.HomepageAdminModel;
import Client.Service.SingletonSocketService;
import Client.View.homePageAdmin;

public class HomepageAdminController {
  homePageAdmin view;
  HomepageAdminModel model;
  SingletonSocketService server;
  EquipmentManager equipment;

    public HomepageAdminController() {
        view = new homePageAdmin();
        model = new HomepageAdminModel();
        server  = SingletonSocketService.getInstance();
        requestEquipment();
    }
    private void getResponse() {
        equipment = model.storeEqToMemory();
    }

    private void requestEquipment() {
        server.sendXMLToServer(model.createRequest("EQUIPMENT"));
    }
}
