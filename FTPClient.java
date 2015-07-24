import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class FTPClient {
	public static void main(String[] args) throws Exception {

       
    }
	
	public FTPClient() throws UnknownHostException, IOException{
		 long start = System.currentTimeMillis();

	     // localhost for testing
	     Socket sock = new Socket("127.0.0.1", 13262);
	     System.out.println("Connecting...");
	     InputStream is = sock.getInputStream();
	     // receive file
	     long end = System.currentTimeMillis();
	     System.out.println(end - start);

	     sock.close();
	}

    public void receiveFile(InputStream is) throws Exception {
        int filesize = 6022386;
        int bytesRead;
        int current = 0;
        byte[] mybytearray = new byte[filesize];

        FileOutputStream fos = new FileOutputStream("de2");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = is.read(mybytearray, current,
                    (mybytearray.length - current));
            if (bytesRead >= 0)
                current += bytesRead;
        } while (bytesRead > -1);

        bos.write(mybytearray, 0, current);
        bos.flush();
        bos.close();
    }
}
