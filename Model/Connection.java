package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
    private static Socket socket ;
    private static InputStream socketInput ;
    private static OutputStream socketOutput ;

    public static boolean connect(String ip , int port){
        try {
            socket = new Socket(ip , port);
            socketOutput = socket.getOutputStream();
            socketInput = socket.getInputStream();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
