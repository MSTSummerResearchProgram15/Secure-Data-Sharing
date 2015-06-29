import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * Created by Dylan on 6/26/2015.
 */
public class BLSSignature {
    private Pairing pairing;
    private Element sysParams;
    private Element privateKey;
    private Element publicKey;

    public BLSSignature(String propertiesFileName){
        pairing = PairingFactory.getPairing(propertiesFileName);    // curve parameters
        sysParams = pairing.getG1().newRandomElement().getImmutable();  // system parameters
        privateKey = pairing.getZr().newRandomElement();        // secret/private key
        publicKey = sysParams.powZn(privateKey);                               // public key
    }

    public Element getPublicKey() {
        return publicKey;
    }

    public Pairing getPairing() {
        return pairing;
    }

    public Element getSysParams() {
        return sysParams;
    }

    public Element getPrivateKey() {
        return privateKey;
    }
}
