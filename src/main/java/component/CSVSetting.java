package component;

import eenum.ECsvCharset;
import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

@Setter
@Getter
public class CSVSetting {
    private ECsvCharset inputECharset;
    private ECsvCharset outECharset;
    private String ipCharsetName;
    private String opCharsetName;
    private Vector<String> vectorCharset;

    public CSVSetting() {
        vectorCharset = new Vector<>();
        vectorCharset.add("SJIS");
        vectorCharset.add("UTF-8");
        vectorCharset.add("UTF-16");
        vectorCharset.add("Shift-JIS");
        this.inputECharset = ECsvCharset.CHARSET_SJIS;
        this.outECharset = ECsvCharset.CHARSET_SJIS;
        this.opCharsetName = "SJIS";
        this.ipCharsetName = "SJIS";
    }

    public CSVSetting(ECsvCharset inputCharset, ECsvCharset outCharset) {
        vectorCharset = new Vector<>();
        vectorCharset.add("SJIS");
        vectorCharset.add("UTF-8");
        vectorCharset.add("UTF-16");
        vectorCharset.add("Shift-JIS");
        this.inputECharset = inputCharset;
        this.outECharset = outCharset;
        if(inputCharset == ECsvCharset.CHARSET_UTF16){
            ipCharsetName = "UTF-16";
        }else if(inputCharset == ECsvCharset.CHARSET_UTF8){
            ipCharsetName = "UTF-8";
        }else if(inputCharset == ECsvCharset.CHARSET_SHIFT_JIS){
            ipCharsetName = "Shift-JIS";
        }else {ipCharsetName = "SJIS";}

        if(outCharset == ECsvCharset.CHARSET_UTF16){
            opCharsetName = "UTF-16";
        }else if(outCharset == ECsvCharset.CHARSET_UTF8){
            opCharsetName = "UTF-8";
        }else if(outCharset == ECsvCharset.CHARSET_SHIFT_JIS){
            opCharsetName = "Shift-JIS";
        }else {opCharsetName = "SJIS";}
    }

}
