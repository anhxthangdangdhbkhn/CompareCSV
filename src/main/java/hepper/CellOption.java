package hepper;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class CellOption {
    private boolean select;
    private String insert;
    private JapaneseMode japaneseMode;
    private boolean deleteSpace;

    public CellOption(boolean select) {
        this.select = select;
    }

    public CellOption(boolean select, String insert) {
        this.select = select;
        this.insert = insert;
    }

//    public CellOption(boolean select, String insert, int convert) {
//        this.select = select;
//        this.insert = insert;
//        this.convert = convert;
//    }

    public String insertData(String data){
        if(insert.indexOf("*")>1){
            int setLength = Integer.parseInt(insert.substring(0,insert.indexOf("*")));
            if(data.length()<setLength){
                String getInsertChar = insert.substring(insert.indexOf("*")+1,insert.length());
                String add = getInsertChar;
                for(int i=0;i<setLength-data.length();i++){
                    add= add +getInsertChar;
                }
                return add +data;
            }

            return data;

        }
        if(insert.indexOf("#")>1){
            int setLength = Integer.parseInt(insert.substring(0,insert.indexOf("#")));
            if(data.length()<setLength){
                String getInsertChar = insert.substring(insert.indexOf("#")+1,insert.length());
                String add = getInsertChar;
                for(int i=0;i<setLength-data.length();i++){
                    add= add +getInsertChar;
                }

                return data+add;
            }

            return data;

        }
        return data;
    }

    public String japaneseConvert(String value){
        return JapaneseConvert.convert(value,japaneseMode);
    }
}
