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


public class ServerSender {
    public static void serverSender(SelectionKey key, String sendCTX) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();  
        sc.write(ByteBuffer.wrap(sendCTX.getBytes()));  //server send message
        
    }
}
