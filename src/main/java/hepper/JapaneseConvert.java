package hepper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.terasoluna.gfw.common.fullhalf.DefaultFullHalf;

@Setter
@Getter
@Slf4j
public class JapaneseConvert {


    public JapaneseConvert() {
    }

    public static String convert(String data, JapaneseMode japaneseMode){
        if(japaneseMode == JapaneseMode.ZENKAKU_NOMI){
            log.info("zenkakuNomi {} ",zenkakuNomi(data));
            return zenkakuNomi(data);
        }
        else if (japaneseMode == JapaneseMode.HANKAKU_NOMI){
            log.info("hankakuNomi {}",hankakuNomi(data));
            return hankakuNomi(data);
        }
        return data;
    }

//    文字種	コード範囲
//    数字（半角）	0x30～0x39
//    英字（半角／大文字）	0x41～0x5A
//    英字（半角／小文字）	0x61～0x7A
//    数字（全角）	0xFF10～0xFF19
//    英字（全角／大文字）	0xFF21～0xFF3A
//    英字（全角／小文字）	0xFF41～0xFF5A

//    半角英数字を全角英数字に変換する
    private  String hankakuToZenkaku(String value) {
        StringBuilder sb = new StringBuilder(value);
        for (int i = 0; i <sb.length(); i++) {
            int c = (int) sb.charAt(i);
            if ((c >= 0x30 && c <= 0x39) || (c >= 0x41 && c <= 0x5A) || (c >= 0x61 && c <= 0x7A)) {
                sb.setCharAt(i, (char) (c + 0xFEE0));
            }
        }
        value = sb.toString();
        return value;
    }

    //全角英数字を半角英数字に変換する
    private   String  zenkakuToHankaku(String value) {
        StringBuilder sb = new StringBuilder(value);
        for (int i = 0; i <sb.length(); i++) {
            int c = (int) sb.charAt(i);
            if ((c >= 0xFF10 && c <= 0xFF19) || (c >= 0xFF21 && c <= 0xFF3A) || (c >= 0xFF41 && c <= 0xFF5A)) {
                sb.setCharAt(i, (char) (c - 0xFEE0));
            }
        }
        value = sb.toString();
        return value;
    }

    private static String  zenkakuNomi(String value) {
        return DefaultFullHalf.INSTANCE.toFullwidth(value);
    }
    private static String  hankakuNomi(String value) {
        return DefaultFullHalf.INSTANCE.toHalfwidth(value);
    }
}
