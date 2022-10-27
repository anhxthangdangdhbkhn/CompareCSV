package test;

import FMPGM.product.ProductInfo;
import hepper.JsonHelp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonHelpTest {
    public static void main(String[] args){
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        String csvFile = "C:\\Users\\jsys04\\Documents\\ThangDD\\Project\\FM定納品発生PGM\\DBBFFFFP.csv";


        JsonHelp jsonHelp = new JsonHelp(jsonArray);

        jsonHelp.ConvertCSVJToJson(csvFile);
        jsonArray1 = jsonHelp.SearchJson("134","0");
        jsonHelp.SortJson(jsonArray1,"G");



        // create a list product
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        for (Object obj:jsonArray1){
            JSONObject jo = (JSONObject) obj;
            ProductInfo productInfo = new ProductInfo(
                    jo.get("C").toString(),
                    jo.get("F").toString(),
                    jo.get("G").toString(),
                    jo.get("W").toString(),
                    jo.get("P").toString(),
                    jo.get("Q").toString(),
                    jo.get("R").toString(),
                    jo.get("S").toString(),
                    jo.get("U").toString(),
                    jo.get("Z").toString(),
                    jo.get("AG").toString(),
                    jo.get("AH").toString(),
                    jo.get("AI").toString(),
                    jo.get("AJ").toString(),
                    jo.get("AK").toString(),
                    "a"
            );
            productInfoList.add(productInfo);
        }


        for(Object obj:productInfoList){
            System.out.println(obj.toString());
        }



    }
}
