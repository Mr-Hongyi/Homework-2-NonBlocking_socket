package nioclient.net;

import java.io.*; 
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.*; 
import java.util.logging.*; 
import nioclient.startup.NioClient;
import nioclient.view.cmdView;

public class ThreadReader extends Thread implements Runnable{
    
    Selector selector;   

    public static SocketChannel SOCKET_CHANNEL;
    
    public ThreadReader() {    

    }  
    public void init() {  
        try { 
            //Building connection with the server running on port 9999 
            SOCKET_CHANNEL = SocketChannel.open();  //Using static method SocketChannel.open() to create a new SocketChannel
            SOCKET_CHANNEL.configureBlocking(false); //make this connection nonblocking 
            SOCKET_CHANNEL.connect(new InetSocketAddress(NioClient.SERVER_ADDRESS, 9999));  //connect with the server running on port 9999 

        } catch (IOException ex) {  
            Logger.getLogger(NioClient.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    }  
    

    @Override
    public void run() {  
        
        init();  
        cmdView.userView();  
    }  
    
}
