package Model;

import ServerSide.Person;

import java.io.*;
import java.util.*;


public class test {
    public static void main(String[] args) {
        List<Post> posts = new ArrayList<>();
        Collections.addAll(posts , new Post("1" , "1" , "1"), new Post("2" , "2" , "2") ,new Post("3" , "3" , "3") , new Post("4" , "4" , "4"));
        List<Post> posts1 = posts.subList(posts.size()-2 , posts.size());
        /*try(FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Arsalan\\IdeaProjects\\SaniMassengerV3\\src\\Model\\sampleMap.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            Person person = new Person("ali" , "1234" , "alavi" , "2001/2/2");
            objectOutputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileInputStream inputStream = new FileInputStream("C:\\Users\\Arsalan\\IdeaProjects\\SaniMassengerV3\\src\\Model\\sampleMap.txt") ;
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
           Person person = ((Person) objectInputStream.readObject());
            System.out.println(person.passWord);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }*/

    }
}
