import component.ReadCsv;
import component.MyCompareView;
import lombok.extern.slf4j.Slf4j;
import ui.CompareCVS;
import ui.FMPGMApp;

import java.util.Vector;


@Slf4j
public class App {
    public static void main(String[] args){
//        FMPGMApp fmpgmApp = new FMPGMApp("FM指定納品書発行PGM");
//        fmpgmApp.showWindown();


        CompareCVS compareCVS = new CompareCVS("CompareCSV");
        compareCVS.showWindow();



//        ReadCsv readCsv = new ReadCsv();
//        readCsv.readCvs();
//
//        MyCompareView myCompareView = new MyCompareView("Compare");
//        myCompareView.showWindow();
//
//        myCompareView.addDocument(readCsv.getVectorHeader());
//
//        Vector<Vector> vectorCsvFile = readCsv.getVectorCsvData();
//
//        for(int i=0;i< vectorCsvFile.size();i++){
//
//            Vector vector = vectorCsvFile.get(i);
//            String row ="<tr>";
//
//            for(int j =0;j<vector.size();j++){
//                String cell = "<td>" + vector.get(j) +"</td>";
//                row = row + cell;
//            }
//
//            row =row + "</tr>";
//
//            myCompareView.addAlineTable(row);
//            if(i>2) break;
//        }
//        myCompareView.tableFinish();
//        log.info("Html {}",myCompareView.toString());
//




//        myCompareView.addVectorHeader(compareHtmlView.getVectorHeader());
//
//
//        Vector<Vector> vectorCsvFile = compareHtmlView.getVectorCsvData();
//
//        for(int i=0;i< vectorCsvFile.size();i++){
//
//            Vector vector = vectorCsvFile.get(i);
//            String row ="<tr>";
//
//            for(int j =0;j<vector.size();j++){
//                String cell = "<td>" + vector.get(j) +"</td>";
//                row = row + cell;
//            }
//
//            row =row + "</tr>";
//
//            myCompareView.addRow(row);
//            if(i>10) break;
//        }
//
//        myCompareView.tableFinish();
//        myCompareView.addAline();
    }
}
