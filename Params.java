import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

public class Params {
	private Field zr, g1, gt;
	private Element g, k, g_k, z_k;
	private ElementPowPreProcessing gpre;
	private Pairing pairing;
	private int chunkSize;

    public Params(Element g, Element k, Element g_k, Element z_k, ElementPowPreProcessing gpre, Pairing pairing) {
        this.g = g;
        this.k = k;
        this.g_k = g_k;
        this.z_k = z_k;
        this.gpre = gpre;
        this.pairing = pairing;
        this.zr = this.pairing.getZr();
        this.g1 = this.pairing.getG1();
        this.gt = this.pairing.getGT();
    }
    
    public Params(){}
        
        
	public void setPairing(Pairing pairing){
		this.pairing = pairing;
	}
	
	public void setFileChunkSize(int chunkSize){
		this.chunkSize = chunkSize;
	}
	
	public void setg1(Field g1){
		this.g1 = g1;
	}
	
	public void setgt(Field gt){
		this.gt = gt;
	}
	
	public void setzr(Field zr){
		this.zr = zr;
        }
	
	public void setg(Element g){
		this.g = g;
	}
	
	public void setgpre(ElementPowPreProcessing gpre){
		this.gpre = gpre;
	}
	
	public void setk(Element k){
		this.k = k;
	}
	
	public void setg_k(Element g_k){
		this.g_k = g_k;
	}
	
	public void setz_k(Element z_k){
		this.z_k = z_k;
	}
	
	public Field getg1(){
		return this.g1;
	}
	
	public Field getgt(){
		return this.gt;
	}
	
	public Field getzr(){
		return this.zr;
	}
	
	public Element getg(){
		return this.g;
	}
	
	public ElementPowPreProcessing getgpre(){
		return this.gpre;
	}
	
	public Element getk(){
		return this.k;
	}
	
	public Element getg_k(){
		return this.g_k;
	}
	
	public Element getz_k(){
		return this.z_k;
	}
	
	public Pairing getPairing(){
		return this.pairing;
	}
	
	public int getFileChunkSize(){
		return this.chunkSize;
	}
	
        // to byte[]        
        public byte[] getgBytes(){
		return this.g.toBytes();
	}
	
	public byte[] getgpreBytes(){
		return this.gpre.toBytes();
	}
	
	public byte[] getkBytes(){
		return this.k.toBytes();
	}
	
	public byte[] getg_kBytes(){
		return this.g_k.toBytes();
	}
	
	public byte[] getz_kBytes(){
		return this.z_k.toBytes();
	}
	
}
