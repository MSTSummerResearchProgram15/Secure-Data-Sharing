

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import it.unisa.dia.gas.jpbc.Element;

public class ProxyReEncryption {
	byte[] cipher1, cipher2;
	InputStream in;
	BufferedWriter bw;
	
	public Element reencrypt(){
		FileReaderWriter bytes = new FileReaderWriter();
		int ciphertextSize = //owner.getPK().getLengthInBytes(); get the length of PK from database
		cipher1 = new byte[ciphertextSize];
		cipher2 = new byte[ciphertextSize];
		try {
			in = new FileInputStream(fin);
		} catch (FileNotFoundException e) {}
		try {
			bw = new BufferedWriter(new FileWriter(fout, true));
		} catch (IOException e1) {e1.printStackTrace();}
		try {
			cipher1 = bytes.readFile(ciphertextSize, in);
		} catch (IOException e) {}
	
	try {
		cipher2 = bytes.readFile(ciphertextSize, in);
	} catch (IOException e) {}
	
	c1 = params.getg1().newRandomElement();
	c2 = params.getgt().newRandomElement();
	
	c1.setFromBytes(cipher1);
	c2.setFromBytes(cipher2);
	
	//Begin decryption here
	Element reencrypt = params.getPairing().pairing(c1,user1.getReEncryptKey());
	Element ialpha = reencrypt.powZn(user1.getISK());
	
	}
}
