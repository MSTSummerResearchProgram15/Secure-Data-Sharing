import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ThreadManager {
	public static char[] array;
	public static File fin, fout;
	public static long numFiles;
        Params params;
        User owner, user1;
        KeyGen key;
        FileSplitMerge sm;
	
	public ThreadManager(){
            //Generate parameters
            
            ParamsGen gen = new ParamsGen();
            params = gen.generate();
            owner = new User();
            key = new KeyGen(params);
            owner = key.generate(); //generate the keys for the data owner
            user1 = new User();
            user1 = key.generate(); //generate the keys for data user 1
        }
        
        public void preprocess(String fileName, int blockSize) throws IOException{
            //Preprocessing - split file into chunks
            int chunkSize = blockSize; //desired size of file chunks, probably will only allow 128, 256, and 512 in future versions
            String inputFile = fileName;
            File fin = new File(inputFile);
            numFiles = (long)Math.ceil((double)fin.length()/(double)chunkSize);
            sm = new FileSplitMerge(inputFile, blockSize);
            sm.splitFile();
        }
        
        // include "." in the extension
        public void Encrypt(String filePath, String baseFileName, String extension){
            //Encrypt the file chunks
            ExecutorService executor = Executors.newFixedThreadPool(4);
            for(int i = 0; i < numFiles; i++){
                String fileIn = filePath + baseFileName + i + extension;
                fin = new File(fileIn);
                String fileOut = filePath + baseFileName + i + ".encrypted";
                fout = new File(fileOut);
                Runnable worker = new Encryption(fin, fout, params, owner);
                executor.execute(worker);
            } 
            
            FileDeleter fd = new FileDeleter(filePath, baseFileName, extension, numFiles);
            fd.delete(); // deletes plaintext split files

            // sign file chunks
            for(int i = 0; i < numFiles; i++){
                String fileIn = baseFileName + i + ".encrypted";
                fin = new File(fileIn);
                String fileOut = baseFileName + i + ".encrypted" + ".signature";
                fout = new File(fileOut);
                Runnable worker = new Signer(fin, fout, params, owner);
                executor.execute(worker);
            }
        }
        public void decrypt(String baseFileName) throws IOException {
            ExecutorService executor = Executors.newFixedThreadPool(4);
            // verify file chunks
            for(int i = 0; i < numFiles; i++){
                String fileIn = baseFileName + i + ".encrypted";
                fin = new File(fileIn);
                String sigIn = baseFileName + i + ".encrypted" + ".signature";
                fout = new File(sigIn);                                 // not acutally an output....
                Runnable worker = new Signer(fin, fout, params, owner);
                executor.execute(worker);
            }

            //Decrypt the file chunks
            key.generateRK(owner, user1); //generate the proxy re-encryption key
            for(int j = 0; j < numFiles; j++){
                String fileIn = baseFileName + j + ".encrypted";
                fin = new File(fileIn);
                String fileOut = baseFileName + j + ".txt";
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
                inputFiles[i] = baseFileName + i + ".txt";
            }
            sm.mergeFiles(inputFiles);
        
	}
}
