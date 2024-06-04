package SERVER;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import CLIENT.MySocket;

public class Main {

    public static void main(String[] args) {
        ConcurrentHashMap<String, MySocket> clientList = new ConcurrentHashMap<>();
        try (MyServerSocket myserversocket = new MyServerSocket(5000)) {

            while (true) {
                MySocket socket = myserversocket.accept();
                serverThread serverThread = new Main().new serverThread(socket, clientList);
                serverThread.start();            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public class serverThread extends Thread {
        private  MySocket mysocket;        
        private ConcurrentHashMap<String, MySocket> clientList;

        public serverThread(MySocket socket, ConcurrentHashMap<String, MySocket> clientList) {
            this.mysocket = socket;
            this.clientList = clientList;
        }

        @Override
        public void run() {
            try {
                //BufferedReader input = new BufferedReader(new InputStreamReader(mysocket.getSocket().getInputStream()));
                BufferedReader input = mysocket.getInput();
                PrintWriter output = mysocket.getOutput();
                //this.output = new PrintWriter(mysocket.getSocket().getOutputStream(), true);
                String clientName = input.readLine();
                System.out.println("Nick received " + clientName);
                clientList.put(clientName, mysocket);

                // inifite loop for server
                while (true) {
                    String outputString = input.readLine();
                    // if user types exit command
                    if (outputString.equals("exit")) {
                        break;
                    }
                    System.out.println("Server received " + outputString);
                    broadcast(outputString);
                }
            } catch (Exception e) {
                System.out.println("Error occured: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private void broadcast(String outputString) {
             clientList.forEach((name, value) -> {
                System.out.println("Sending message to " + name);
                value.getOutput().println(outputString);
            });
        }
    }
}