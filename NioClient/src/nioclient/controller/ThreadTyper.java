
package nioclient.controller;

import java.io.*; 
import java.nio.ByteBuffer;  
import java.util.logging.*;  
import java.util.*;
import nioclient.net.ThreadNet;
import nioclient.net.ThreadSender;

public class ThreadTyper extends Thread implements Runnable{
    public static String THREAD_SEND;
    public ThreadTyper() {  
        
    }
 
@Override        
    public void run() {  
        
  Scanner scan = new Scanner(System.in);
  
   while (true) {  
       try { 
           String sendCTX = scan.next(); //get the content that the client enters
           sendCTX = ThreadNet.SOCKET_CHANNEL.getLocalAddress().toString()+")"+"["+sendCTX+"]"; //encapsulate the content and the client's IP address
           THREAD_SEND=sendCTX;
           ThreadSender serverSend = new ThreadSender();
           new Thread(serverSend).start();
       } catch (IOException ex) {  
           Logger.getLogger(ThreadTyper.class.getName()).log(  
                   Level.SEVERE, null, ex);  
       }  
   }  

} 
          
}
