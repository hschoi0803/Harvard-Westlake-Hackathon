import java.util.*;
import java.nio.charset.*;
import java.io.*;
import java.nio.file.*;

public class PublicTester
{
    public static void main(String[] args)
    {
        if(args.length < 1){
          String fileName = "hitch.txt";
        }else{
          String fileName = args[0];
        }
        Test1 poet = new Test1(fileName);
        for(int i = 0 ; i < args.length ; i ++)
        {
            fileName = args[i];
            String text = "";
            try{
                 text= readFile(fileName,StandardCharsets.UTF_8);
            }
            catch(Exception e)
            {
                System.out.println("Couldn't read the file, " + fileName + " Exception: " + e);
            }
            poet.learn(text);
        }
        poet.createText(50);
    }

    static String readFile(String path, Charset encoding)
      throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
