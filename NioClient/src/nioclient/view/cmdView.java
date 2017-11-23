
package nioclient.view;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nioclient.net.ThreadReader.SOCKET_CHANNEL;
import nioclient.startup.NioClient;

public class cmdView {
    
    public static void userView() {  

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
                    String rcvCTX = stringByte.toString();
                    String rcvIP = NioClient.getCTX(rcvCTX,"/",")");
                    String localIP =SOCKET_CHANNEL.getLocalAddress().toString()+")";
                    localIP = NioClient.getCTX(localIP,"/",")");
                    //if the local IP address is the same as the datagram received
                    //from the server, then extract the endlabel. If the endlabel is
                    //"end", the socket will be closed and exit the program. If the
                    //endLabel is not found, then the message will be displayed to 
                    //the player.
                    if(rcvIP.equals(localIP))
                    {
                        String endLabel = NioClient.getCTX(rcvCTX,"{","}");

                        if(endLabel.equals("end")){
                            String ctx = NioClient.getCTX(rcvCTX,"[","]");
                            System.out.println(ctx);
                            SOCKET_CHANNEL.close();  
                            SOCKET_CHANNEL.socket().close();  
                            System.exit(0);
                        }
                        else{
                            String ctx = NioClient.getCTX(rcvCTX,"[","]");
                            System.out.println(ctx);
                        }
                    } 
                } 
              
            } catch (IOException ex) {  
                Logger.getLogger(NioClient.class.getName()).log(  
                        Level.SEVERE, null, ex);  
            }  
        }  

    }  
}
