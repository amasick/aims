package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Write_toLog {
    public static boolean write(String role,String user_id,String action){
        try {
            // Open given file in append mode by creating an
            // object of BufferedWriter class
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time= dtf.format(now);
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("src/main/resources/log.txt", true));
            String query=role + " " + user_id+ " " +action +" on "+ time +"\n";
            // Writing on output stream
            out.write(query);
            // Closing the connection
            out.close();
        }
        // Catch block to handle the exceptions
        catch (IOException e) {

            // Display message when exception occurs
            System.out.println("exception occurred" + e);
            return false;
        }
        return true;
    }
}
