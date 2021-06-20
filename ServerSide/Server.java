package ServerSide;

import Model.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable {
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
            this.socketInput = socket.getInputStream();
            this.socketOutput = socket.getOutputStream();
            objIn = new ObjectInputStream(socketInput);
            objOut = new ObjectOutputStream(objOut);
        } catch (IOException e) {
            throw new AssertionError("Unable to establish connection : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        Message currentMessage = null;
        System.err.println("client : " + socket.getInetAddress() + " connected .");
        Label:
        while (true) {
            try {
                currentMessage = ((Message) objIn.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error case : " + e.getMessage() + " From Client " + socket.getInetAddress());
            }
            if (currentMessage != null) {
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
                    case "CloseMessage": {
                        break Label;
                    }
                }
            }
        }
        closeResources();
    }

    private Message signInCheck(SignInMessage signInMessage) {
        String uName = signInMessage.uName;
        String[] clientData = {signInMessage.uName, signInMessage.passWord,
                signInMessage.name, signInMessage.lastName, signInMessage.BirthDate};
        Optional<File> person = findPerson(uName);
        if (person.isPresent()) {
            return new ErrorMessage("User : " + uName + " already has an account .");
        }
        try (Formatter formatter = new Formatter("ServerSide/Persons" + "/" + uName + ".txt")) {
            for (String data :
                    clientData) {
                if (data != null) {
                    formatter.format(data);
                    formatter.flush();
                }
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
        File personDir = new File("ServerSide/Persons");
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
