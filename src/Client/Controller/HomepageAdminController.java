package Client.Controller;
import Client.Model.EquipmentManager;
import Client.Model.HomepageAdminModel;
import Client.Service.SingletonSocketService;
import Client.View.homePageAdmin;
import Server.Controller.TransactionController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomepageAdminController {
  homePageAdmin view;
  HomepageAdminModel model;
  SingletonSocketService server;
  EquipmentManager equipment;
  TransactionController transactions;

    public HomepageAdminController() {
        view = new homePageAdmin();
        model = new HomepageAdminModel();
        view.setVisible(true);
        server  = SingletonSocketService.getInstance();
        requestEquipment();
        getResponse();
        addEventActionListeners();
    }

    private void addEventActionListeners() {
        view.getHistory().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                server.sendXMLToServer(model.createRequest("TRANSACTION_HISTORY_ADMIN"));
                transactions = model.storeTransactionsToMemory();
                view.populateTableTransaction(transactions.getArrayList());
            }
        });
        view.getAddItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new AddItemController();
            }
        });
        view.getEquipment().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });


    }

    private void getResponse() {
        equipment = model.storeEqToMemory();
    }

    private void requestEquipment() {
        server.sendXMLToServer(model.createRequest("EQUIPMENT"));
    }

}
