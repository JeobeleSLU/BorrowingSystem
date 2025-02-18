package Client.Controller;

import Client.Model.EquipmentManager;
import Client.Model.HomepageModel;
import Client.Service.SingletonSocketService;
import Client.View.HomepageClient;
import Common.Model.Transaction;
import Server.Controller.TransactionController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomepageController {
    HomepageClient view;
    HomepageModel model;
    SingletonSocketService server;
    EquipmentManager equipment;
    TransactionController transaction;

    public HomepageController() {
        view = new HomepageClient();
        model = new HomepageModel();
        view.setVisible(true);
        server = SingletonSocketService.getInstance();
        requestEquipment();
        getResponse();
//        addActionEventListners();
        addEventActionListeners();
    }

//    private void addActionEventListners() {
//        view.borrowButton().addActionListener(e-> {
//            requestEquipment();
//        });
//    }

    private void addEventActionListeners() {
        view.getBorrowedItemlbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.requestEquipment("TRANSACTION_HISTORY");
                System.out.println("TITE");
                transaction = model.stroreTransacToMem();
                view.populateTableList2(transaction.getArrayList());
            }
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
