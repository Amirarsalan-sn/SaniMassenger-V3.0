package ServerSide;

import Model.*;

import java.io.*;
import java.net.Socket;
import java.util.*;


public class Server implements Runnable {
    public static final String SERVER_SIDE_PERSONS = "/home/amir/IdeaProjects/untitled2/src/ServerSide/Persons";
    private Socket socket;
    private InputStream socketInput;
    private OutputStream socketOutput;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    /**
     * if one of the getInputStream or getOutputStream operations failed ,
     * server is not responsible and should end it's operation soon .
     * this field is for checking this kind of situation .
     * private boolean errorFlag = false;
     */

    public Server(Socket socket) {
        this.socket = socket;
        try {
            this.socketOutput = socket.getOutputStream();
            this.socketInput = socket.getInputStream();
            objIn = new ObjectInputStream(socketInput);
            objOut = new ObjectOutputStream(socketOutput);
        } catch (IOException e) {
            throw new AssertionError("Unable to establish connection : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        Message currentMessage = null;
        System.err.println("client : " + socket.getInetAddress() + " connected .");
        while (true) {
            try {
                currentMessage = ((Message) objIn.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new AssertionError("Couldn't send message to client " + socket.getInetAddress()
                        + " " + e.getMessage());
            }
            if (currentMessage != null) {
                if (handle(currentMessage))
                    break;
            }
        }
        closeResources();
    }

    private boolean handle(Message currentMessage) {
        switch (currentMessage.getClass().getSimpleName()) {
            case "LoginMessage": {
                LoginMessage loginMessage = ((LoginMessage) currentMessage);
                Message mess;
                mess = uPassCheck(loginMessage);
                send(mess);
                break;
            }
            case "SignInMessage": {
                SignInMessage signInMessage = ((SignInMessage) currentMessage);
                Message mess;
                mess = signInCheck(signInMessage);
                send(mess);
                break;
            }
            case "CheckMessage" : {
                send(new CheckMessage());
            }
            case "CloseMessage": {
                return true;
            }
        }
        return false;
    }

    private Message signInCheck(SignInMessage signInMessage) {
        String uName = signInMessage.uName;
        String[] clientData = {signInMessage.uName, signInMessage.passWord,
                signInMessage.name, signInMessage.lastName, signInMessage.BirthDate};
        Optional<File> person = findPerson(uName);
        if (person.isPresent()) {
            return new ErrorMessage("User : " + uName + " already has an account .");
        }
        try (Formatter formatter = new Formatter(SERVER_SIDE_PERSONS + "/" + uName + ".txt")) {
            for (String data :
                    clientData) {
                formatter.format("%s\n", data);
                formatter.flush();
            }
        } catch (FileNotFoundException e) {
            throw new AssertionError("Unable to create a Date file for " + uName + " : " + e.getMessage());
        }
        return new ConfirmMessage("""
                You have signed in successfully .
                Welcome to SaniMessenger V3.0 .
                """);
    }

    private Message uPassCheck(LoginMessage loginMessage) {
        Optional<File> person = findPerson(loginMessage.Uname);
        if (person.isPresent()) {
            File personFile = person.get();
            try (Scanner scanner = new Scanner(personFile)) {
                scanner.nextLine();
                String pass = scanner.nextLine();
                if (pass.equals(loginMessage.Pass)) {
                    return new BooleanMessage(true);
                }
            } catch (FileNotFoundException e) {
                throw new AssertionError("there is a problem with opening the Client data file : " + e.getMessage());
            }
        }
        return new BooleanMessage(false);
    }

    private Optional<File> findPerson(String uName) {
        File personDir = new File(SERVER_SIDE_PERSONS);
        List<File> persons = Arrays.asList(personDir.listFiles());
        return persons.stream().filter(f -> f.getName().equals(uName + ".txt")).findFirst();
    }

    private void send(Message message) {
        try {
            objOut.writeObject(message);
        } catch (IOException e) {
            throw new AssertionError("there is a problem sending message to Client "
                    + socket.getInetAddress() + " : " + e.getMessage());
        }
    }

    private void closeResources() {
        try {
            objOut.close();
        } catch (Exception e) {
            /*ignore*/
        }
        try {
            objIn.close();
        } catch (Exception e) {
            /*ignore*/
        }
    }

}
