
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

//This class reads n number of bytes from a text file at a time
public class ByteReaderWriter {
	int blockSize;
	File fout;
	long numberofBlocks;
	byte[] array;
	InputStream in;
	BufferedWriter bw;
	OutputStream os;
	int eof;
	
	//Read a file n bytes at a time (to handle large files)
	public byte[] readFile(int blockSize, InputStream in) throws IOException{
		this.blockSize = blockSize; //specify the size of the file block (in bytes)
		this.in = in;
		array = new byte[blockSize];
		int eof = 0;
		this.in.read(array, 0, blockSize);
		return array;
	}
		
	
	public void writeFile(byte[] line, OutputStream os) throws IOException{
		this.os = os;
		this.os.write(line);
	}
	
}

