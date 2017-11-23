
package nioclient.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import nioclient.controller.ThreadTyper;

public class ThreadSender extends Thread implements Runnable {
    
    public ThreadSender() {  
        
    } 
    
    @Override
    public void run() {  
        try{
            
        ThreadNet.SOCKET_CHANNEL.write(ByteBuffer.wrap(ThreadTyper.THREAD_SEND.getBytes()));  //send the datagram sendCTX to the server
        
        }catch (IOException ex) {  
           Logger.getLogger(ThreadTyper.class.getName()).log(  
                   Level.SEVERE, null, ex);
        }
    }
    
    
}
