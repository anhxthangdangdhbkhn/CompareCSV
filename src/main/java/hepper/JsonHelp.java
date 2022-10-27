package hepper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class JsonHelp {

    public JSONArray jsonArray;

    public JsonHelp(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray ConvertCSVJToJson(String path){
        try {
            File file = new File(path);
            InputStreamReader ir = new InputStreamReader(new FileInputStream(file),"SJIS");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);

            for (CSVRecord record : records) {
                JSONObject json =  new JSONObject(record.toMap());
                jsonArray.add(json);
            }
            return this.jsonArray;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.jsonArray;
    }

    public JSONArray SearchJson(String Wdata,String Snotdata){
        JSONArray filtedArray = new JSONArray();
        for(Object json:this.jsonArray){
            JSONObject jsonObject = new JSONObject((Map) json);
            // ID洗い出し
            if(jsonObject.get("W").toString().equals(Wdata) && jsonObject.get("S").toString().equals(Snotdata)==false){
                filtedArray.add(jsonObject);
            }
        }
        return filtedArray;
    }

    public void SortJson(JSONArray jsonArr,String CELL){
        Collections.sort(jsonArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String str1 = new String();
                String str2 = new String();
                str1 = (String)o1.get(CELL);
                str2 = (String)o2.get(CELL);
                return str1.compareTo(str2);
            }
        });
    }



}
