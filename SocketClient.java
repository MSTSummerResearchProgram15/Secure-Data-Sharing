
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import java.io.*;
import java.net.*;
import java.util.Arrays;
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

    /*
     reads in this order: g, k, g_k, z_k, privateKey, role
     */
    public void populateThreadManager(ThreadManager tm) throws IOException {
        //PairingParameters p;
        byte[] g = new byte[512];
        byte[] k = new byte[512];
        byte[] gk = new byte[512];
        byte[] zk = new byte[512];

        int role;
                
        byte[] buffer = new byte[512]; // max size?

        output.writeBytes("Userinfo:" + tm.owner.getUserID()); // send request for info to server

        boolean needG = true;
        boolean needK = true;
        boolean needGK = true;
        boolean needZK = true;
        boolean needSKey = true;
        byte posZero;

        while (needG || needK || needGK || needZK || needSKey) {
            input.readFully(buffer);
            posZero = buffer[0];

            for (int i = 0; i < buffer.length - 1; i++) {
                buffer[i] = buffer[i + 1];
            }
            Arrays.copyOf(buffer, buffer.length - 1);

            if (posZero == 'a') {
                g = buffer;
                needG = false;
                posZero = 0;
            }
            if (posZero == 'b') {
                k = buffer;
                needK = false;
                posZero = 0;
            }
            if (posZero == 'c') {
                gk = buffer;
                needGK = false;
                posZero = 0;
            }
            if (posZero == 'd') {
                zk = buffer;
                needZK = false;
                posZero = 0;
                if (!(needG || needK || needGK || needZK)) {
                    tm.params = new Params(g, k, gk, zk);
                }
            }
            if (posZero == 'e') {
                tm.owner.setSK(tm.params.getzr().newElementFromBytes(buffer));

                tm.owner.setPK(tm.params.getgpre().powZn(tm.owner.getSK()).getImmutable());

                tm.owner.setISK(tm.owner.getSK().invert().getImmutable());
                needSKey = false;
                posZero = 0;

            }

            //byte[] buffer = new byte[input.available()];
            //input.readFully(buffer);
            //p = SerializationUtils.deserialize(buffer);

            role = input.readInt();
            if (role == 0) {
                tm.isOwner = true;
            } else {
                tm.isOwner = false;
            }
        }
    }

    public String[] populateFileList() {

        String temp = "";
        String last = "";
        String[] out = new String[1024];
        int next = 0;
        byte[] buffer = new byte[100];
        boolean moreFiles = true;

        while (moreFiles) {
            try {
                output.writeBytes("fileList:");
                input.readFully(buffer);
            } catch (IOException ex) {
                Logger.getLogger(FileDownloadPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            last = temp;
            temp = new String(buffer);
            moreFiles = !buffer.equals("NOMOREFILES");

            if (moreFiles && !buffer.equals(last)) {
                out[next] = new String(buffer);
                next++;
            }
        }
        return out;
    }

}
