import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


public class ParamsGen {
	public Pairing pairing;
	public Params params;
	
	public Params generate(){
		params = new Params();
		//Get the curve parameters
		PairingParametersGenerator pg = new TypeACurveGenerator(320, 1024);
		PairingParameters curveParams = pg.generate();
		//PairingParameters curveParams = PairingFactory.getPairingParameters("a_181_603.properties");
		this.pairing = PairingFactory.getPairing(curveParams);
		params.setPairing(this.pairing);
				
		//Initialize the parameters for second-level encryption
		params.setg1(pairing.getG1());
	    params.setgt(pairing.getGT());
	    params.setzr(pairing.getZr());
	    params.setg(params.getg1().newRandomElement().getImmutable());
		params.setgpre(params.getg().getElementPowPreProcessing());
		params.setk(params.getzr().newRandomElement().getImmutable());
		params.setg_k(params.getgpre().powZn(params.getk()).getImmutable());
		params.setz_k(pairing.pairing(params.getg(), params.getg_k()).getImmutable());
							      
		//Generate data owner keys
		params.setOwnerSK(pairing.getZr().newRandomElement().getImmutable()); //private key
		params.setOwnerPK(params.getgpre().powZn(params.getOwnerSK()).getImmutable());
		params.setOwnerISK(params.getOwnerSK().invert().getImmutable()); //invert the secret key to calculate the proxy re-encryption key
				
				
		//Generate user keys (USER1)
		params.setUserSK(pairing.getZr().newRandomElement().getImmutable()); //private key
		params.setUserPK(params.getgpre().powZn(params.getUserSK()).getImmutable());
		params.setUserISK(params.getUserSK().invert().getImmutable());
				
		//Generate proxy re-encryption keys
		params.setReEncryptionKey(params.getUserPK().powZn(params.getOwnerISK()).getImmutable());
		
		return params;
	}
	
}
