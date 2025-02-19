package Server.Network;

import java.util.Scanner;

public class ServerHandler {
    static class serverHandler implements Runnable{
        Scanner scanner = new Scanner(System.in);
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("The server is running... would you like to shut it down? [y/n]");
                    char choice = scanner.nextLine().toLowerCase().charAt(0);
                    switch (choice) {
                        case 'y' -> {
                            System.out.println("Server is shutting down..");
                            System.exit(1);
                        }
                        case 'n' -> {
                            break;
                        }
                        default -> System.out.println("Invalid input");
                    }

                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
