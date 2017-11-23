package nioserver.model;

import nioserver.startup.NioServer;

public class DataBase {
    private static int USER_ATTEMPT;
    private static int USER_RECORD;
    private static String USER_UNDERLINE;     
    public static synchronized String dataProcess(String rcvCTX){
        //Extracting the client's ip address by method getCTX
        String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
        String sendCTX = null;
        //Extracting the payload which is encapsulated in "[]".
        String CTX = NioServer.getCTX(rcvCTX,"[","]");
        //Extracting client IP address that stores in USER_BASE
        for(int i=0;i<NioServer.USER_BASE.size();i++)
        {
            String temp = NioServer.USER_BASE.get(i);
            String baseIP = NioServer.getCTX(temp,"/",")");
            /*
            Compare the ip address from the received packet and the ip address in 
            USER_BASE. If they are the same, executing function dataCompare and get 
            the result of the client's guessing. Update the score and the result to 
            test.   
            */ 
            if(rcvIP.equals(baseIP))
            {
                String userRecord = NioServer.getCTX(temp,"!","?");
                int userRecord_n = Integer.parseInt(userRecord);
                String userAttempt = NioServer.getCTX(temp,"<",">");
                int userAttempt_n = Integer.parseInt(userAttempt);
                String userUnderline = NioServer.getCTX(temp,"[","]");
                String userWord = NioServer.getCTX(temp,"{","}");
                String resultCTX = dataCompare(userWord,userAttempt_n,userRecord_n,CTX,userUnderline);
                
                NioServer.USER_BASE.remove(i);
                String userData;
                userData = "/"+baseIP+")"+"<"+USER_ATTEMPT+">"+"!"+USER_RECORD+"?"+"["+USER_UNDERLINE+"]"+"{"+userWord+"}";
                NioServer.USER_BASE.add(userData);
                sendCTX = null;
                sendCTX ="/"+ baseIP + ")"+ "{on}" +"["+ resultCTX+"]";
                resultCTX = null;
                break;
            }
           
        }
        return sendCTX;
    }
   
    /*
    This method is used to compare and ensure whether the client guess the right
    word or not. 
    */
    static synchronized String dataCompare(String str,int attempt,int point,String input,String underline) {
                        String results = null;
                        boolean flag = true;
                        char[] arr = underline.toCharArray();
                        String reveal = str;

                        int n = str.length();
			String letter = input;

                        int inputNum = letter.length();
			boolean mFLG = false;
                        boolean nFLG = true;
                        //If the client has more than 0 times attempt left and 
                        //guess the single word, execute the rest of the function.
                        if (inputNum == 1 && attempt != 0){
                        //Compare the guessing with every letter in the word. If 
                        //it is right, set flag mFLG as true.    
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == letter.charAt(0)) {
					arr[i] = letter.charAt(0);
					mFLG = true;
            
				}
			}
                            String sUnderline = String.valueOf(arr);
                            USER_UNDERLINE = sUnderline;
                            // If flag mFLG is false, which means the guessing is wrong,
                            // the attempt will be reduced, and if the attempt is zero,
                            // the player has no chance to guess the word.
			if (mFLG == false) 
                            {
                                attempt--;
                                if (attempt != 0 )
                                {
                                
                                sUnderline = String.valueOf(arr);
                                results = "The"+" '"+letter +"' "+ "is not in the word. "+"You have " + attempt+ 
                                        " attempts left"+" "+sUnderline+" <"+n+" letters>";
                                
                                USER_UNDERLINE = sUnderline;
                                }
                                else
                                {
                                    point--;
                                    results = "Lose.."+" The word is:"+ reveal + " Your point is: "+
                                            point+" Continue？" + " YES or NO";
                                }
                            }	
                        else{
                                sUnderline = String.valueOf(arr);
                                results = "The"+" '"+letter +"' "+ "is in the word. "+"You have " + attempt+ 
                                        " attempts left"+" "+sUnderline+" <"+n+" letters"+">";
                                for (int j = 0; j < sUnderline.length(); j++) {
				if (sUnderline.charAt(j) == '_') {
					nFLG = false;
           
                                }
				}
                                if(nFLG){
                                    point ++;
                                    attempt = 0;
                                    results = "Win!"+" The word is:"+ reveal+" Your point is: "+
                                            point+" Continue?"+" YES or NO";
                                }
                            }
                        }
                        //If the content that client entered is not a single word, 
                        //compare the complete content with the entire word. If 
                        //it is the same, regard it as right and scoring.
                        else if(inputNum != 1 && attempt != 0){
                            
                           if (letter.length() != reveal.length())
                                {
                                    flag = false;
                                }
                                else{
                                    for (int i =0; i<reveal.length();i++)
                                    if (letter.charAt(i)!=reveal.charAt(i))
                                    {
                                    flag = false;
                                    break;
                                    }
                                    

                                } 
                           
                            if (flag)
                            {
                                point ++;
                                attempt = 0;
                                results = "Win!"+" The word is:"+ reveal+" Your point is: "+
                                              point+" Continue?" + " YES or NO" ;
           
                            }
                            else{
                                
                            attempt--;
                            
                            if (attempt != 0 ){
                                String sUnderline = String.valueOf(arr);
                                results = "The"+" '"+letter +"' " + "is not in the word. You have " + attempt + 
                                        " attempts left"+" "+sUnderline+" <"+n+" letters>";

                                USER_UNDERLINE = sUnderline; 
                                }
                            else
                            {
                                point--;
                                results = "Lose.."+" The word is: "+ reveal +" Your point is: "+
                                            point+" Continue?" + " YES or NO";
                            }
                            }
                        }
                        
                        USER_ATTEMPT = attempt;
                        USER_RECORD = point;
                
		return results;
	}
}
