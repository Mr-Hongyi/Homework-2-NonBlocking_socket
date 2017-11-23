package nioserver.model;

import nioserver.startup.NioServer;

public class DataProcessing {
    /*
    This class is used for initializing the game.
    It will randomly pick up a word from String array base, get the attempt
    from the length of the word and replace every letter of the word with "_"
    */
    public static String GUESS_WORD;
    public static String SEND_UNDERLINE;
    
    public static synchronized void initial(){
        //Randomly picking up a word from the library -- WORD_BASE
        GUESS_WORD = NioServer.WORD_BASE[(int)(Math.random()*51527)];
        //The original word is picked up from the library contains "\n", substring to
        //drop it
        GUESS_WORD = GUESS_WORD.substring(0,GUESS_WORD.length()-1);
        //Replace the word with "_" and then be used for sending to client for 
        //guessing the word
        String[] underLineARR = new String[GUESS_WORD.length()];
        for(int i=0;i<GUESS_WORD.length();i++)
        {
           underLineARR[i]="_";
        }
        SEND_UNDERLINE = String.join(",", underLineARR);
        SEND_UNDERLINE = SEND_UNDERLINE.replace(",","");
        }
    }
    
    

