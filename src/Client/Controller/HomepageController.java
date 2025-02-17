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
        addActionEventListners();
    }

    private void addActionEventListners() {
        view.borrowButton().addActionListener(e-> {
            requestEquipment();
        });
    }

    private void getResponse() {
        equipment = model.storeEqToMemory();
        view.populateTableList(model.storeEqToMemory().getEquipmentArrayList());
    }

    private void requestEquipment() {
        server.sendXMLToServer(model.requestEquipment("EQUIPMENT"));
    }
}
