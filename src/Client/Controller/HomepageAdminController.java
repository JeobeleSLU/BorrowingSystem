package Client.Controller;
import Client.Model.EquipmentManager;
import Client.Model.HomepageAdminModel;
import Client.Service.SingletonSocketService;
import Client.View.homePageAdmin;
import Server.Controller.TransactionController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                System.out.println("Transaction requesting admin");

                view.populateTableList2(transactions.getArrayList(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String equipmentName = e.getActionCommand();
                        returnItem(equipmentName);
                        System.out.println("Returning: " + equipmentName);
                        //TODO: ADD RETURN LOGIC (REQUEST TO SERVER)
                        JOptionPane.showMessageDialog(null, "Returned: " + equipmentName, "Return Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
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
                requestEquipment();
            }
        });
    }


    private void requestEquipment() {
        server.sendXMLToServer(model.requestEquipment("EQUIPMENT"));
        getResponse();
    }
    private void getServerResponse() {
        String response =  model.parseServerResponse();
        if (response.equals("1")){
            view.showValid(view.getItemToRemove());
        }else
            view.showInvalidEnter();
    }

    private void getResponse() {
        equipment = model.storeEqToMemory();
        equipment.getEquipmentArrayList().forEach(e-> System.out.println("Search query: "+ e.getName()));
        view.populateEquipmentTable(equipment.getEquipmentArrayList(), this::handleRemoveButtonClick);
    }

    private void handleRemoveButtonClick(ActionEvent actionEvent) {
        server.sendXMLToServer(model.requestRemove(view.getItemToRemove()));
        getServerResponse();
    }

    private void returnItem(String equipmentName) {
        server.sendXMLToServer(model.updateEquipmentReturn(equipmentName));
        server.sendXMLToServer(model.createRequest("TRANSACTION_HISTORY_ADMIN"));
        transactions = model.storeTransactionsToMemory();
    }


}

