package ServerSide;

import Model.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Server implements Runnable {
    private Socket socket;
    private InputStream socketInput;
    private OutputStream socketOutput;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private Person currentPerson;
    private ArrayList<Person> followers = new ArrayList<>();
    private ArrayList<Person> following = new ArrayList<>();

    /**
     * if one of the getInputStream or getOutputStream operations failed ,
     * server is not responsible and should end it's operation soon .
     * this field is for checking this kind of situation .
     */
    private boolean errorFlag = true;

    public Server(Socket socket) {
        this.socket = socket;
        try {
            this.socketOutput = socket.getOutputStream();
            this.socketInput = socket.getInputStream();
            objIn = new ObjectInputStream(socketInput);
            objOut = new ObjectOutputStream(socketOutput);
        } catch (IOException e) {
            /*throw new AssertionError("Unable to establish connection : " + e.getMessage());*/
            errorFlag = false;
        }
    }

    @Override
    public void run() {
        Message currentMessage = null;
        System.err.println("[" + socket.getInetAddress() + "] [connected]\ntime: " + LocalDateTime.now());
        while (errorFlag) {
            try {
                currentMessage = ((Message) objIn.readObject());
            } catch (IOException | ClassNotFoundException e) {
                /*throw new AssertionError("Couldn't send message to client " + socket.getInetAddress()
                        + " " + e.getMessage());*/
                errorFlag=false;
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
            case "PostRefMessage": {
                Post[] posts = getOtherPosts(currentPerson);
                send(new PostRefMessage(posts));
                System.err.println("["+currentPerson.uname+"] get posts list.\ntime: "+ LocalDateTime.now());
                break;
            }
            case "ReConnectMessage": {
                String uName = ((ReConnectMessage) currentMessage).uName;
                this.currentPerson = Listener.unameToPass.get(uName);
                loadList(currentPerson);
                System.err.println("[" + currentPerson.uname + "] reconnected\ntime: " + LocalDateTime.now());
                break;
            }
            case "DeleteAccMessage": {
                Listener.unameToPass.remove(currentPerson.uname);
                System.err.println("["+currentPerson.uname+"] delete account.\ntime: "+LocalDateTime.now());
                return true;
            }
            case "ChangeInfoMessage": {
                String newName = ((ChangeInfoMessage) currentMessage).name;
                String newDate = ((ChangeInfoMessage) currentMessage).birthDate;
                if (!newName.equals(""))
                    currentPerson.name = newName;
                if (!newDate.equals(""))
                    currentPerson.birthDate = newDate;
                System.err.println("["+currentPerson.uname+"] update info.\ntime: " + LocalDateTime.now());
                break;
            }
            case "ProfileMessage": {
                ProfileMessage profileMessage = ((ProfileMessage) currentMessage);
                Person profile = Listener.unameToPass.get(profileMessage.uname);
                send(new ProfileMessage(null, profile));
                if (profile == null) {
                    delete(profileMessage.uname);
                    System.err.println("[" + currentPerson.uname + "] get info ["+profileMessage.uname+"]\nmessage: ["+profileMessage.uname+"] does not exist.\ntime: "+ LocalDateTime.now());
                } else {
                    System.err.println("[" + currentPerson.uname + "] get info ["+profile.uname+"]\nmessage: ["+profile.uname+"]\ntime: "+ LocalDateTime.now());
                }
                break;
            }
            case "FollowMessage": {
                FollowMessage followMessage = ((FollowMessage) currentMessage);
                Person followed = Listener.unameToPass.get(followMessage.uName);
                if (followed != null) {
                    System.err.println("[" + currentPerson.uname + "] [follow]\nmessage: [" + followed.uname + "]\ntime: " + LocalDateTime.now());
                    currentPerson.addFollowingNames(followMessage.uName);
                    followed.addFollowerNames(currentPerson.uname);
                }
                System.err.println("[" + currentPerson.uname + "] [follow]\nmessage: [" + followMessage.uName + "] does not exist.\ntime: " + LocalDateTime.now());
                break;
            }
            case "UnfollowMessage": {
                UnfollowMessage unfollowMessage = ((UnfollowMessage) currentMessage);
                Person unfollowed = Listener.unameToPass.get(unfollowMessage.uName);
                if (unfollowed != null) {
                    System.err.println("[" + currentPerson.uname + "] [unfollow]\nmessage: [" + unfollowed.uname + "]\ntime: " + LocalDateTime.now());
                    currentPerson.removeFollowingNames(unfollowed.uname);
                    unfollowed.removeFollowingNames(currentPerson.uname);
                } else {
                    delete(unfollowMessage.uName);
                    System.err.println("[" + currentPerson.uname + "] [unfollow]\nmessage: [" + unfollowMessage.uName + "] does not exist.\ntime: " + LocalDateTime.now());
                }
                break;
            }
            case "SendCommentMessage": {
                SendCommentMessage commentMessage = ((SendCommentMessage) currentMessage);
                Person commented = Listener.unameToPass.get(commentMessage.author);
                if (commented != null) {
                    Post post = commented.getPost(commentMessage.localDateTime);
                    post.addComment(commentMessage.comment);
                    System.err.println("["+currentPerson.uname+"] comment\nmessage: ["+post.title+"]\ntime: "+ LocalDateTime.now());
                } else {
                    delete(commentMessage.author);
                    System.err.println("["+currentPerson.uname+"] comment\nmessage: ["+commentMessage.author+"] does not exist.\ntime: "+ LocalDateTime.now());

                }
                break;
            }
            case "LikeMessage": {
                LikeMessage like = ((LikeMessage) currentMessage);
                Person liked = Listener.unameToPass.get(like.author);
                if (liked != null) {
                    Post likedPost = liked.getPost(like.localDateTime);
                    likedPost.like(like.liker);
                    System.err.println("["+currentPerson.uname+"] [like]\nmessage: ["+likedPost.author+"] ["+likedPost.title+"]\ntime: "+LocalDateTime.now());
                } else {
                    delete(like.author);
                    System.err.println("["+currentPerson.uname+"] [like]\nmessage: ["+like.author+"] does not exist.\ntime: "+LocalDateTime.now());
                }
                break;
            }
            case "RepostMessage": {
                RepostMessage repost = ((RepostMessage) currentMessage);
                Person reposted = Listener.unameToPass.get(repost.author);
                if (reposted != null) {
                    Post repostedPost = reposted.getPost(repost.localDateTime);
                    currentPerson.addPost(new Post(repostedPost.author, repostedPost.title, repostedPost.context));
                    System.err.println("[" + currentPerson.uname + "] [repost]\nmessage: [" + repostedPost.author + "] [" + repostedPost.title + "]\ntime: " + LocalDateTime.now());
                } else {
                    delete(repost.author);
                    System.err.println("[" + currentPerson.uname + "] [repost]\nmessage: [" + repost.author + "] does not exist.\ntime: " + LocalDateTime.now());
                }
                break;
            }
            case "NewPostMessage": {
                NewPostMessage newPostMess = ((NewPostMessage) currentMessage);
                Post newPost = newPostMess.post;
                currentPerson.addPost(newPost);
                System.err.println("[" + currentPerson.uname + "] publish\nmessage: [" + newPost.title + "] [" + newPost.author + "]\ntime: " + LocalDateTime.now());
                break;
            }
            case "LogOutMessage": {
                System.err.println("["+currentPerson.uname+"] logout\ntime: "+ LocalDateTime.now());
                return true;
            }
            case "CheckMessage": {
                send(new CheckMessage());
            }
        }
        return false;
    }

    private void delete(String uName) {
        currentPerson.removeFollowerNames(uName);
        currentPerson.removeFollowingNames(uName);
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
        if (person != null) {
            return new ErrorMessage("User : " + uName + " already has an account .");
        }
        person = new Person(uName, signInMessage.passWord, signInMessage.name + " " + signInMessage.lastName,
                signInMessage.BirthDate);
        Listener.unameToPass.put(uName, person);
        this.currentPerson = person;
        System.err.println("[" + currentPerson.uname + "] [register]\ntime: " + LocalDateTime.now());
        return new ConfirmMessage("""
                You have signed in successfully .
                Welcome to SaniMessenger V3.0 .
                """);
    }

    private Message uPassCheck(LoginMessage loginMessage) {
        Person person = Listener.unameToPass.get(loginMessage.Uname);
        if (person == null || !person.passWord.equals(loginMessage.Pass)) {
            return new BooleanMessage(false);
        }
        this.currentPerson = person;
        loadList(person);
        System.err.println("[" + currentPerson.name + "] [login]\ntime: " + LocalDateTime.now());
        return new BooleanMessage(true);
    }

    private void loadList(Person person) {
        loadFollowers(person);
        loadFollowings(person);
    }

    private void loadFollowings(Person person) {
        for (String uname :
                person.getFollowingNames()) {
            Person followingPerson = Listener.unameToPass.get(uname);
            if (followingPerson != null)
                following.add(followingPerson);
            else
                person.removeFollowingNames(uname);
        }
    }

    private void loadFollowers(Person person) {
        for (String uname :
                person.getFollowerNames()) {
            Person followerPerson = Listener.unameToPass.get(uname);
            if (followerPerson != null)
                followers.add(followerPerson);
            else
                person.removeFollowerNames(uname);
        }
    }


    private void send(Message message) {
        try {
            objOut.writeObject(message);
        } catch (IOException e) {
            /*throw new AssertionError("there is a problem sending message to Client "
                    + socket.getInetAddress() + " : " + e.getMessage());*/
            errorFlag = false;
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
        Listener.liveServers.addAndGet(-1);
        Listener.writeMap();
    }

}
