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
	public static long numFiles;
	
	public static void main(String[] args) throws IOException{
            //Generate parameters
            Params params;
            ParamsGen gen = new ParamsGen();
            params = gen.generate();
            User owner = new User();
            KeyGen key = new KeyGen(params);
            owner = key.generate(); //generate the keys for the data owner
            User user1 = new User();
            user1 = key.generate(); //generate the keys for data user 1

            //Preprocessing - split file into chunks
            int chunkSize = 128; //desired size of file chunks, probably will only allow 128, 256, and 512 in future versions
            String inputFile = "test.txt";
            File fin = new File(inputFile);
            numFiles = (long)Math.ceil((double)fin.length()/(double)chunkSize);
            FileSplitMerge sm = new FileSplitMerge(inputFile, chunkSize);
            sm.splitFile();

            //Encrypt the file chunks
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for(int i = 0; i < numFiles; i++){
                    String fileIn = "File" + i + ".txt";
            fin = new File(fileIn);
                    String fileOut = "Encrypted" + i + ".txt";
            fout = new File(fileOut);
            Runnable worker = new Encryption(fin, fout, params, owner);
            executor.execute(worker);
            } 

            // sign file chunks
            for(int i = 0; i < numFiles; i++){
                    String fileIn = "Encrypted" + i + ".txt";
                    fin = new File(fileIn);
                    String fileOut = "Encrypted" + i + ".txt" + ".signature";
                    fout = new File(fileOut);
                    Runnable worker = new Signer(fin, fout, params, owner);
                    executor.execute(worker);
            }

            // verify file chunks
            for(int i = 0; i < numFiles; i++){
                    String fileIn = "Encrypted" + i + ".txt";
                    fin = new File(fileIn);
                    String sigIn = "Encrypted" + i + ".txt" + ".signature";
                    fout = new File(sigIn);                                 // not acutally an output....
                    Runnable worker = new Signer(fin, fout, params, owner);
                    executor.execute(worker);
            }

            //Decrypt the file chunks
            key.generateRK(owner, user1); //generate the proxy re-encryption key
            for(int j = 0; j < numFiles; j++){
                    String fileIn = "Encrypted" + j + ".txt";
                    fin = new File(fileIn);
                    String fileOut = "Decrypted" + j + ".txt";
                    fout = new File(fileOut);
                    Runnable worker = new Decryption(fin, fout, params, owner, user1);
                    executor.execute(worker);
            }
            executor.shutdown();

            //wait for all threads to finish before continuing
            try {
                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {e.printStackTrace();}

            //Create an array of all files to merge
            String[] inputFiles = new String[(int)numFiles];
            for(int i = 0; i < numFiles; i++){
                    inputFiles[i] = "Decrypted" + i + ".txt";
            }
            sm.mergeFiles(inputFiles);
        
	}
}
