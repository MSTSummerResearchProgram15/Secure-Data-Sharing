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
import java.util.concurrent.TimeUnit;


public class ThreadManager {
	public static char[] array;
	public static File fin, fout;
	
	public static void main(String[] args) throws IOException{
		//Generate parameters
		Params params;
		ParamsGen gen = new ParamsGen();
		params = gen.generate();
		FileSplitMerge sm = new FileSplitMerge();
		//Preprocessing - split file into chunks
		fin = new File("test.txt");
		long fileLength = fin.length();
		int fileSize = 128; //size of split files
		long numFiles = (long)Math.ceil((double)fileLength/(double)fileSize); //number of files = length of file/size of each smaller file
		String[] splitFileNames = new String[(int)numFiles];
		for(int i = 0; i < numFiles; i++){
			splitFileNames[i] = "File" + i + ".txt";
		}
		sm.splitFile(fin, splitFileNames, fileSize);
		
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
        for(int j = 0; j < numFiles; j++){
        	String fileIn = "Encrypted" + j + ".txt";
        	fin = new File(fileIn);
      		String fileOut = "Decrypted" + j + ".txt";
      		fout = new File(fileOut);
        	Runnable worker = new Decryption(fin, fout, params);
        	executor.execute(worker);
        }
        executor.shutdown();
        
        //wait for threads to finish before continuing
        try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {e.printStackTrace();}
        
        
        //Create an array of all files to merge
        String[] inputFiles = new String[(int) numFiles];
        for(int i = 0; i < numFiles; i++){
        	inputFiles[i] = "Decrypted" + i + ".txt";
        }
        String outputFile = "Decrypted_Final.txt";
        sm.mergeFiles(inputFiles, outputFile, (int)numFiles);
        
	}
}
