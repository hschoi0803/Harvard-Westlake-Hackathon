import java.util.*;
import java.lang.Math.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.*;
import java.io.IOException;
import java.nio.file.*;

class Markov {
	public void writeBasedOnText(String base) {
		//Scanner reader = new Scanner(System.in);
			
		int numOfWords = 100;
		
		ArrayList<String> wordArr = new ArrayList<String>();
		String t = "";

		//System.out.println("Input text: ");
		String text = base;
		
		String[] textList = text.split("\\s+");
			
		HashMap<String,ArrayList<String>> has = new HashMap<String,ArrayList<String>>();
			
		int index = 0;
			
		for(int c = 0; c<textList.length-1; c++)
		{	
			if (has.containsKey(textList[c]))
			{
				has.get(textList[c]).add(textList[c+1]);
			}
			else
			{
				ArrayList<String> a = new ArrayList<String>();
				a.add(textList[c+1]);
				has.put(textList[c],a);
			}
		}
		
		String word = textList[(int)(Math.random()*textList.length)];
		word = word.substring(0,1).toUpperCase() + word.substring(1,word.length()-1);
		
		t = word;
		
		//System.out.println(has);	
		
		for (int x = 0 ; x < numOfWords ; x++) {
			if (has.containsKey(word))
			{
				 wordArr = has.get(word);
			 }
			 else
			 {
			 	word = textList[(int)(Math.random()*textList.length)];
			 	wordArr = has.get(word);
			 }
			 //System.out.println(word);
			 word = wordArr.get((int)(Math.random()*wordArr.size()));
			 t += " " + word;
				
		}
		try {
            BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
            out.write(t);  //Replace with the string 
                                                     //you are trying to write  
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");
        
        }
	}
	
	public void learnFromText(String file)
    {
        String text;
        try{
            text = readFile(file,StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("something really wrong happened.");
            return;
        }
        this.writeBasedOnText(text);
        //learn(text);
    }
    
        static String readFile(String path, Charset encoding) 
      throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}