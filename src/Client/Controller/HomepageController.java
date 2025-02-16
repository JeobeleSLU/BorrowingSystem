package Client.Controller;

import Client.Model.HomepageModel;
import Client.View.HomepageClient;

public class HomepageController {
    HomepageClient view;
    HomepageModel model;

    public HomepageController() {
        view = new HomepageClient();
        model = new HomepageModel();
        view.setVisible(true);
    }
}
