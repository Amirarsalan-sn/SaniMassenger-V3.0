package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Listener {
    private static ServerSocket listenerSocket;
    private static Executor e = Executors.newCachedThreadPool();
    public static Map<String, Person> unameToPass = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        Listener.loadMap();
        try {
            listenerSocket = new ServerSocket(8888);
        } catch (SecurityException e) {
            throw new SecurityException("Server was baned for establishing connection : " + e.getMessage());
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException(" Server cannot use port 8888 : " + e2.getMessage());
        } catch (IOException e3) {
            throw new IOException(" an error occurred : " + e3.getMessage());
        }

        while (true) {
            Socket serverSocket;
            try {
                serverSocket = listenerSocket.accept();
                e.execute(new Server(serverSocket));
            } catch (IOException e) {
                System.err.println("Listening for connection failed : " + e.getMessage());
            }
        }
    }

    private static void loadMap() {
        try (FileInputStream fileInput = new FileInputStream("/ServerSide/map.txt");
             ObjectInputStream objIn = new ObjectInputStream(fileInput)
        ) {
            unameToPass = ((ConcurrentHashMap) objIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Check the classes related to unameToPass : " + e.getMessage());
        } catch (InvalidClassException e2) {
            throw new AssertionError("Probably there is a problem about" +
                    " serialVersionUID of classes related to unameToPass : " + e2.getMessage());
        } catch (StreamCorruptedException e3) {
            throw new AssertionError("You probably have wrote multiple times on this file . " +
                    "the header does not correspond to the rest : " + e3.getMessage());
        } catch (OptionalDataException e4) {
            throw new AssertionError("some thing other than object is in map.txt : " + e4.getMessage());
        }catch (FileNotFoundException e5) {
            throw new AssertionError("cant find map.txt : " + e5.getMessage());
        }catch (EOFException e6) {
            /* ignore because its the first time that server is running and map.txt is empty */
        }catch (IOException e7) {
            throw new AssertionError("some IOException occurred : " + e7.getMessage());
        }
    }
}
