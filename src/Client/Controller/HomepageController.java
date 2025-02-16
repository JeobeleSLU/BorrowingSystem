package Client.Controller;

import Client.Model.EquipmentManager;
import Client.Model.HomepageModel;
import Client.Service.SingletonSocketService;
import Client.View.HomepageClient;

public class HomepageController {
    HomepageClient view;
    HomepageModel model;
    SingletonSocketService server;
    EquipmentManager equipment;

    public HomepageController() {
        view = new HomepageClient();
        model = new HomepageModel();
        view.setVisible(true);
        server = SingletonSocketService.getInstance();
        requestEquipment();
        getResponse();
    }

    private void getResponse() {
        equipment = model.storeEqToMemory();
    }

    private void requestEquipment() {
        server.sendXMLToServer(model.createRequest("EQUIPMENT"));
    }
}
