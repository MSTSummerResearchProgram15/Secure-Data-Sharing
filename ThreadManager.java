import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadManager {
	public static char[] array;
	public static File fin, fout;
	
	public static void main(String[] args) throws IOException{
		//Generate parameters
		Params params;
		ParamsGen gen = new ParamsGen();
		params = gen.generate();
		
		//Preprocessing - split file into chunks
		ByteReaderWriter rw = new ByteReaderWriter();
		fin = new File("book.txt");
		long fileLength = fin.length();
		int fileSize = 128; //size of split files
		long numFiles = (long)Math.ceil((double)fileLength/(double)fileSize); //number of files = length of file/size of each smaller file
		BufferedReader br = new BufferedReader(new FileReader(fin));
		for(int i = 0; i < numFiles; i++){
			String fileName = "File" + i + ".txt";
			fout = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fout));
			array = new char[fileSize];
			array = rw.readFile(fileSize, br);
			rw.writeFile(array, bw);	
			bw.close();
		}
		br.close();
		
		
		//Encrypt the file chunks
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i = 0; i < numFiles; i++){
			String fileIn = "File" + i + ".txt";
        	fin = new File(fileIn);
			String fileOut = "Encrypted" + i + ".txt";
        	fout = new File(fileOut);
        	Runnable worker = new Encryption(fin, fout, params);
        	executor.execute(worker);
        } 
        
        
        //Decrypt the file chunks
        for(int i = 0; i < numFiles; i++){
        	String fileIn = "Encrypted" + i + ".txt";
        	fin = new File(fileIn);
      		String fileOut = "Decrypted" + i + ".txt";
      		fout = new File(fileOut);
        	Runnable worker = new Decryption(fin, fout, params);
        	executor.execute(worker);
        }
        executor.shutdown();
        
        /*
        //Post processing - merge the file chunks back into one file
  		String fileOut = "Decrypted_Final.txt";
  		fout = new File(fileOut);
  		BufferedWriter writer = new BufferedWriter(new FileWriter(fout, true));
        for(int i = 0; i < numFiles; i++){
      		String fileIn = "Decrypted" + i + ".txt";
      		fin = new File(fileIn);
      		BufferedReader reader = new BufferedReader(new FileReader(fin));
      		char[] file = rw.readFile(fileSize, reader);
      		rw.writeFile(file, writer);
      		Files.delete(fin.toPath());
      		reader.close();
        }
        writer.close();*/
	}
}
