package panel;

import component.JComboTOption;
import component.ReadCsv;
import component.WriteCSV;
import hepper.CellOption;
import hepper.JapaneseConvert;
import hepper.JapaneseMode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
   // private JScrollPane scrollPane;

    private JCheckBox jCheckSelectColumn[];
    private JTextField jTextFieldInsertOption[];
    private JComboTOption jComboTOptionOption[];
    private JCheckBox jCheckDeleteSpace[];

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
      //  this.scrollPane = new JScrollPane(jPanelEditCsv);
//        this.scrollPane.setBounds(50, 30, 300, 50);
//        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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

                JScrollPane jScrollPane = new JScrollPane();
                JPanel panelSettingCsv = new JPanel(new GridLayout(5,csvCols));

                jScrollPane.add(panelSettingCsv);


                jCheckSelectColumn = new JCheckBox[csvCols];
                jTextFieldInsertOption = new JTextField[csvCols];
                jComboTOptionOption = new JComboTOption[csvCols];
                jCheckDeleteSpace = new JCheckBox[csvCols];


                log.info("Columns {}",csvCols);
               // panelSettingCsv.add(new JTextField("Columns"));
                for (int i=0;i<csvCols;i++){
                    jCheckSelectColumn[i] = new JCheckBox(readCsv.getVectorHeader().get(i),true);
                    panelSettingCsv.add(jCheckSelectColumn[i]);
                }

               // panelSettingCsv.add(new JTextField("Columnの最大長さ"));
                readCsv.getMapCellMaxLength().forEach((k,v)->{
                    panelSettingCsv.add(new JLabel(v.toString()));
                });

               // panelSettingCsv.add(new JTextField("文字追加"));
                for (int i=0;i<csvCols;i++){
                    jTextFieldInsertOption[i] = new JTextField(1);
                    panelSettingCsv.add(jTextFieldInsertOption[i]);
                }

                //panelSettingCsv.add(new JTextField("スペース削除"));
                 for (int i=0;i<csvCols;i++){
                    jCheckDeleteSpace[i] = new JCheckBox(readCsv.getVectorHeader().get(i),false);
                    panelSettingCsv.add(jCheckDeleteSpace[i]);
                }

               // panelSettingCsv.add(new JTextField("半角や全角交換"));
                for (int i=0;i<csvCols;i++){
                    jComboTOptionOption[i] = new JComboTOption();
                    panelSettingCsv.add(jComboTOptionOption[i].getJComboBox());
                }


                jPanelEditCsv.add(jScrollPane);
                jPanelEditCsv.add(panelSettingCsv);
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
                    CellOption cellOption = new CellOption(jCheckSelectColumn[i].isSelected(),jTextFieldInsertOption[i].getText(), jComboTOptionOption[i].getJapaneseMode(),jCheckDeleteSpace[i].isSelected());
                   mapCellOption.put(i,cellOption);
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
                                data = cellOption.insertData(vector.get(listSelected.get(j)));
                            }else{
                                data =vector.get(listSelected.get(j));
                            }

                            if(cellOption.getJapaneseMode()== JapaneseMode.HANKAKU_NOMI || cellOption.getJapaneseMode()== JapaneseMode.ZENKAKU_NOMI){
                                //log.info("Convert: {}",data);
                               data = cellOption.japaneseConvert(data);
                                //log.info("Convert after: {}",data);
                            };

                        }

                        newVector.add(data);
                    }
                    vectorCsvData.set(i,newVector);
                }


                mapCellOption.forEach((k,d)->
                {
                    log.info("Key: " + k +" data: " +d.toString());
                });

                //log.info("Get max {}",readCsv.getMapCellMaxLength());



                WriteCSV writeCSV = new WriteCSV("thang.csv");
                writeCSV.write(vectorHeader,vectorCsvData);
            }
        });
    }
}
