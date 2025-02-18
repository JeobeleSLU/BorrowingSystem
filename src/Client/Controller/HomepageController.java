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
       view.getSearchButton().addActionListener(e-> {
           String search = view.getSearchField().getText();
           server.sendXMLToServer(model.request("SEARCH",search));
           equipment.clear();
           getResponse();
       });
    }

    private void getResponse() {

        equipment = model.storeEqToMemory();
        view.populateTableList(model.storeEqToMemory()
                .getEquipmentArrayList());

        System.out.println("Search plss");
        equipment.getEquipmentArrayList().forEach(e-> System.out.println("Search query: "+ e.getName()));

    }

    private void requestEquipment() {
        server.sendXMLToServer(model.requestEquipment("EQUIPMENT"));
    }
}
