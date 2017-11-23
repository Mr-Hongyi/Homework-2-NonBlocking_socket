
package nioserver.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import nioserver.model.DataProcessing;
import nioserver.startup.NioServer;
import nioserver.net.ServerThread;

public class GetConnection {
    
    public static void getConn(SelectionKey key) throws IOException {  
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();  
        SocketChannel sc = ssChannel.accept();  
        sc.configureBlocking(false);  //Set the SocketChannel to be nonblocking 
        sc.register(ServerThread.selector, SelectionKey.OP_READ);  
        System.out.println("build connection :" 
                + sc.socket().getRemoteSocketAddress());
        DataProcessing.initial(); //Call DataProcessing.initial() to initialize the game
        int wordLength = DataProcessing.GUESS_WORD.length();
        System.out.println("Guessing word:"+DataProcessing.GUESS_WORD+" "+wordLength+" letters");
        //Generate the welcome message that will be sent to the client
        String sendCTX = "("+sc.socket().getRemoteSocketAddress().toString()+")"+"{on}"+"["+"Game starts!"+
                ":"+DataProcessing.SEND_UNDERLINE+" <"+
                wordLength+" letters>"+" You can quit the game just type \"QUIT\" Please guess a letter: ]";
        System.out.println(sc.socket().getRemoteSocketAddress().toString());
        //Storing the user data into USER_BASE
        String userIP = sc.socket().getRemoteSocketAddress().toString()+")";
        userIP = NioServer.getCTX(userIP,"/",")");
        int userAttempt = wordLength;
        String userData = "/"+userIP+")"+"<"+userAttempt+">"+"!0?"+"["+
                DataProcessing.SEND_UNDERLINE+"]"+"{"+DataProcessing.GUESS_WORD+"}";
        NioServer.USER_BASE.add(userData);
        System.out.println(NioServer.USER_BASE);
        sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
               
        }
}
