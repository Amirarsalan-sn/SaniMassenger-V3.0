package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String path = "C:\\Users\\Arsalan\\IdeaProjects\\SaniMassengerV3\\src\\Model\\MainInit.txt";
        try(Scanner scanner = new Scanner(new File(path))) {
            System.out.println(scanner.nextLine());
            System.out.println(scanner.nextLine());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
