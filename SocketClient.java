
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {

    public void run() {

        BufferedReader br;
        Socket MyClient;

        //Create an input stream
        DataInputStream input = null;
        //Create an output stream
        DataOutputStream output = null;
        int Portnumber = -1; // initialize to dummy value

        try {
            //Read the port number from a text file
            br = new BufferedReader(new FileReader("portnumber.txt"));
            String Port = br.readLine();
            br.close();

            //Convert string to int 
            Portnumber = Integer.parseInt(Port);

        
		//Open the socket
        
            MyClient = new Socket("localhost", Portnumber);
            
            input = new DataInputStream(MyClient.getInputStream());
            output = new DataOutputStream(MyClient.getOutputStream());
            
            //WRITE WHAT WE WANT TO SEND OR RECEIVE HERE
            int num = input.readInt(); // example server code
            switch(num){
                case 1: // login
                    output.write(num);
                    int user = input.read();
                    byte[] password = new byte[1024];
                    input.readFully(password);
                    // check password
                    
            } // end example server code
            
            MyClient.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		
        //Close the input, output stream and thread
        try {
            output.close();
            input.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
