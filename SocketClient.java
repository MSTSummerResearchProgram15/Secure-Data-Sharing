
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;


public class SocketClient {

    Socket MyClient;
    DataOutputStream output = null;
    DataInputStream input = null;

    public SocketClient() {
        BufferedReader br;
        int Portnumber;
        
        try {
            //Read the port number from a text file
            br = new BufferedReader(new FileReader("portnumber.txt"));
            String Port = br.readLine();
            br.close();

            //Convert string to int 
            Portnumber = Integer.parseInt(Port);

            //Open the socket
            MyClient = new Socket("localhost", Portnumber);           
            output = new DataOutputStream(MyClient.getOutputStream());
            input = new DataInputStream(MyClient.getInputStream());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        

    public boolean login(String usr, String pw) {        
        boolean a = true;
        int result;

        
        try {
            //WRITE WHAT WE WANT TO SEND OR RECEIVE HERE
            output.writeBytes(usr);
            output.writeBytes("\n");
            output.writeBytes(pw);
            result = input.read();

            if (result == 0) {

                a = true;
            } else {
                a = false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return a;
    }
    
    public void populateThreadManager(ThreadManager tm) throws IOException {
        PairingParameters p;
        byte[] g, k, gk, zk;
        
        
        output.writeBytes("Userinfo:" + tm.owner.getUserID()); // send request for info to server
        
        byte[] buffer = new byte[input.available()];
        input.readFully(buffer);
        p = SerializationUtils.deserialize(buffer);
        
        g = new byte[128];
        k = new byte[128];
        gk = new byte[128];
        zk = new byte[128];
        input.readFully(g);
        input.readFully(k);
        input.readFully(gk);
        input.readFully(zk);
        
        tm.params = new Params(g, k, gk, zk, p);
        
        
        int role = input.readInt();
        if(role == 0){
            
        }
        
        
        
        
    }
}
