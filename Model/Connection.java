package Model;

import java.io.*;
import java.net.Socket;

public class Connection {
    private static Socket socket ;
    private static InputStream socketInput ;
    private static OutputStream socketOutput ;
    private static ObjectInputStream objIn ;
    private static ObjectOutputStream objOut ;
    private static String ip = "127.0.0.1";
    private static int port = 8888;

    public static boolean connect() {
        try {
            socket = new Socket(ip , port);
            socketOutput = socket.getOutputStream();
            socketInput = socket.getInputStream();
            objOut = new ObjectOutputStream(socketOutput);
            objIn = new ObjectInputStream(socketInput);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static Message send(Message message) throws AssertionError {
        try {
            objOut.writeObject(message);
        } catch (NotSerializableException e) {
            throw new AssertionError("Message class is not serializable : " + e.getMessage());
        } catch (InvalidClassException e2) {
            throw new AssertionError("Some thing is wrong with "
                    +message.getClass().getSimpleName() + " : " + e2.getMessage());
        } catch (IOException e3) {
            return new ErrorMessage("Some thing is wrong with the connection (sockets,internet ...) : "
            + e3.getMessage()+ "\n You can also click on refresh button and try again .");
        }
        return new Message();
    }

    public static Message receive() throws AssertionError{
        Message message;
        try {
            message = ((Message) objIn.readObject());
        }catch (ClassNotFoundException e ) {
            throw new AssertionError("Check the consistency of ServerSide's Message classes " +
                    "and ClientSide's Message classes : " + e.getMessage());
        }catch (InvalidClassException e2) {
            throw new AssertionError("Check the Message classes' serialVersionUID : " + e2.getMessage());
        }catch (StreamCorruptedException e3) {
            throw new AssertionError("Something is wrong writing the object headers in stream : " + e3.getMessage());
        }catch (OptionalDataException e4) {
            throw new AssertionError("A primitive data type is sent over the network check it : " + e4.getMessage());
        }catch (IOException e5) {
            return new ErrorMessage("Something is wrong with Your connection (sockets,internet ...) : "
                    + e5.getMessage() + "\n You can also click on refresh button and try again .");
        }
        return message;
    }

    public static boolean isOpen () {
        try {
            objOut.writeObject(new CheckMessage());
        } catch (InvalidClassException e) {
            throw new AssertionError("check the CheckMessage class : " + e.getMessage());
        } catch (NotSerializableException e2) {
            throw new AssertionError("Check Message is not serializable : " + e2.getMessage());
        } catch (IOException e3) {
            return false;
        }
        try {
            CheckMessage respond = ((CheckMessage) objIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Check the consistency of ServerSide's Message classes " +
                    "and ClientSide's Message classes : " + e.getMessage());
        } catch (InvalidClassException e2) {
            throw new AssertionError("Check the Message classes' serialVersionUID : " + e2.getMessage());
        } catch (StreamCorruptedException e3) {
            throw new AssertionError("Something is wrong writing the object headers in stream : " + e3.getMessage());
        } catch (OptionalDataException e4) {
            throw new AssertionError("A primitive data type is sent over the network check it : " + e4.getMessage());
        } catch (ClassCastException e5) {
            throw new AssertionError("something other than CheckMessage has been sent ");
        }catch (IOException e6) {
            return false ;
        }
        return true;
    }
}
