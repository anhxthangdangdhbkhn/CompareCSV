package component;

import hepper.CellOption;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

@Getter
@Setter
@Slf4j
public class PanelEditCsv {
    private JPanel jPanelEditCsv;
    private JButton jButtonCsvSelect;
    private JButton jButtonCsvExport;
    private  JFileChooser jFileChooserCSV;
    private GridLayout exportSetting;
    private JCheckBox jCheckSelectColumn[];
    private JCheckBox jCheckConvertColumn[];
    private JTextField jTextFieldInsertOption[];

    private ReadCsv readCsv;
    private Map<Integer, CellOption> mapCellOption;

    public PanelEditCsv() {
        this.jPanelEditCsv = new JPanel();
        this.jButtonCsvSelect = new JButton("Csv選択");
        this.jButtonCsvExport = new JButton("Csv出力");
        this.jFileChooserCSV = new JFileChooser();
        this.jPanelEditCsv.add(jButtonCsvSelect);
        this.jPanelEditCsv.add(jButtonCsvExport);
        this.jButtonCsvExport.setVisible(false);
        addEvents();
    }

    private void addEvents() {
        jButtonCsvSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCsv = new ReadCsv();
                readCsv.readCvs();
                log.info("jButtonEditCsvSelect click");
                log.info("Header csv {}",readCsv.getVectorHeader());


                int csvCols = readCsv.getVectorHeader().size();
                exportSetting = new GridLayout(4,csvCols);

                JPanel panel = new JPanel();
                panel.setLayout(exportSetting);


                jCheckSelectColumn = new JCheckBox[csvCols];
                jCheckConvertColumn = new JCheckBox[csvCols];
                jTextFieldInsertOption = new JTextField[csvCols];


                log.info("Columns {}",csvCols);
                for (int i=0;i<csvCols;i++){
                    jCheckSelectColumn[i] = new JCheckBox(readCsv.getVectorHeader().get(i),true);
                    panel.add(jCheckSelectColumn[i]);
                }
                readCsv.getMapCellMaxLength().forEach((k,v)->{
                    panel.add(new JLabel(v.toString()));
                });
                for (int i=0;i<csvCols;i++){
                    jTextFieldInsertOption[i] = new JTextField(1);
                    panel.add(jTextFieldInsertOption[i]);
                }

                for (int i=0;i<csvCols;i++){
                    jCheckConvertColumn[i] = new JCheckBox("H",true);
                    panel.add(jCheckConvertColumn[i]);
                }


                jPanelEditCsv.add(panel);
                jButtonCsvExport.setVisible(true);

            }
        });

        jButtonCsvExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                Vector<String> vectorHeader = new Vector<>();
                Vector<Vector> vectorCsvData = readCsv.getVectorCsvData();




                mapCellOption = new HashMap<>();
                int csvCols = readCsv.getVectorHeader().size();

                // select cells
                for(int i =0;i<csvCols;i++){
                   mapCellOption.put(i,new CellOption(jCheckSelectColumn[i].isSelected(),jTextFieldInsertOption[i].getText(),jCheckConvertColumn[i].isSelected()));
                }

                List<Integer> listSelected = new ArrayList<>();
                for(int i =0;i<jCheckSelectColumn.length;i++){
                    if(jCheckSelectColumn[i].isSelected()){
                        listSelected.add(i);
                    }
                }

                for(int i=0;i<vectorCsvData.size();i++){
                    Vector<String> vector = vectorCsvData.get(i);
                    Vector<String> newVector = new Vector<>();
                    for(int j=0;j<listSelected.size();j++){
                        CellOption cellOption = mapCellOption.get(listSelected.get(j));
                        String data =null;
                        if(mapCellOption.containsKey(j)){
                            if(cellOption.getInsert()!=null) {
                                data = cellOption.convertData(vector.get(listSelected.get(j)));
                            }else{
                                data =vector.get(listSelected.get(j));
                            }
                        }

                        newVector.add(data);
                    }
                    vectorCsvData.set(i,newVector);
                }


//                mapCellOption.forEach((k,d)->
//                {
//                    log.info("Key: " + k +" data: " +d.toString());
//                });

                //log.info("Get max {}",readCsv.getMapCellMaxLength());



                WriteCSV writeCSV = new WriteCSV("thang.csv");
                writeCSV.write(vectorHeader,vectorCsvData);
            }
        });
    }
}
