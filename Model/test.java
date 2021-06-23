package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class test {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String time = localDate + " "  + localTime ;
        time =  time.replaceAll("-" , "/");
        time =  time.replaceAll(":\\d\\d\\.\\d+" , "");
        System.out.println(time);
    }
}
