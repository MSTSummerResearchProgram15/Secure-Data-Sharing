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
	Socket sock;
	
	public FTPClient() throws UnknownHostException, IOException{
		 long start = System.currentTimeMillis();

	     // localhost for testing
	     sock = new Socket("127.0.0.1", 13262);
	     System.out.println("Connecting...");
	     // receive file
	     long end = System.currentTimeMillis();
	     System.out.println(end - start);

	}

    public void receiveFile() throws Exception {
	    InputStream is = sock.getInputStream();
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
	     sock.close();
    }
}
