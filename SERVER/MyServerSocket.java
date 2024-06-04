package SERVER;

import java.net.ServerSocket;
import java.net.Socket;

import CLIENT.MySocket;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket(int port) throws Exception {
        super(port);
    }

    @Override
    public MySocket accept(){
        try{
            Socket socket = super.accept();
            MySocket mySocket = new MySocket(socket);
            System.out.println("Acceptat: " + socket.getInetAddress().getHostName() + " " + socket.getLocalPort());            
            return mySocket;
        }catch(Exception e){
            System.out.println("Error acceptant: " + e.getStackTrace());
        }
        return null;
    }

}
