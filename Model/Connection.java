package Model;

import java.io.*;
import java.net.Socket;

public class Connection {
    private static Socket socket ;
    private static InputStream socketInput ;
    private static OutputStream socketOutput ;

    public static boolean connect(String ip , int port) {
        try {
            socket = new Socket(ip , port);
            socketOutput = socket.getOutputStream();
            socketInput = socket.getInputStream();
        } catch (IOException e) {
            return true;
        }
        return true;
    }

    public static boolean send(Message message) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socketOutput);
            outputStream.writeObject(message);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static Message receive() throws Exception{
        Message message = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            message = ((Message) inputStream.readObject());
        }catch (Exception e ) {
            throw new Exception(e.getMessage());
        }
        return message;
    }
}
