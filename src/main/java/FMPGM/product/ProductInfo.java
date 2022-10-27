package FMPGM.product;

public class ProductInfo {
    private String denpyoubangou;
    private String ruuto;
    private String sokyakukouto;
    private String ID;
    private String shouhinkoudo;
    private String suuryou;
    private String tanka;
    private String kingaku;
    private String shouhinme;
    private String hidzuke;
    private String kokyakumei1;
    private String kokyakumei2;
    private String shaten;
    private String torihikisaki;
    private String bunruibumon;
    private String zeiritsu;

    public ProductInfo() {
    }

    public ProductInfo(String denpyoubangou, String ruuto, String sokyakukouto, String ID, String shouhinkoudo, String suuryou, String tanka, String kingaku, String shouhinme, String hidzuke, String kokyakumei1, String kokyakumei2, String shaten, String torihikisaki, String bunruibumon, String zeiritsu) {
        this.denpyoubangou = denpyoubangou;
        this.ruuto = ruuto;
        this.sokyakukouto = sokyakukouto;
        this.ID = ID;
        this.shouhinkoudo = shouhinkoudo;
        this.suuryou = suuryou;
        this.tanka = tanka;
        this.kingaku = kingaku;
        this.shouhinme = shouhinme;
        this.hidzuke = hidzuke;
        this.kokyakumei1 = kokyakumei1;
        this.kokyakumei2 = kokyakumei2;
        this.shaten = shaten;
        this.torihikisaki = torihikisaki;
        this.bunruibumon = bunruibumon;
        this.zeiritsu = zeiritsu;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "denpyoubangou='" + denpyoubangou + '\'' +
                ", ruuto='" + ruuto + '\'' +
                ", sokyakukouto='" + sokyakukouto + '\'' +
                ", ID='" + ID + '\'' +
                ", shouhinkoudo='" + shouhinkoudo + '\'' +
                ", suuryou='" + suuryou + '\'' +
                ", tanka='" + tanka + '\'' +
                ", kingaku='" + kingaku + '\'' +
                ", shouhinme='" + shouhinme + '\'' +
                ", hidzuke='" + hidzuke + '\'' +
                ", kokyakumei1='" + kokyakumei1 + '\'' +
                ", kokyakumei2='" + kokyakumei2 + '\'' +
                ", shaten='" + shaten + '\'' +
                ", torihikisaki='" + torihikisaki + '\'' +
                ", bunruibumon='" + bunruibumon + '\'' +
                ", zeiritsu='" + zeiritsu + '\'' +
                '}';
    }

    public String getDenpyoubangou() {
        return denpyoubangou;
    }

    public void setDenpyoubangou(String denpyoubangou) {
        this.denpyoubangou = denpyoubangou;
    }

    public String getRuuto() {
        return ruuto;
    }

    public void setRuuto(String ruuto) {
        this.ruuto = ruuto;
    }

    public String getSokyakukouto() {
        return sokyakukouto;
    }

    public void setSokyakukouto(String sokyakukouto) {
        this.sokyakukouto = sokyakukouto;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getShouhinkoudo() {
        return shouhinkoudo;
    }

    public void setShouhinkoudo(String shouhinkoudo) {
        this.shouhinkoudo = shouhinkoudo;
    }

    public String getSuuryou() {
        return suuryou;
    }

    public void setSuuryou(String suuryou) {
        this.suuryou = suuryou;
    }

    public String getTanka() {
        return tanka;
    }

    public void setTanka(String tanka) {
        this.tanka = tanka;
    }

    public String getKingaku() {
        return kingaku;
    }

    public void setKingaku(String kingaku) {
        this.kingaku = kingaku;
    }

    public String getShouhinme() {
        return shouhinme;
    }

    public void setShouhinme(String shouhinme) {
        this.shouhinme = shouhinme;
    }

    public String getHidzuke() {
        return hidzuke;
    }

    public void setHidzuke(String hidzuke) {
        this.hidzuke = hidzuke;
    }

    public String getKokyakumei1() {
        return kokyakumei1;
    }

    public void setKokyakumei1(String kokyakumei1) {
        this.kokyakumei1 = kokyakumei1;
    }

    public String getKokyakumei2() {
        return kokyakumei2;
    }

    public void setKokyakumei2(String kokyakumei2) {
        this.kokyakumei2 = kokyakumei2;
    }

    public String getShaten() {
        return shaten;
    }

    public void setShaten(String shaten) {
        this.shaten = shaten;
    }

    public String getTorihikisaki() {
        return torihikisaki;
    }

    public void setTorihikisaki(String torihikisaki) {
        this.torihikisaki = torihikisaki;
    }

    public String getBunruibumon() {
        return bunruibumon;
    }

    public void setBunruibumon(String bunruibumon) {
        this.bunruibumon = bunruibumon;
    }

    public String getZeiritsu() {
        return zeiritsu;
    }

    public void setZeiritsu(String zeiritsu) {
        this.zeiritsu = zeiritsu;
    }
}

