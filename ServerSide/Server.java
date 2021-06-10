package ServerSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Server implements Runnable{
    private Socket socket;
    private InputStream socketInput ;
    private OutputStream socketOutput;
    private boolean errorFlag = true;

    public Server(Socket socket) {
        this.socket = socket;
        try {
            this.socketInput = socket.getInputStream();
            this.socketOutput = socket.getOutputStream();
        } catch (IOException e) {
            errorFlag = false;
        }
    }

    @Override
    public void run() {
        if(errorFlag){
            System.err.println("client : " + socket.getInetAddress() + " connected .");
        }
    }
}
