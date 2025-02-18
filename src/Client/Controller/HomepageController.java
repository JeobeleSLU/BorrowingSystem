package Client.Controller;

import Client.Model.EquipmentManager;
import Client.Model.HomepageModel;
import Client.Service.SingletonSocketService;
import Client.View.HomepageClient;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Server.Controller.TransactionController;
import Server.Model.Equipment;

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
        addActionEventListners();
    }

    private void addActionEventListners() {
       view.getSearchButton().addActionListener(e-> {
           String search = view.getSearchField().getText();
           server.sendXMLToServer(model.request("SEARCH",search));
           equipment.clear();
           getResponse();
       });

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
        view.getBorrowButton().addActionListener(_ ->{
//            extractDetails();
        } );
    }

//    private void extractDetails() {
//        String eq = view.getItemToBeBorrowed();
//        Equipment equipmentToBeBorrowed = equipment.getSearch(eq).get(0);
//        File file = model.reqestTransaction(equipmentToBeBorrowed,view.getMonth(),view.getStartTime(),view.getEndTime());
//    }

    private void addEventActionListeners() {

    }

    private void getResponse() {

        equipment = model.storeEqToMemory();
        view.populateTableList(model.storeEqToMemory()
                .getEquipmentArrayList());

        System.out.println("Search plss");
        equipment.getEquipmentArrayList().forEach(e-> System.out.println("Search query: "+ e.getName()));

        view.populateTableList(model.storeEqToMemory().getEquipmentArrayList());

    }

    private void requestEquipment() {
        server.sendXMLToServer(model.requestEquipment("EQUIPMENT"));
    }
}
