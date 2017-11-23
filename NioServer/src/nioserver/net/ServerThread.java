package nioserver.net;

import java.io.*;  
import java.net.*;    
import java.nio.channels.*; 
import java.util.*; 
import java.util.logging.*;
import nioserver.startup.NioServer;
import nioserver.net.ServerReceiver;
import nioserver.controller.GetConnection;

public class ServerThread extends Thread implements Runnable{  
      
    public static Selector selector;
    SelectionKey ssKey;  

    public ServerThread() {  
  

    }  

    public void init() {  
        try {  
            selector = Selector.open();  //Using static factory method Selector.open() to create a new Selector object
            ServerSocketChannel ssChannel = ServerSocketChannel.open();  //Using static method ServerSocketChannel.open() to create a new ServerSocketChannel
            ssChannel.configureBlocking(false);  //make this connection nonblocking
            ssChannel.socket().bind(new InetSocketAddress(9999));  //binds the channel to a server socket on port 9999
            ssKey = ssChannel.register(selector, SelectionKey.OP_ACCEPT);  //register the channel with SelectionKey to check whether the server socket is ready for a new connection
            System.out.println("server is starting..." + new Date());  
        } catch (IOException ex) {  
            Logger.getLogger(NioServer.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    } 
     public void execute() {  
        try {  
            while (true) {  
                int num = selector.select();  //to check whether anything is ready to be acted on, call the selector's select() method 
                if (num > 0) {  
                    //if num is larger than 0, channel has data to be dealt with
                    Iterator<SelectionKey> it = selector.selectedKeys()  
                            .iterator();  
                    while (it.hasNext()) {  
                        //Remove key from set so that it don't have to be processed twice 
                        SelectionKey key = it.next();  
                        it.remove();  
                        //if the SelectionKey is invalid, the channel is closed or its selector is closed. So this key will be removed in the next cycle.
                        if (!key.isValid()) 
                            continue;  
                        //If the server channel is ready, the program accepts the new socket channel and adds it to the selector by calling function getConn(Key)
                        if (key.isAcceptable()) {   
                            GetConnection.getConn(key);  
                        } 
                        //If the server channel has data to be read, using function readMsg(key) to deal with it
                        else if (key.isReadable()|key.isWritable()) {  
                            ServerReceiver.serverReceive(key);  
                        }  
                        else 
                            break;  

                    }  

                }  
          
            }  

        } catch (IOException ex) {  
            Logger.getLogger(NioServer.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    }  

    
        
        

@Override    
    public void run() {  
        init();  
        execute();  
    }  
}
