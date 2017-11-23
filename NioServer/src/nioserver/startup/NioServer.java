package nioserver.startup;
import java.util.*;
import java.io.*;
import nioserver.net.ServerThread;

public class NioServer {  
    public static String[] WORD_BASE;
    public static ArrayList<String> USER_BASE = new ArrayList<String>();

    public static void main(String[] args) throws Exception{  
        /*
        Extracting the txt file "word.txt", and importing it into an string array 
        "WORD_BASE" to store all the words as a library.
        */
        File file = new File("/Users/harry/Desktop/KTH Course/Period 2/ID1212/Task 1/words.txt");
        FileReader reader = new FileReader(file);
        int fileLen = (int)file.length();
        char[] chars = new char[fileLen];
        reader.read(chars);
        String txt = String.valueOf(chars);
        WORD_BASE = txt.split("\n"); 
        
        //instantiate ServerThread, and based on this instantation to create a Thread object
        //call Thread object method start() to start the server thread
        ServerThread server = new ServerThread();  
        new Thread(server).start();  

    }  
 public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
} 
