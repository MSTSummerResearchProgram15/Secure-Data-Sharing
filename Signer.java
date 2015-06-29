import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import org.bouncycastle.jce.provider.JDKMessageDigest;

import java.security.MessageDigest;

/**
 * Created by Dylan on 6/24/2015.
 *
 * Uses sha256 hash
 */
public class Signer {

    Pairing pairing;
    Element sysParams;
    Element publicKey;
    Element privateKey;

    // full constructor
    public Signer(Pairing pairing, Element sysParams, Element publicKey, Element privateKey){
        this.pairing = pairing;
        this.sysParams = sysParams;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    // partial constructor, use for verifying
    public Signer(Pairing pairing, Element sysParams, Element publicKey){
        this(pairing, sysParams, publicKey, null);
    }

    // partial constructor, use for signing
    public Signer(Pairing pairing, Element privateKey){
        this(pairing, null, null, privateKey);
    }

    // generates the signature using a value and the private key (uses hash of value)
    public Element generateSignature(byte[] message) {
        Element sig;
        Element map = mapValue(message);
        sig = map.powZn(privateKey);
        return sig;
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
