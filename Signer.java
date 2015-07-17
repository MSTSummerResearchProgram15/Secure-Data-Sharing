import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import org.bouncycastle.jce.provider.JDKMessageDigest;

import com.dropbox.core.DbxException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

/**
 * Created by Dylan on 6/24/2015.
 *
 * Uses SHA256 hash
 */
public class Signer implements Runnable {

    Field g1;
    Element sysParams;
    Element publicKey;
    Element privateKey;
    File output;
    File input;
    int i;
    DropboxReadWrite dp;

    // constructor
    public Signer(File input, File output, Params params, User user, int i, DropboxReadWrite dp){
        this.g1 = params.getg1();
        this.sysParams = params.getg();
        this.publicKey = user.getPK();
        this.privateKey = user.getSK();
        this.output = output;
        this.input = input;
        this.i = i;
        this.dp = dp;
    }

    //run
    public void run(){
        InputStream is;
        OutputStream os;
        try {
            is = new FileInputStream(input);
            byte[] message = new byte[is.available()];
            is.read(message);
            is.close();
            os = new FileOutputStream(input);
            os.write(generateSignature(message).toBytes());
            os.close();
			dp.write(input.getName(), i);
			try {
				System.out.println(Files.deleteIfExists(input.toPath()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        catch(DbxException e){
        	e.printStackTrace();
        }
    }

    // generates the signature using a value and the private key (uses hash of value)
    public Element generateSignature(byte[] message) {
        Element sig;
        Element map = mapValue(message);
        sig = map.powZn(privateKey);
        return sig;
    }

    // takes the value, and maps the hash of the value
    public Element mapValue(byte[] value){
        Element map;
        byte[] hash = hash(value);
        map = g1.newElement().setFromHash(hash, 0, hash.length);
        return map;
    }

    // computes hash of value with SHA256
    public byte[] hash(byte[] value){
        byte[] hash;
        MessageDigest md = new JDKMessageDigest.SHA256();
        hash = md.digest(value);
        return hash;
    }

}
