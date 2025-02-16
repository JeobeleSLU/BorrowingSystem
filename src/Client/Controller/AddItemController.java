package Client.Controller;
import Client.Service.SingletonSocketService;
import Client.View.addItem;
import Client.Model.AddItemModel;

public class AddItemController {
    addItem view;
    AddItemModel model;
    SingletonSocketService server ;

    public AddItemController() {
        server = SingletonSocketService.getInstance();
        view = new addItem();
        model = new AddItemModel();
        view.getAddButton().addActionListener(e-> {
            reqAddEquipment();
        });
    }
    private void reqAddEquipment() {
        String name = view.getEquipNameFld().getText();
        String qty = view.getTypefld().getText();
        String id = view.getEquipCodeFld().getText();
        String type = view.getTypefld().getText();
        System.out.println("Requesting add to server");
        server.sendXMLToServer(model.sendEquipmentToServer(name,qty,id,type));
    }
}
