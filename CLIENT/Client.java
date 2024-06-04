package CLIENT;

import java.io.IOException;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        try (MySocket socket = new MySocket(args[0], Integer.parseInt(args[1]))) {
            
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String clientName = "";
            Client.clientThread clientThread = new Client().new clientThread(socket);
            clientThread.start();

            do {
                if (clientName.equals("")) {
                    System.out.println("Nickname: ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    socket.output.println(userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
                } else {
                    String message = clientName + ": " + " message : ";
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    socket.output.println(message + " " + userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
                }
            } while (!userInput.equals("exit"));
        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getStackTrace());
        }
    }

    public class clientThread extends Thread {
        private MySocket socket;

        public clientThread(MySocket s){
            this.socket = s;
        }

        @Override
        public void run() {
            while (true) {
                String response = socket.readLine();
                System.out.println(response);
            }
        }
    }
}