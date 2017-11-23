
package nioclient.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nioclient.net.ThreadNet.SOCKET_CHANNEL;
import nioclient.startup.NioClient;

public class cmdView {
    
    public static void userView(StringBuffer stringByte) {  
        try{
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
                 
              
            } catch (IOException ex) {  
                Logger.getLogger(NioClient.class.getName()).log(  
                        Level.SEVERE, null, ex);  
            }  
        }  

}

      

