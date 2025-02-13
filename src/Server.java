import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is where the clients should connect into
 */
public class Server {

    public static void main(String[] args) throws IOException {
        EquipmentManager equipmentManager = new EquipmentManager();
        Authenticator authenticator = new Authenticator();
        ServerSocket server= new ServerSocket(6969);
        while (true){
            Socket client = server.accept();
            new Thread(new ClientHandler(client,equipmentManager,authenticator)).start();
        }

    }

}
