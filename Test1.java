import java.util.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.nio.charset.*;
import java.io.IOException;
import java.nio.file.*;
public class Test1
{

    ArrayList<Hashtable<String,ArrayList<String>>> hashArry = new ArrayList<Hashtable<String,ArrayList<String>>>();
    ArrayList<String> startingWords = new ArrayList<String>();
    Hashtable<String,Integer> orderList = new Hashtable<String,Integer>();
    String[] textArr;
    //int stanzaLength = 0;
    int lineLength = 0;
    String story;

    public Test1(String file)
    {
        story = "";
        try{
             story= readFile(file,StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            System.out.println("Couldn't read the file, Exceptioin: " + e);
        }

        textArr = story.split("\\s+");
        this.initializeLineLength();
        this.initializeHashArry(story);
        this.initializeOrders();
        //System.out.println(orderList);
    }

    public void initializeLineLength()
    {
        int numOfSentences =1;
        for(int c = 0; c < textArr.length; c++)
        {
            if(textArr[c].indexOf(".") >=0)
            {
                numOfSentences++;
            }
        }
        lineLength = textArr.length/numOfSentences;
    }

    /*
    public void analyze(String file)
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
        String[] arr = text.split("\\s+");
        for(String s : arr)
        {
            if(s.indexOf("\\n") > -1)
                System.out.println(s);
        }
    }
    */
    /*
    public void analyze1(String file)
    {
        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
             while ((line = br.readLine()) != null) {
                // line += " $$$";

                String[] tempArr = line.split("\\s+");
                if(tempArr[0].length() > 1)
                    startingWords.add(tempArr[0]);
                this.addToMap(tempArr);


                }


        }
        catch (Exception ex){
             ex.printStackTrace();
            System.out.println("something really wrong happened.");
            return;
        }

    }
    */

    public String createText(int numOfWords)
    {
        String word = textArr[(int)(Math.random()*textArr.length)];
        //word = word.substring(0,1).toUpperCase() + word.substring(1,word.length()-1);

        String t = word;

        ArrayList<String> wordArr = new ArrayList<String>();

        //System.out.println(has);
        int x = 0;
        while (x < numOfWords || t.charAt(t.length()-1)!='.') {
            String[] tempArr= t.split("\\s+");
            String lastWord = tempArr[tempArr.length-1];
            //System.out.println("LASTWORD: " + lastWord);
            int order = this.getOrder(lastWord);
            //System.out.println("ORDER: " + order);

            String input = "";
            if(order >= tempArr.length)
            {
                order = tempArr.length;
            }
            if(order > 10)
            {
                System.out.println(lastWord);
                System.out.println(order);
            }
            input = tempArr[tempArr.length-order];
            for(int c = order - 1; c > 0; c--)
            {
                input += " " + tempArr[tempArr.length-c];
            }

            //System.out.println("T:" + t);
            //System.out.println(input);
            t += " " +this.getNextWord(input);
            x++;
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
            //t.replaceAll("$$$","\n");
            if(lineLength >50)
            {
                lineLength = 10;
            }
            String finalText = this.betterMeter(t,10);
            //System.out.println("Before: " + finalText);
            finalText = finalText.replace("\"","");
            finalText = finalText.substring(0,1).toUpperCase() + finalText.substring(1,finalText.length()-1);
            out.write(finalText);  //Replace with the string
                                                     //you are trying to write
            out.close();
            System.out.println(finalText);
            return finalText;
            //System.out.println(t);
        }
        catch (IOException e)
        {
            System.out.println("Exception ");

        }
        return "";
    }

    public int getOrder(String s)
    {
        if(orderList.containsKey(s))
        {
            //System.out.println("It is in the orderList!!");
            if(orderList.get(s) > 10)
            {
                System.out.println("String: " + s + "Order: " + orderList.get(s + " "));
            }
            return orderList.get(s);
            //return 1;
        }
        //System.out.println("There is no " +  s + " in orderList");
        return 1;
    }

    public String getNextWord(String input)
    {
        String s = "";
        int order = input.split("\\s+").length;
        Hashtable<String,ArrayList<String>> has = this.hashArry.get(order-1);
        ArrayList<String> wordArr;
        int firstIndex = 0;
        for(int c = order; c >0; c--)
        {
            has = this.hashArry.get(c-1);
            if(has.containsKey(input))
            {
                wordArr = has.get(input);
                return wordArr.get((int)(Math.random()*wordArr.size()));
            }
            input = input.substring(input.indexOf(" ") + 1,input.length());
        }
        System.out.println("Have to choose a random word");
        return textArr[(int)(Math.random()*textArr.length)];
    }

    /*
    public void addToMap(String[] arr)
    {
        String[] textList = arr;  //Initializing variables

        //Hashtable<String,ArrayList<String>> has = new Hashtable<String,ArrayList<String>>();

        String key = "";

        for(int c = 0; c<textList.length-order; c++) //This for-loop puts together the Hashtable
        {
            for (int x = 0 ; x < order ; x++) {
                key += textList[c+x] + " ";
            }

            //System.out.println(key);
            if (has.containsKey(key))
            {
                has.get(key).add(textList[c+order]);
            }
            else
            {
                ArrayList<String> a = new ArrayList<String>();
                a.add(textList[c+order]);
                has.put(key,a);
            }
            key = "";
        }
    }
    */

    public Hashtable<String,ArrayList<String>> createMap(String s, int order)
    {
        String[] textList = s.split("\\s+");  //Initializing variables

        Hashtable<String,ArrayList<String>> has = new Hashtable<String,ArrayList<String>>();

        String key = "";

        for(int c = 0; c<textList.length-order; c++) //This for-loop puts together the HashMap
        {
            key = textList[c];
            for (int x = 1 ; x < order ; x++) {
                key += " " + textList[c+x];
            }

            //System.out.println(key);
            if (has.containsKey(key))
            {
                has.get(key).add(textList[c+order]);
            }
            else
            {
                ArrayList<String> a = new ArrayList<String>();
                a.add(textList[c+order]);
                has.put(key,a);
            }
            key = "";
        }
        return has;
    }

    public void initializeOrders()
    {
        Hashtable<String,ArrayList<String>> has = hashArry.get(0);
        //System.out.println(has);
        int xMax = determineMax(has);
        int xMin = determineMin(has);
        for (Enumeration<String> e = has.keys(); e.hasMoreElements();)
        {
            String key = e.nextElement();
            //System.out.println(key);
            int length = has.get(key).size();

            int order = (int)((((double)(length - xMin)/(double)(xMax - xMin))*5));
            if(order <= 0)
            {
                order = 1;
            }
            //System.out.println("Length: " + length + " xMin: " + xMin + " xMax: " + xMax + " order: " + order);
            orderList.put(key,order);
        }
        for (Enumeration<String> e = orderList.keys(); e.hasMoreElements();)
        {
            String key = e.nextElement();
            if(orderList.get(key) > 10)
            {
                System.out.println("KEY: " + key + " ORDER: " + orderList.get(key));
            }

        }
    }

    public int determineMax(Hashtable<String,ArrayList<String>> has)
    {
        int max = 0;
        for (Enumeration<String> e = has.keys(); e.hasMoreElements();)
        {
            String key = e.nextElement();
            if(max < has.get(key).size())
            {
                max = has.get(key).size();
            }

        }
        return max;
    }

    public int determineMin(Hashtable<String,ArrayList<String>> has)
    {
        int min = Integer.MAX_VALUE;
        for (Enumeration<String> e = has.keys(); e.hasMoreElements();)
        {
            String key = e.nextElement();
            if(min > has.get(key).size())
            {
                min = has.get(key).size();
            }

        }
        return min;
    }

    public void initializeHashArry(String s)
    {
        for(int c = 0; c < 10; c++)
        {
            hashArry.add(createMap(s,c+1));
        }
    }

    public String freeform(String t) {

        String[] tList = t.split("\\s+");
        t = "";
        for ( String r : tList ) {
            if (r.substring(r.length()-1).equals(".")) {
                r += " \n";
                t += r + " ";
            }
            else if (r.substring(r.length()-1).equals(",")) {
                r += " \n";
                t += r + " ";
            }
            else {
                t += r + " ";
            }
        }
        return t;
    }

     public String betterMeter(String t, int increment) {
        String[] tList = t.split("\\s+");
        t = "";
        int maxProbabilityCap = 0;

        int random = (int)(Math.random()*100.0);

        for ( String r : tList ) {
            if ( maxProbabilityCap <= random ) {
                t += r + " ";
                maxProbabilityCap += increment;

            }
            else {
                t += r +  "\n ";
                maxProbabilityCap = 0;
            }
        }
        return t;
    }

        static String readFile(String path, Charset encoding)
      throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void addToMap(String s)
    {
        String[] textList = s.split("\\s+");
        for(int o = 1; o<11; o++)
        {
            Hashtable<String,ArrayList<String>> has = hashArry.get(o-1);
            String key ="";
            for(int c = 0; c<textList.length-o; c++) //This for-loop puts together the HashMap
            {
                key = textList[c];
                for (int x = 1 ; x < o ; x++) {
                    key += " " + textList[c+x];
                }

                //System.out.println(key);
                if (has.containsKey(key))
                {
                    has.get(key).add(textList[c+o]);
                }
                else
                {
                    ArrayList<String> a = new ArrayList<String>();
                    a.add(textList[c+o]);
                    has.put(key,a);
                }
                key = "";
            }
        }
    }

    public  void printHash()
    {
        for(Hashtable<String,ArrayList<String>> h : hashArry)
        {
            System.out.println(h);
        }
    }

    public void learn(String newLearning)
    {
        this.addToMap(newLearning);
        this.initializeOrders();
    }
}
