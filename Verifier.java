import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import org.bouncycastle.jce.provider.JDKMessageDigest;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;

/**
 * Created by Dylan on 6/29/2015.
 */
public class Verifier implements Runnable{
    Pairing pairing;
    Element sysParams;
    Element publicKey;
    Element privateKey;
    File signature;
    File message;

    // constructor
    public Verifier(File message, File signature, Params params){
        this.pairing = params.getPairing();
        this.sysParams = params.getg();
        this.publicKey = params.getOwnerPK();
        this.privateKey = params.getOwnerSK();
        this.message = message;
        this.signature = signature;
    }

    //run
    public void run(){
        InputStream mis;
        InputStream sis;
        Element sig = null;
        try {

            mis = new FileInputStream(message);
            sis = new FileInputStream(signature);
            byte[] message = new byte[mis.available()];
            byte[] sign = new byte[sis.available()];
            mis.read(message);
            sis.read(sign);
            sig.setFromBytes(sign);
            if(!verifySignature(sig, message)){
                System.out.println("not valid");
            }
            mis.close();
            sis.close();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

    }

    // verifies that the signature is valid, given the necessary inputs
    public boolean verifySignature(Element signature, byte[] message){
        Element map = mapValue(message);
        Element temp1 = pairing.pairing(signature, sysParams);
        Element temp2 = pairing.pairing(map,publicKey);
        return temp1.equals(temp2);
    }

    // takes the value, and maps the hash of the value
    public Element mapValue(byte[] value){
        Element map;
        byte[] hash = hash(value);
        map = pairing.getG1().newElement().setFromHash(hash, 0, hash.length);
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
