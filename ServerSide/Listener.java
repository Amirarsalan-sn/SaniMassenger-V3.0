package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {
    private static ServerSocket listenerSocket;

    public static void main(String[] args) throws IOException {
        try {
            listenerSocket = new ServerSocket(8888);
        } catch (SecurityException e) {
            throw new SecurityException("Server baned to establish connection : " + e.getMessage());
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException(" Server cannot use port 8888 : " + e2.getMessage());
        } catch (IOException e) {
            throw new IOException(" an error occurred : " + e.getMessage());
        }

        while(true) {
            Socket serverSocket = null;
            try {
                serverSocket = listenerSocket.accept();
            } catch (IOException e) {
                System.err.println("Some client failed to connect : " + e.getMessage());
            }
            new Thread(new Server(serverSocket)).start();
        }
    }
}
