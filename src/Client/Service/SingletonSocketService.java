package Client.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class will maintain the connections and will create a connection to the server
 */
public class SingletonSocketService {
    private Socket socket;
    InputStream inputStream ;
    OutputStream outputStream;
    private SingletonSocketService(){
        establishConnection();
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void establishConnection() {
        try {
            socket = new Socket("lcalhost",6969);

        } catch (IOException e) {
            System.out.println("Can't establish connection");
            throw new RuntimeException(e);
        }
    }

    public boolean sendRequestToServer(File file){

        return false;
    }

    private static class SingletonHelper{
        private static final SingletonSocketService INSTANCE = new SingletonSocketService();
    }
    public static SingletonSocketService getInstance(){
        return SingletonHelper.INSTANCE;
    }
}

//public class BillPughSingleton {
//
//    private BillPughSingleton(){}
//
//    private static class SingletonHelper {
//        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
//    }
//
//    public static BillPughSingleton getInstance() {
//        return SingletonHelper.INSTANCE;
//    }
//}
