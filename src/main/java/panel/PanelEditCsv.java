package panel;

import component.*;
import eenum.ECsvCharset;
import hepper.CellOption;
import eenum.JapaneseMode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Getter
@Setter
@Slf4j
public class PanelEditCsv implements KeyListener {
    private JPanel jPanelEditCsv;
    private JButton jButtonCsvSelect;
    private JButton jButtonCsvExport;
    private JButton jButtonCsvSetting;
    private JLabel jLabelFileName;
    private JFileChooser jFileChooserCSV;
    private JScrollPane jScrollSettingCsv;
    private JPanel panelSettingCsv;

    private JCheckBox jCheckSelectColumn[];
    private JTextField jTextFieldInsertOption[];
    private JComboTOption jComboTOptionOption[];
    private JCheckBox jCheckDeleteSpace[];

    private ReadCsv readCsv;
    private Map<Integer, CellOption> mapCellOption;
    private CSVSettingUI csvSettingUI;

    public PanelEditCsv() {
        this.csvSettingUI = new CSVSettingUI("CSV設定",new CSVSetting(ECsvCharset.CHARSET_SHIFT_JIS,ECsvCharset.CHARSET_SJIS));
        addControls();
        addEvents();
    }

    private void addControls() {
        jPanelEditCsv = new JPanel();
        jButtonCsvSetting = new JButton("Csv設定");
        jButtonCsvSelect = new JButton("Csv選択");
        jButtonCsvExport = new JButton("Csv出力");
        jFileChooserCSV = new JFileChooser();
        jLabelFileName = new JLabel();
        jPanelEditCsv.add(jButtonCsvSetting);
        jPanelEditCsv.add(jButtonCsvSelect);
        jPanelEditCsv.add(jButtonCsvExport);
        jPanelEditCsv.add(jLabelFileName);
        jButtonCsvExport.setVisible(false);
        jLabelFileName.setVisible(false);
    }

    private void addEvents() {
        jButtonCsvSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                readCsv = new ReadCsv(csvSettingUI.getCsvSetting());
                readCsv.readCvsByFileChooser();
                jLabelFileName.setText(readCsv.getPathFile());
                jLabelFileName.setVisible(true);
                readFileCsv();

            }
        });

        jButtonCsvSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("CSV 設定");
                csvSettingUI.showWindow();

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
                log.info("listSelected {}",listSelected);

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


                TimeZone timeZone = TimeZone.getDefault();//[1]
                Calendar calendar = Calendar.getInstance(timeZone);//[2]
                String name = calendar.get(Calendar.YEAR) +""+ (calendar.get(Calendar.MONTH)+1) +""+ calendar.get(Calendar.DAY_OF_MONTH)+"-"
                        +calendar.get(Calendar.HOUR_OF_DAY)+"-"+calendar.get(Calendar.MINUTE)+"-"+calendar.get(Calendar.SECOND);

               // WriteCSV writeCSV = new WriteCSV(name);
                WriteCSV writeCSV = new WriteCSV(name,mapCellOption,csvSettingUI.getCsvSetting());
                writeCSV.write(vectorHeader,vectorCsvData);
            }
        });

        jButtonCsvSelect.setDropTarget(new DropTarget(){
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                List<File> droppedFiles = null;
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    droppedFiles = (List<File>)
                            dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if(droppedFiles.size()>1){
                        JOptionPane.showInternalMessageDialog(null,
                                "1個ファイルのみを移動してください。", "OK!", JOptionPane.ERROR_MESSAGE);
                    }else {
                        File file = droppedFiles.get(0);
                        readCsv = new ReadCsv(csvSettingUI.getCsvSetting());
                        readCsv.readCvsByFileDrag(file.getPath());
                        readFileCsv();
                        jLabelFileName.setText(file.getPath());
                        jLabelFileName.setVisible(true);
                        log.info("File Path: {}",file.getPath());
                    }

                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });



    }

    void readFileCsv(){

        int csvCols = readCsv.getVectorHeader().size();

        if(panelSettingCsv != null) panelSettingCsv = null;



        panelSettingCsv = new JPanel(new GridLayout(5,csvCols));
        jCheckSelectColumn = new JCheckBox[csvCols];
        jTextFieldInsertOption = new JTextField[csvCols];
        jComboTOptionOption = new JComboTOption[csvCols];
        jCheckDeleteSpace = new JCheckBox[csvCols];

        TitledBorder panelSettingCsvBorder =
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE,2),"CSV出力設定",TitledBorder.LEFT, TitledBorder.TOP);
//        if(jScrollSettingCsv !=null){
//            Component[] components = jScrollSettingCsv.getComponents();
//            jScrollSettingCsv.remove(panelSettingCsv);
//            log.info("components {}",components);
//        }

        if(jPanelEditCsv!=null){
            Component component[] = jPanelEditCsv.getComponents();
            for (Component components: component) {
                if (components.equals(jScrollSettingCsv)) {
                    jPanelEditCsv.remove(jScrollSettingCsv);
                }
            }

        }

        if(readCsv.getMapCellMaxLength().size()>0){
            jScrollSettingCsv = new JScrollPane(panelSettingCsv,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollSettingCsv.setPreferredSize(new Dimension(1400,180));
            jScrollSettingCsv.setBorder(panelSettingCsvBorder);


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



            jPanelEditCsv.add(jScrollSettingCsv);
            jButtonCsvExport.setVisible(true);
        }





    }

    @Override
    public void keyTyped(KeyEvent e) {
        log.info("ENTER");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("ENTER");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        log.info("ENTER");
        System.out.println("ENTER");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        log.info("ENTER");
        System.out.println("ENTER");

    }


}
