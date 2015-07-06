import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/*This class splits one input file into multiple output files,
 * or merges multiple input files into one output file
 */
public class FileSplitMerge {
	File fin, fout;
	String filein;
	String mergedOutputFile;
	String fileToSplit;
	String[] splitOutputFiles;
	int chunkSize;
	long numFiles;
	FileReaderWriter rw;
	
	FileSplitMerge(){
		//Default constructor (can be used when you just want to merge but not split, don't know the original input file, etc)
	}
	
	FileSplitMerge(String inputFile, int chunkSize){
		this.filein = inputFile;
		this.chunkSize = chunkSize;
		this.numFiles = (long)Math.ceil((double)this.filein.length()/(double)this.chunkSize); //number of files = length of input file file/file chunk size
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
			Files.delete(fin.toPath()); //delete the input file
			br.close();
		}
		bw.close();
	}
	
	void splitFile() throws IOException{
		FileReaderWriter rw = new FileReaderWriter();
		BufferedReader br = new BufferedReader(new FileReader(filein));
		
		//Generate an array containing the names for the split files. Maybe let the user specify the name instead of hardcoding?
		String[] splitFileNames = new String[(int)numFiles];
		for(int i = 0; i < numFiles; i++){
			splitFileNames[i] = "File" + i + ".txt";
		}
		
		//Read "chunksize" bytes from the input file, write it to an output file, and repeat
		for(int i = 0; i < numFiles; i++){
			BufferedWriter bw = new BufferedWriter(new FileWriter(splitOutputFiles[i]));
			char[] input = rw.readFile(chunkSize, br);
			rw.writeFile(input, bw);
			bw.close();
		}
		br.close();
	}
}
