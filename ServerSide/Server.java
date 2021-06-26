package ServerSide;

import Model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Server implements Runnable {
    public static final String SERVER_SIDE_PERSONS = "/home/amir/IdeaProjects/untitled2/src/ServerSide/Persons";
    private Socket socket;
    private InputStream socketInput;
    private OutputStream socketOutput;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private Person currentPerson ;
    private ArrayList<Person> followers = new ArrayList<>();
    private ArrayList<Person> following = new ArrayList<>();

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
            case "PostRefMessage" : {
                Post[] posts = getOtherPosts(currentPerson);
                send(new PostRefMessage(posts));
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

    private Post[] getOtherPosts(Person currentPerson) {
        ArrayList<Post> posts = new ArrayList<>();
        for (Person person :
                following) {
            posts.addAll(person.getTwoLastPosts());
        }
        return posts.toArray(new Post[0]);
    }

    private Message signInCheck(SignInMessage signInMessage) {
        String uName = signInMessage.uName;
        Person person = Listener.unameToPass.get(uName);
        if(person != null) {
            return new ErrorMessage("User : " + uName + " already has an account .");
        }
        person = new Person(uName , signInMessage.passWord,signInMessage.name + " " + signInMessage.lastName,
                signInMessage.BirthDate);
        Listener.unameToPass.put(uName , person);
        this.currentPerson = person;
        return new ConfirmMessage("""
                You have signed in successfully .
                Welcome to SaniMessenger V3.0 .
                """);
    }

    private Message uPassCheck(LoginMessage loginMessage) {
        Person person = Listener.unameToPass.get(loginMessage.Uname);
        if(person == null || !person.passWord.equals(loginMessage.Pass)) {
            return new BooleanMessage(false);
        }
        this.currentPerson = person;
        loadList(person);
        return new BooleanMessage(true);
    }

    private void loadList(Person person) {
        loadFollowers(person) ;
        loadFollowings(person);
    }

    private void loadFollowings(Person person) {
        for (String uname :
                person.getFollowingNames()) {
            Person followingPerson = Listener.unameToPass.get(uname);
            if(followingPerson != null)
                following.add(followingPerson);
            else
                person.removeFollowingNames(uname);
        }
    }

    private void loadFollowers(Person person) {
        for (String uname :
                person.getFollowerNames()) {
            Person followerPerson = Listener.unameToPass.get(uname);
            if(followerPerson != null)
                followers.add(followerPerson);
            else
                person.removeFollowerNames(uname);
        }
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
