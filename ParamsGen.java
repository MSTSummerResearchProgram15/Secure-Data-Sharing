
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class ParamsGen {

    public Pairing pairing;
    public Params params;

    public Params generate(int x, int y) {
        params = new Params();
        //Get the curve parameters
        PairingParametersGenerator pg = new TypeACurveGenerator(x, y);
        PairingParameters curveParams = pg.generate();
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

        return params;
    }

}
