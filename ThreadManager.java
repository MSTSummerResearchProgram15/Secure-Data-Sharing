import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.dropbox.core.DbxException;


public class ThreadManager {
	public char[] array;
	public File fin, fout;
	public long numFiles;
        Params params;
        User owner, user1;
        KeyGen key;
        FileSplitMerge sm;
        String directory;
        boolean isOwner;
        String baseName;
	
	public ThreadManager(int keySize){
            //Generate parameters
            directory = "message";
            File dir = new File(directory);
            dir.mkdir();   
            directory = directory + "\\"; // add back slash
            ParamsGen gen = new ParamsGen();
            owner = new User();
            switch(keySize){
            	case 128: params = gen.generate(160, 512);
            		break;
            	case 256: params = gen.generate(360, 1024);
            		break;
            	case 512: params = gen.generate(620, 2048);
            		break;
            	case 1024: params = gen.generate(1040, 4096);
            		break;
            	case 2048: params = gen.generate(2080, 8192);
            		break;
            	case 4096: params = gen.generate(4160, 16384);
            		break;
            	default: params = gen.generate(160, 512);
            		break;	
            	//If you choose anything greater than 512 it probably will never finish...
            }
            key = new KeyGen(params);
            owner = key.generate(); //generate the keys for the data owner
            user1 = new User();
            user1 = key.generate(); //generate the keys for data user 1
            System.out.println("The PK size is: " + owner.getPK().getLengthInBytes());
        }
        

        public ThreadManager(){
            params = new Params();
            owner = new User();
        }
        
        public void restoreFromNetwork(){
            // takes a socket and reads from server various values, assigning as it goes?
        }
        
        public void preprocess(File file, int blockSize) throws IOException, DbxException{

            //Preprocessing - split file into chunks
            int chunkSize = blockSize; //desired size of file chunks, probably will only allow 128, 256, and 512 in future versions
            File fin = file;
            numFiles = (long)Math.ceil((double)fin.length()/(double)chunkSize);
            String fileName = file.getName();
            int pos = fileName.lastIndexOf(".");
            baseName = "";
            if (pos > 0) {
            baseName = fileName.substring(0, pos);
            }
            sm = new FileSplitMerge(file, blockSize); // doesnt know where to look! only looks in current dir.
            sm.splitFile(directory);
        }
        
        // include "." in the extension
        public void Encrypt(String filePath, String baseFileName, String extension) throws IOException, DbxException{
    		DropboxReadWrite dp = new DropboxReadWrite();
            //Encrypt the file chunks
            ExecutorService executor = Executors.newFixedThreadPool(4);
            for(int i = 0; i < numFiles; i++){
                String fileIn = baseFileName + i + extension;
                fin = new File(fileIn);
                String fileOut = directory + baseFileName + i + ".encrypted";
                fout = new File(fileOut);
                Runnable worker = new Encryption(fin, fout, params, owner, dp, i, fileIn);
                executor.execute(worker);
            } 
 
            // sign file chunks
            for(int i = 0; i < numFiles; i++){
                String fileIn = baseFileName + i + ".txt";
                fin = new File(fileIn);
                String fileOut = directory + baseFileName + i + ".encrypted" + ".signature";
                fout = new File(fileOut);
                Runnable worker = new Signer(fin, fout, params, owner, i, dp, baseName);
                executor.execute(worker);
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {e.printStackTrace();}               
        }
        
        public void decrypt(String baseFileName, String outputDirectory) throws IOException {
            File dir = new File(outputDirectory);
            dir.mkdir(); 
            outputDirectory = outputDirectory + "\\";
            ExecutorService executor = Executors.newFixedThreadPool(4);
            // verify file chunks
            for(int i = 0; i < numFiles; i++){
                String fileIn = directory + baseFileName + i + ".encrypted";
                fin = new File(fileIn);
                String sigIn = directory + baseFileName + i + ".encrypted" + ".signature";
                fout = new File(sigIn);                                 // not acutally an output....
                Runnable worker = new Verifier(fin, fout, params, owner);
                executor.execute(worker);
            }            
            //Decrypt the file chunks
            
            // reencryption will be done on server eventually
            key.generateRK(owner, user1); //generate the proxy re-encryption key 
            
            for(int j = 0; j < numFiles; j++){
                String fileIn = directory + baseFileName + j + ".encrypted";
                fin = new File(fileIn);
                String fileOut = outputDirectory + baseFileName + j + ".txt";
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
                inputFiles[i] = outputDirectory + baseFileName + i + ".txt";
            }
            sm.mergeFiles(inputFiles);
        
	}        

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
        
}
