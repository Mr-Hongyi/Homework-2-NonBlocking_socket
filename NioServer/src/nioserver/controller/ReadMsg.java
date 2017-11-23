
package nioserver.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import nioserver.model.DataBase;
import nioserver.model.DataProcessing;
import nioserver.startup.NioServer;
import nioserver.net.ServerSender;
        
public class ReadMsg {
    
        public static void readWriteMsg(SelectionKey key, StringBuffer stringByte) throws IOException {  
 
        SocketChannel sc = (SocketChannel) key.channel();   
        
        String rcvCTX = stringByte.toString();
        String sendCTX = null;
                
                System.out.println("server recevied:"+ rcvCTX);
                String continueFLG = NioServer.getCTX(rcvCTX,"[","]");
                
                switch (continueFLG) {
                    case "YES":
                        //If the client wants to continue playing the game (the content of 
                        //the message is "YES"), the server will initialize the game again
                        //and start a new round.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    DataProcessing.initial();
                                    int wordLength=DataProcessing.GUESS_WORD.length();
                                    int userAttempt = wordLength;
                                    String userWord=DataProcessing.GUESS_WORD;
                                    String userUnderline = DataProcessing.SEND_UNDERLINE;
                                    NioServer.USER_BASE.remove(i);
                                    String userData;
                                    userData = "/"+baseIP+")"+"<"+userAttempt+">"+"!"+userRecord+
                                            "?"+"["+userUnderline+"]"+"{"+userWord+"}";
                                    NioServer.USER_BASE.add(userData);
                                    System.out.println(NioServer.USER_BASE);
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{on}"+"["+"New round: Please guess a letter"+
                                            userUnderline+" <"+wordLength+" letters>"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    ServerSender.serverSender(key,sendCTX);
                                    break;
                                }
                                
                            }
                            break;
                        }
                    case "NO":
                        //If the client wants to exit the game (the content of the message 
                        //is "NO", the server will send a goodbye message which contains 
                        //the score to the client and delete all the user data.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    NioServer.USER_BASE.remove(i);
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                            "!"+" You have "+userRecord+" points!"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    ServerSender.serverSender(key,sendCTX);
                                    key.cancel();   //Shut down the connection
                                    sc.close();  
                                    sc.socket().close(); 
                                    break;
                                }
                                
                            }       
                            break;
                        }
                    case "QUIT":
                        //If the client wants to temporarily exit the game (the content of the message 
                        //is "QUIT", the server will send a goodbye message which contains 
                        //the score to the client and delete the user data.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    NioServer.USER_BASE.remove(i);
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                            "!"+" You have "+userRecord+" points!"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    ServerSender.serverSender(key,sendCTX);
                                    key.cancel();   //close
                                    sc.close();  
                                    sc.socket().close(); 
                                    break;
                                }
                                
                            }       
                            break;
                        }
                    default:
                        sendCTX = DataBase.dataProcess(rcvCTX);
                        System.out.println("server sent:"+ sendCTX);
                ServerSender.serverSender(key,sendCTX);
                        break;
                }
        }   
                
   

    }  

