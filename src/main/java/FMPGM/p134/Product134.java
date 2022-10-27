package FMPGM.p134;

public class Product134 extends  Header{

    private String slipID;
    private String productname;
    private String quantity;
    private String price;
    private String man;
    private String sen;
    private String yen;
    private String select;
    private String weekend;

    public Product134(String customer, String company, String date, String slipid,String productname, String quantity, String price,String select) {
        super(customer, company, date);
        this.slipID = slipid;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
        this.man="";
        this.sen="";
        this.yen="";
        int priceLen =  this.price.length() -1;
        for(int i=this.price.length()-1;i>=0;i--){
            if(i<=2 && i >=0)  this.yen = this.yen + this.price.charAt(priceLen - i);
            if(i <=5 && i >=3 )  this.sen = this.sen + this.price.charAt(priceLen - i);
            if(i > 5) this.man = this.man  + this.price.charAt(priceLen - i);
        }
        this.select = select;
    }

    public String getSlipID() {
        return slipID;
    }

    public void setSlipID(String slipID) {
        this.slipID = slipID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getSen() {
        return sen;
    }

    public void setSen(String sen) {
        this.sen = sen;
    }

    public String getYen() {
        return yen;
    }

    public void setYen(String yen) {
        this.yen = yen;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }
}
