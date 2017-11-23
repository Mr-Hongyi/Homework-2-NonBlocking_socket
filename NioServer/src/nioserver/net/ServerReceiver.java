/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nioserver.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import nioserver.controller.ReadMsg;
/**
 *
 * @author harry
 */
public class ServerReceiver {
    
    public static void serverReceive(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();   
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        buffer.clear();  
        int len = 0;  
        StringBuffer stringByte = new StringBuffer();  
        while ((len = sc.read(buffer)) > 0) {  
            buffer.flip();  //turn the buffer's status from storing data into ready for reading data
            stringByte.append(new String(buffer.array(), 0, len));  
        }  
        
        if(stringByte.length()>0)
        {
            ReadMsg.readWriteMsg(key,stringByte);  //recognize the flag or content
        }
    }
    
}
