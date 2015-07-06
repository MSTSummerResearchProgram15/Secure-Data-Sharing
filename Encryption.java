import it.unisa.dia.gas.jpbc.Element;

import java.io.BufferedReader;
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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Encryption implements Runnable{
	File fin, fout;
	char[] array;
	BufferedReader br;
	FileOutputStream out;
	Params params;
	public Element c1, c2, reencrypt;
	User owner;
	public Encryption(File fin, File fout, Params params, User owner){
		this.fin = fin;
		this.fout = fout;
		this.params = params;
		this.owner = owner;
	}
		
	public void run(){
		Element plainText;
		FileReaderWriter bytes = new FileReaderWriter();
		long length = fin.length(); //Length of input file
		byte[] result, stringToBytes = null;
		String temp;
		
		try {
			br = new BufferedReader(new FileReader(fin));
		} catch (FileNotFoundException e4) {e4.printStackTrace();}
		
		try {
			out = new FileOutputStream(fout, true);
		} catch (FileNotFoundException e2) {e2.printStackTrace();}
		
		int blockSize = (int)length; //How many bytes of file to encrypt at a time
		long blocks = (long)Math.ceil((double)length/(double)blockSize); //How many blocks the file will be encrypted in
		//for(int i = 0; i < blocks; i++){
			array = new char[blockSize];
			try {
				array = bytes.readFile(blockSize, br); //Read the plaintext file into char array
			} catch (IOException e3) {e3.printStackTrace();}
			
			//Convert char array to string, and then encode string and convert to byte array
			temp = new String(array);
			
			try {
				stringToBytes = temp.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
			//encrypt here
			plainText = params.getgt().newRandomElement();
			plainText.setFromBytes(stringToBytes);
			c1 = owner.getPK().powZn(params.getk());
			c2 = params.getz_k().mul(plainText);
			result = new byte[c1.getLengthInBytes() + c2.getLengthInBytes()]; //set the byte array size = size of both ciphertexts
			System.arraycopy(c1.toBytes(), 0, result, 0, c1.getLengthInBytes());
			System.arraycopy(c2.toBytes(), 0, result, c1.getLengthInBytes(), c2.getLengthInBytes()); //concatenate both ciphertexts into 1 array
			
			try {
				bytes.writeFile(result, out); //write the result to output file
			} catch (IOException e1) {e1.printStackTrace();}
		//}
		
		try {
			br.close();
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			out.close();
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			Files.delete(fin.toPath());
		} catch (IOException e) {e.printStackTrace();}
		
	}
}
