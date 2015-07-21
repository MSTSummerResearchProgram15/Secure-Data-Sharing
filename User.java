import it.unisa.dia.gas.jpbc.Element;

//This class allows easy access to user keys
public class User {
    private Element pk, sk, isk, rk;
    private int userID;

    public User(int userID, Element pk, Element sk, Element isk, Element rk) {
        this.userID = userID;
        this.pk = pk;
        this.sk = sk;
        this.isk = isk;
        this.rk = rk;
    }
        
    public User(int userID){
        this.userID = userID;
    }
    
    public User(){}
    
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
    
    public byte[] getPKBytes(){
            return this.pk.toBytes();
    }

    public byte[] getSKBytes(){
            return this.sk.toBytes();
    }

    public byte[] getISKBytes(){
            return this.isk.toBytes();
    }
    
    public int getUserID(){
        return this.userID;
    }
}
