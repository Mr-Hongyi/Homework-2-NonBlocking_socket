
package nioclient.controller;

import java.io.*; 
import java.nio.ByteBuffer;  
import java.util.logging.*;  
import java.util.*;
import nioclient.net.ThreadReader;

public class ThreadWriter extends Thread implements Runnable{
  
    public ThreadWriter() {  
        
    }
 
@Override        
    public void run() {  
        
  Scanner scan = new Scanner(System.in);
  
   while (true) {  
       try {
           
           String sendCTX = scan.next(); //get the content that the client enters
           sendCTX = ThreadReader.SOCKET_CHANNEL.getLocalAddress().toString()+")"+"["+sendCTX+"]"; //encapsulate the content and the client's IP address
           ThreadReader.SOCKET_CHANNEL.write(ByteBuffer.wrap(sendCTX.getBytes()));  //send the datagram sendCTX to the server
       } catch (IOException ex) {  
           Logger.getLogger(ThreadWriter.class.getName()).log(  
                   Level.SEVERE, null, ex);  
       }  
   }  

} 
          
}
