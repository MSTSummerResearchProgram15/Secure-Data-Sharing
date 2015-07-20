
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {
    
    public boolean login(String usr, String pw) {

        BufferedReader br;
        Socket MyClient;
        int Portnumber;
        DataOutputStream output = null;
        DataInputStream input = null;
        boolean a = true; 
        int result;
  
        
        try {
            //Read the port number from a text file
            br = new BufferedReader(new FileReader("portnumber.txt"));
            String Port = br.readLine();
            br.close();

            //Convert string to int 
            Portnumber = Integer.parseInt(Port);
            System.out.println(Port);
            
            System.out.println("Position 1");
            //Open the socket
        
            MyClient = new Socket("localhost", Portnumber);
            

            output = new DataOutputStream(MyClient.getOutputStream());
            input = new DataInputStream(MyClient.getInputStream());

            
            //WRITE WHAT WE WANT TO SEND OR RECEIVE HERE
            System.out.println("Position 2");
     
            output.writeBytes(usr);
            output.writeBytes("\n");
            output.writeBytes(pw);
            
            result = input.read();
            System.out.println(result);
            if(result == 0)
            {
                a = true;
            }
            else 
            {
                a = false;
            }
            
            MyClient.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return a;
    }	
}
  