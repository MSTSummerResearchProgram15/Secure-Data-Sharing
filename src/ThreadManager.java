import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadManager {
	public static byte[] array;
	public static void main(String[] args) throws IOException{
		//Generate parameters
		Params params;
		ParamsGen gen = new ParamsGen();
		params = gen.generate();
		//Preprocessing - split file into chunks
		ByteReaderWriter rw = new ByteReaderWriter();
		File fin = new File("book.txt");
		long fileLength = fin.length();
		int fileSize = 128;
		long numFiles = (long)Math.ceil((double)fileLength/(double)fileSize); //number of files
		InputStream in = new FileInputStream(fin);
		
		for(int i = 0; i < numFiles; i++){
			String fileName = "File" + i + ".txt";
			File fout = new File(fileName);
			OutputStream out = new FileOutputStream(fout);
			array = new byte[fileSize];
			array = rw.readFile(fileSize, in);
			rw.writeFile(array, out);	
			out.close();
		}
		in.close();
		
		//Encrypt the file chunks
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i = 0; i < numFiles; i++){
        	fin = new File("File" + i + ".txt");
        	Runnable worker = new Encryption(fin, fin, params);
        	executor.execute(worker);
        }        
        /*
        for(int i = 0; i < numFiles; i++){
        	fin = new File("File" + i + ".txt");
        	Runnable worker = new Decryption(fin, fin, params);
        	executor.execute(worker);
        }*/
        executor.shutdown();
        
        
	}
}
