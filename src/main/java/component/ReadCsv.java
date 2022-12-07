package component;

import hepper.ABC;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.io.*;
import java.util.*;

@Getter
@Setter
@Slf4j
public class ReadCsv {
    private JFileChooser jFileChooser;
    private int resultFile;
    private File file;
    private String pathFile;
    private Vector<String> vectorHeader = new Vector<>();
    private Vector<Vector> vectorCsvData = new Vector<>();
    private Map<String,Integer> mapCellMaxLength;


    public void readCvs(){

        resultFile = jFileChooser.showOpenDialog(null);
        if(resultFile == JFileChooser.APPROVE_OPTION){
            pathFile = jFileChooser.getSelectedFile().getPath();

            if(pathFile.contains(".csv") || pathFile.contains(".CSV")){
                file = new File(pathFile);
                InputStreamReader ir = null;
                try {
                    ir = new InputStreamReader(new FileInputStream(file),"SJIS");
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);

                    CSVRecord r = records.iterator().next();
                    Set<String> setHeader  = r.toMap().keySet();
                    setHeader.forEach(s->{
                        vectorHeader.add(s);
                    });

                    mapCellMaxLength = new HashMap<>();

                    for(CSVRecord record:records){
                        Vector<String> line = new Vector<>();
                        record.toMap().forEach((k,v)->{

                            if(mapCellMaxLength.containsKey(k)){
                                Integer max = v.length();
                                Integer current = mapCellMaxLength.get(k);
                                if(max>current){
                                    mapCellMaxLength.put(k,max);
                                }

                            }else {
                                mapCellMaxLength.put(k,v.length());
                            }


                            line.add(v);
                        });
                        vectorCsvData.add(line);
                    }
//
//                    mapCellMaxLength.forEach((k,v)->{
//                    log.info("Column: " + k +" Length: " +v);
//                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null,
                        "CSVファイルを選択してください",
                        "CSVインポート",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null,
                    "何も選択されてない",
                    "CSVインポート",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ReadCsv() {
        jFileChooser = new JFileChooser();
    }

}
