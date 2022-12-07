package hepper;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Integers;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class CellOption {
    private boolean select;
    private String insert;
    private boolean convert;

    public CellOption(boolean select) {
        this.select = select;
    }

    public CellOption(boolean select, String insert) {
        this.select = select;
        this.insert = insert;
    }

    public String convertData(String data){
        if(data ==null) return data;
        if(insert.indexOf("*")>1){
            int setLength = Integer.parseInt(insert.substring(0,insert.indexOf("*")));
            if(data.length()<setLength){
                String getInsertChar = insert.substring(insert.indexOf("*")+1,insert.length());
//                String str ="000000000000000000000000000000000000000000";
//                str = str.substring(0,setLength-data.length());
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
//                String str ="000000000000000000000000000000000000000000";
//                str = str.substring(0,setLength-data.length());
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
}
