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
	BufferedWriter bw;
	BufferedReader br;
	File fin, fout;
	String[] filesToMerge;
	String mergedOutputFile;
	File fileToSplit;
	String[] splitOutputFiles;
	int numFiles, chunkSize;
	FileReaderWriter rw;
	
	void mergeFiles(String[] inputFiles, String outputFile, int numFiles) throws IOException{
		this.filesToMerge = inputFiles;
		this.mergedOutputFile = outputFile;
		this.numFiles = numFiles;
		rw = new FileReaderWriter();
		fout = new File(mergedOutputFile);
		bw = new BufferedWriter(new FileWriter(fout, true)); //Set the FileWriter to append
		
		for(int i = 0; i < numFiles; i++){ //iterate through each input file
			fin = new File(filesToMerge[i]);
			int fileSize = (int)fin.length();
			br = new BufferedReader(new FileReader(fin));
			char[] input = rw.readFile(fileSize, br); //read the input file
			rw.writeFile(input, bw); //write the file to the output file
			Files.delete(fin.toPath()); //delete the input file
			br.close();
		}
		bw.close();
	}
	
	void splitFile(File fileToSplit, String[] splitOutputFiles, int chunkSize) throws IOException{
		this.fileToSplit = fileToSplit;
		this.splitOutputFiles = splitOutputFiles;
		this.chunkSize = chunkSize; //Size of the file chunks
		rw = new FileReaderWriter();
		fin = fileToSplit;
		//fin = new File(fileToSplit);
		long numFiles = (long)Math.ceil((double)fin.length()/(double)chunkSize); //number of files = length of file/size of each smaller file
		br = new BufferedReader(new FileReader(fin));
		
		for(int i = 0; i < numFiles; i++){
			bw = new BufferedWriter(new FileWriter(splitOutputFiles[i]));
			char[] input = rw.readFile(chunkSize, br);
			rw.writeFile(input, bw);
			bw.close();
		}
		br.close();
	}
}
