import java.util.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.nio.charset.*;
import java.io.IOException;
import java.nio.file.*;
public class Generate
{
    public static void main(String[] args)
    {
        
        String text = "";
        Test1 a = new Test1("twocity.txt");
        for (int c = 0; c < 10; c++)
        {
            text += a.createText(50) + "\n\n";
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("result.txt"));
            out.write(text);  //Replace with the string 
                                                     //you are trying to write  
            out.close();
            //System.out.println(text);
            //System.out.println(t);
        }
        catch (IOException e)
        {
            System.out.println("Exception ");
        
        }
        
    }
}