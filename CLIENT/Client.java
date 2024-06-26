package CLIENT;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (MySocket mysocket = new MySocket(args[0], Integer.parseInt(args[1]))) {
            Scanner scanner = new Scanner(System.in);
            String userInput;
            Client.clientThread clientThread = new Client().new clientThread(mysocket);
            clientThread.start();
            System.out.println("Nickname: ");
            userInput = scanner.nextLine();
            String clientName = userInput;
            mysocket.enviarMSg(userInput);

            do {
                userInput = scanner.nextLine();
                mysocket.enviarMSg(clientName + ":   msg : " + userInput);
                if (userInput.equals("exit")) {
                    break;
                }
            } while (!userInput.equals("exit"));
        } catch (Exception e) {
            System.out.println("Exception a main: " + e.getStackTrace());
        }
    }

    public class clientThread extends Thread {
        private MySocket socket;

        public clientThread(MySocket s) {
            this.socket = s;
        }

        @Override
        public void run() {
            while (true) {
                String response = socket.rebreMsg();
                System.out.println(response);
            }
        }
    }
}