import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.dropbox.core.DbxException;

/*This class splits one input file into multiple output files,
 * or merges multiple input files into one output file
 */
public class FileSplitMerge {
	File fin, fout;
	File filein;
	String mergedOutputFile;
	String fileToSplit;
	int chunkSize;
	long numFiles;
	FileReaderWriter rw;
	
	FileSplitMerge(){
		//Default constructor (can be used when you just want to merge but not split, don't know the original input file, etc)
	}
	
	FileSplitMerge(File inputFile, int chunkSize){
		this.filein = inputFile;
		this.chunkSize = chunkSize;
		this.numFiles = (long)Math.ceil((double)this.filein.length()/(double)this.chunkSize); //number of files = length of input file file/file chunk size
		System.out.println(numFiles);
	}
	
	void mergeFiles(String[] inputFiles) throws IOException{
		String[] filesToMerge = inputFiles;
		FileReaderWriter rw = new FileReaderWriter();
		File fout = new File("Decrypted_Final.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fout, true)); //Set the FileWriter to append
		
		for(int i = 0; i < numFiles; i++){ //iterate through each input file
			File fin = new File(filesToMerge[i]);
			int fileSize = (int)fin.length();
			BufferedReader br = new BufferedReader(new FileReader(fin));
			char[] output = rw.readFile(fileSize, br); //read the input file
			rw.writeFile(output, bw); //write the file to the output file
			//Files.delete(fin.toPath()); //delete the input file// lets use the FileDeleter for this, easier to track down errors later on, call FileDeleter from Decrypt()
			br.close();
		}
		bw.close();
	}
	
	void splitFile(String directory) throws DbxException, IOException{
		FileReaderWriter rw = new FileReaderWriter();
		BufferedReader br;
		br = new BufferedReader(new FileReader(filein));
		DropboxReadWrite dropbox = new DropboxReadWrite();
		
		//Generate an array containing the names for the split files.
		
                String fileName = filein.getName();
                int pos = fileName.lastIndexOf(".");
                String baseName = "";
                if (pos > 0) {
                baseName = fileName.substring(0, pos);
                }
                String fileExtension;
                int pos2 = fileName.length();
                fileExtension = fileName.substring(pos, pos2);
                String filePath = filein.getPath();
                int pathLength = filePath.length();
                filePath = filePath.substring(0, pathLength - pos2);
		//Read "chunksize" bytes from the input file, write it to an output file, and repeat
		for(int i = 0; i < numFiles; i++){
			try{
			char[] input = rw.readFile(chunkSize, br);
			BufferedWriter bw = new BufferedWriter(new FileWriter(baseName + i + fileExtension));
			bw.write(input);
			System.out.println("File written");
			bw.close();
			dropbox.write(baseName + i + fileExtension, i);
			Path path = Paths.get(baseName + i + fileExtension);
			System.out.println(path);
			System.out.println(Files.deleteIfExists(path));
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
