/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nioclient.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nioclient.net.ThreadNet.SOCKET_CHANNEL;
import nioclient.startup.NioClient;
import nioclient.view.cmdView;

public class ThreadReceiver {
     public static void ctxRcv(){

        int num = 0;  
        try {  
            //Call finishConnect() function to poll the connection status. Before
            //the connection is built up successfully, it will return false.
            //During waiting for building up connection, the program can execute
            //other tasks.
            while (!SOCKET_CHANNEL.finishConnect()) {  
            }  
        } catch (IOException ex) {  
            Logger.getLogger(NioClient.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
 
        while (true) {  
            try {  
                
                ByteBuffer buffer = ByteBuffer.allocate(1024); //Call allocate() function to build up a 1024 byte buffer 
                buffer.clear();  

                StringBuffer stringByte = new StringBuffer();  
             
                //While the buffer has data, the data will be exported into 
                //StringBuffer stringByte. Then stringByte will be transfer
                //into String rcvCTX and the content of rcvCTX will be extracted and 
                //separated into IP address, and the result of the game from the server.               
                while ((num = SOCKET_CHANNEL.read(buffer)) > 0) {  
                    stringByte.append(new String(buffer.array(), 0, num));  
                    buffer.clear();  
                }  
                if (stringByte.length()>0)
                {
                    
                   cmdView.userView(stringByte);
                } 
              
            } catch (IOException ex) {  
                Logger.getLogger(NioClient.class.getName()).log(  
                        Level.SEVERE, null, ex);  
            }  
        }  

    }  
         
     
}
