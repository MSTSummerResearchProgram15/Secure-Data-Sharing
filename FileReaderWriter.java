
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//This class reads n number of bytes from a text file at a time
public class FileReaderWriter {
	int blockSize;
	File fout;
	long numberofBlocks;
	char[] charArray;
	byte[] byteArray;
	BufferedReader br;
	BufferedWriter bw;
	InputStream is;
	OutputStream os;
	int eof;
	
	//Read a file n bytes at a time (to handle large files)
	public char[] readFile(int blockSize, BufferedReader br) throws IOException{
		this.blockSize = blockSize; //specify the size of the file block (in bytes)
		this.br = br;
		charArray = new char[blockSize];
		this.br.read(charArray, 0, blockSize);
		return charArray;
	}
	
	public byte[] readFile(int blockSize, InputStream is) throws IOException{
		this.blockSize = blockSize; //specify the size of the file block (in bytes)
		this.is = is;
		byteArray = new byte[blockSize];
		this.is.read(byteArray, 0, blockSize);
		return byteArray;
	}
	
	public void writeFile(char[] line, BufferedWriter bw) throws IOException{
		this.bw = bw;
		this.bw.write(line);
	}
	
	public void writeFile(byte[] line, OutputStream os) throws IOException{
		this.os = os;
		this.os.write(line);
	}
	
}

