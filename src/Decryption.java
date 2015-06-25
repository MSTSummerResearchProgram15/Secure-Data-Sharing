import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.unisa.dia.gas.jpbc.Element;


public class Decryption implements Runnable{
	File fin, fout;
	Params params;
	byte[] cipher1, cipher2, result;
	Element decrypt, c1, c2;
	
	public Decryption(File fin, File fout, Params params){
		this.fin = fin;
		this.fout = fout;
		this.params = params;
	}

	public void run(){
		ByteReaderWriter bytes = new ByteReaderWriter();
		InputStream in = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(fout);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long length = fin.length();
		try {
			in = new FileInputStream(fin);
		} catch (FileNotFoundException e) {}
		int ciphertextSize = 64;
		cipher1 = new byte[ciphertextSize];
		cipher2 = new byte[ciphertextSize];
		try {
			cipher1 = bytes.readFile(ciphertextSize, in);
		} catch (IOException e) {}
		
		try {
			cipher2 = bytes.readFile(ciphertextSize, in);
		} catch (IOException e) {}
		
		try {
			in.close();
		} catch (IOException e) {}
		c1 = params.getg1().newRandomElement();
		c2 = params.getg1().newRandomElement();

		c1.setFromBytes(cipher1);
		c2.setFromBytes(cipher2);
		//Begin decryption here
		Element ialpha = params.getReEncryptionKey().powZn(params.getUserISK());
		decrypt = c2.div(ialpha);
		result = new byte[decrypt.getLengthInBytes()];
		result = decrypt.toBytes();
		try {
			bytes.writeFile(result, os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Decrypted");
		}
	}
