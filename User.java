import it.unisa.dia.gas.jpbc.Element;


//This class allows easy access to user keys
public class User {
	private Element pk, sk, isk, rk;
	
	public void setPK(Element pk){
		this.pk = pk;
	}
	
	public void setSK(Element sk){
		this.sk = sk;
	}
	
	public void setISK(Element isk){
		this.isk = isk;
	}
	
	public void setReEncryptKey(Element rk){
		this.rk = rk;
	}
	
	public Element getPK(){
		return this.pk;
	}
	
	public Element getSK(){
		return this.sk;
	}
	
	public Element getISK(){
		return this.isk;
	}
	
	public Element getReEncryptKey(){
		return this.rk;
	}
}
