import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;


public class Params {
	private Field zr, g1, gt;
	private Element pk_a, sk_a, isk_a, isk_b, pk_b, sk_b, ownersk_a, g, k, g_k, z_k, e, rka_b;
	private ElementPowPreProcessing gpre;
	private Pairing pairing;
	
	public void setPairing(Pairing pairing){
		this.pairing = pairing;
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
	
	//********Keys*******//
	public void setOwnerPK(Element pk_a){
		this.pk_a = pk_a;
	}
	
	public void setOwnerSK(Element sk_a){
		this.sk_a = sk_a;
	}
	
	public void setOwnerISK(Element isk_a){
		this.isk_a = isk_a;
	}
	
	public void setUserPK(Element pk_b){
		this.pk_b = pk_b;
	}
	
	public void setUserSK(Element sk_b){
		this.sk_b = sk_b;
	}
	
	public void setUserISK(Element isk_b){
		this.isk_b = isk_b;
	}
	
	public void setReEncryptionKey(Element rka_b){
		this.rka_b = rka_b;
	}
	
	public Element getOwnerPK(){
		return this.pk_a;
	}
	
	public Element getOwnerSK(){
		return this.sk_a;
	}
	
	public Element getOwnerISK(){
		return this.isk_a;
	}
	
	public Element getUserPK(){
		return this.pk_b;
	}
	
	public Element getUserSK(){
		return this.sk_b;
	}
	
	public Element getUserISK(){
		return this.isk_b;
	}
	
	public Element getReEncryptionKey(){
		return this.rka_b;
	}
}
