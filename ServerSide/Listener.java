package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Listener {
    private static ServerSocket listenerSocket;
    private static Executor e = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        try {
            listenerSocket = new ServerSocket(8888);
        } catch (SecurityException e) {
            throw new SecurityException("Server was baned for establishing connection : " + e.getMessage());
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException(" Server cannot use port 8888 : " + e2.getMessage());
        } catch (IOException e3) {
            throw new IOException(" an error occurred : " + e3.getMessage());
        }

        while(true) {
            Socket serverSocket ;
            try {
                serverSocket = listenerSocket.accept();
                e.execute(new Server(serverSocket));
            } catch (IOException e) {
                System.err.println("Listening for connection failed : " + e.getMessage());
            }
        }
    }
}
