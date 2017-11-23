package nioclient.startup;

import java.util.*;
import nioclient.controller.ThreadTyper;
import nioclient.net.ThreadNet;

public class NioClient {  
    
    public static String SERVER_ADDRESS;
    
    public static void main(String[] args) {  
        //Since the ip address of the server varies when accessing to different network. 
        //Ask for the client to input the ip address to connect to the specific server. 
        System.out.println("Welcome to HangMan Game! Please enter the server address:");
        Scanner scan = new Scanner(System.in);
        SERVER_ADDRESS = scan.next();
        ThreadNet read = new ThreadNet();
        ThreadTyper write = new ThreadTyper();
        new Thread(read).start();
        new Thread(write).start();
    }  
    
    public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
} 
