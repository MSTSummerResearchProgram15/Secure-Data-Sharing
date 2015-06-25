import it.unisa.dia.gas.jpbc.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Encryption implements Runnable{
	File fin, fout;
	byte[] array;
	OutputStream out;
	Params params;
	public Element c1, c2, reencrypt;
	

	public Encryption(File fin, File fout, Params params){
		this.fin = fin;
		this.fout = fout;
		this.params = params;
	}
		
	public void run(){
		Element plaintext;
		ByteReaderWriter bytes = new ByteReaderWriter(); //create a new nBytes object
		InputStream in = null;
		long length = fin.length();
		byte[] result;
		try {
			in = new FileInputStream(fin);
		} catch (FileNotFoundException e) {				e.printStackTrace();
}
		int blockSize = 25;
		long blocks = (long)Math.ceil((double)length/(double)blockSize);
		for(int i = 0; i < blocks; i++){
			array = new byte[blockSize];
			try {
				array = bytes.readFile(blockSize, in);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
	
			//encrypt here
			plaintext = params.getgt().newRandomElement();
			plaintext.setFromBytes(array);
			c1 = params.getOwnerPK().powZn(params.getk());
			c2 = params.getz_k().mul(plaintext);
			result = new byte[c1.getLengthInBytes() + c2.getLengthInBytes()];
			System.arraycopy(c1.toBytes(), 0, result, 0, c1.getLengthInBytes());
			System.arraycopy(c2.toBytes(), 0, result, c1.getLengthInBytes(), c2.getLengthInBytes());
			try {
				out = new FileOutputStream(fout);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			try {
				bytes.writeFile(result, out);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
			
	
}
