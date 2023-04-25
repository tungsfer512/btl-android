package fourteam.fantastic.btl.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadWriteToken {

    public static String readToken() {
        File myObj = new File("token.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String data = myReader.nextLine();
        myReader.close();
        return data;
    }

    public static void writeToken(String token){
        try {
            File myObj = new File("token.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            new FileWriter("token.txt").close();
            FileWriter myWriter = new FileWriter("token.txt");
            myWriter.write(token);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully wrote to the file.");
    }

}
