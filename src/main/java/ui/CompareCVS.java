package ui;

import component.CSVView;
import component.CompareView;
import panel.PanelEditCsv;
import hepper.ABC;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class CompareCVS extends JFrame {

    public CompareCVS(String title) throws HeadlessException {
        super(title);
        addControls();
        addEvents();
    }



    JTabbedPane jTabbedPane;
    JButton jButtonNewCsv;

    JButton jButtonImportNewCsv;
    JButton jButtonImportOldCsv;
    JButton jButtonCompareCsv;
    JComboBox<String> jComboBoxMode;
    JComboBox<String> jComboBoxKey;
    JButton jButtonCompareView;


    JButton jButtonClearCsv;

    JButton jButtonPreview;
    JButton jButtonLocal;
    JComboBox jComboBoxCustomer;

    JPanel jPanelCSV;





    CSVView csvViewNew;
    Vector<String> vectorNewHeader ;
    Vector<Vector> vectorNewCsv;


    CSVView csvViewOld ;
    Vector<String> vectorOldHeader;
    Vector<Vector> vectorOldCsv;
    Vector<String> vectorChange;


    JPanel panelCsvCompare = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();



    int[] rowStatus ={0,0,0,0};
    private boolean compareViewDetails = false;

    Map<String,Vector> mapNewBase = new LinkedHashMap<>();
    Map<String,Vector> mapOldBase = new LinkedHashMap<>();






    private void addControls() {

        Container container = getContentPane();
        jTabbedPane = new JTabbedPane();
        container.add(jTabbedPane);

        jPanelCSV = new JPanel();
        PanelEditCsv panelEditCsv = new PanelEditCsv();
        JPanel jPanelCsv = new JPanel();
        JPanel jPanelHelp = new JPanel();

        jTabbedPane.add(jPanelCSV,"Csv比較");
        jTabbedPane.add(panelEditCsv.getJPanelEditCsv(),"Csv処理");
        jTabbedPane.add(jPanelCsv,"ツール設定");
        jTabbedPane.add(jPanelHelp,"使用方法説明");



        JPanel jPanelHelpLayout = new JPanel();
        jPanelHelpLayout.setLayout(new BoxLayout(jPanelHelpLayout,BoxLayout.Y_AXIS));


        jButtonClearCsv = new JButton("Clear CSV");

        jButtonImportOldCsv = new JButton("古いCSV選択");
        jButtonImportNewCsv = new JButton("新規CSV選択");
        jButtonCompareCsv  = new JButton("比較スタート");
        jComboBoxKey = new JComboBox<>();
        jComboBoxMode = new JComboBox<>();
        jButtonCompareView = new JButton("View");

        jComboBoxMode.addItem("---");
        jComboBoxMode.addItem("new");
        jComboBoxMode.addItem("old");
        jComboBoxMode.addItem("change");
        jComboBoxMode.addItem("delete");

        jPanelCsv.add(jButtonClearCsv);

        jButtonNewCsv= new JButton("Import New");

        jPanelCSV.add(jButtonImportOldCsv);
        jPanelCSV.add(jButtonImportNewCsv);
        jPanelCSV.add(jComboBoxKey);
        jPanelCSV.add(jButtonCompareCsv);

        jPanelCSV.add(jComboBoxMode);
        jPanelCSV.add(jButtonCompareView);

        jButtonCompareCsv.setVisible(false);
        jComboBoxMode.setVisible(false);
        jComboBoxKey.setVisible(false);
        jButtonCompareView.setVisible(false);




        jButtonPreview= new JButton("Preview");
        jButtonLocal= new JButton("Get data");
        jComboBoxCustomer = new JComboBox();






        jPanelCSV.add(jComboBoxCustomer);
        jComboBoxCustomer.setVisible(false);


        GridBagLayout layout = new GridBagLayout();
        panelCsvCompare.setLayout(layout);

        vectorNewCsv = new Vector<>();
        vectorOldCsv = new Vector<>();

        csvViewNew = new CSVView();







    }

    private void addEvents() {

        chooseOldFileCsv();
        chooseNewCsv();
        CsvCompare();
        comboBoxFilter();
        selectComboKey();
        compareView();



    }

    private void comboBoxFilter(){
        jComboBoxMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mode = jComboBoxMode.getSelectedIndex();
                jButtonCompareView.setVisible(false);
                compareViewDetails = false;
                if(mode>=0){
                    if(mode==0){
                        csvViewNew.getRowSorter().setRowFilter(null);
                        csvViewOld.getRowSorter().setRowFilter(null);
                    }
                    if(mode==1){
//                        csvViewNew.getRowSorter().setRowFilter(null);
//                        csvViewOld.getRowSorter().setRowFilter(null);
                        csvViewNew.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "new",csvViewNew.getModel().getColumnCount()-1));
                        csvViewOld.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "new",csvViewNew.getModel().getColumnCount()-1));
                    }
                    if(mode==2){
//                        csvViewNew.getRowSorter().setRowFilter(null);
//                        csvViewOld.getRowSorter().setRowFilter(null);
                        csvViewOld.getjScrollPane().getVerticalScrollBar().setModel(csvViewNew.getjScrollPane().getVerticalScrollBar().getModel());
                        csvViewNew.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "old",csvViewNew.getModel().getColumnCount()-1));
                        csvViewOld.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "old",csvViewNew.getModel().getColumnCount()-1));
                       // System.out.println("Get index: "+ jComboBoxMode.getSelectedIndex());
                    }
                    if(mode==3){
                        jButtonCompareView.setVisible(true);
                        compareViewDetails = true;
//                        csvViewNew.getRowSorter().setRowFilter(null);
//                        csvViewOld.getRowSorter().setRowFilter(null);
                        csvViewOld.getjScrollPane().getVerticalScrollBar().setModel(csvViewNew.getjScrollPane().getVerticalScrollBar().getModel());
                        csvViewNew.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "change",csvViewNew.getModel().getColumnCount()-1));
                        csvViewOld.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "change",csvViewNew.getModel().getColumnCount()-1));
                        // System.out.println("Get index: "+ jComboBoxMode.getSelectedIndex());
                    }

                    if(mode==4){
//                        csvViewNew.getRowSorter().setRowFilter(null);
//                        csvViewOld.getRowSorter().setRowFilter(null);
                       // csvViewNew.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "change",csvViewNew.getModel().getColumnCount()-1));
                        csvViewOld.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "delete",csvViewNew.getModel().getColumnCount()-1));
                        csvViewNew.getRowSorter().setRowFilter(RowFilter.regexFilter("(?i)" + "delete",csvViewNew.getModel().getColumnCount()-1));
                        // System.out.println("Get index: "+ jComboBoxMode.getSelectedIndex());
                    }

                }
            }
        });
    }

    public void showWindow(){
        this.setSize(900,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void chooseOldFileCsv(){
        JFileChooser jFileChooserOldCSV = new JFileChooser();
        jButtonImportOldCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int returnVal = jFileChooserOldCSV.showOpenDialog(jPanelCSV);




                if(returnVal == JFileChooser.APPROVE_OPTION){

                    File f = jFileChooserOldCSV.getSelectedFile();
                    String stringPath = f.getPath();
                    System.out.println(stringPath);

                    if(stringPath.contains(".csv") || stringPath.contains(".CSV")){
                        try {
                            File file = new File(stringPath);
                            InputStreamReader ir = new InputStreamReader(new FileInputStream(file),"SJIS");
                            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);

                            int input= JOptionPane.showConfirmDialog(null, "インポートですか");



                            vectorOldHeader = new Vector<>();

                            CSVRecord r = records.iterator().next();
                            Set<String> setHeader  = r.toMap().keySet();
                            setHeader.forEach(s->{
                                vectorOldHeader.add(s);
                            });
                            vectorOldHeader.add("Check");



                            if(input==0){

                                for(CSVRecord record:records){
                                    Vector line = new Vector<>();
                                    record.toMap().forEach((k,v)->{
                                        line.add(v);
                                    });
                                    line.add("STATUS");
                                    vectorOldCsv.add(line);
                                }


                                csvViewOld = new CSVView();
                                csvViewOld.getModel().setDataVector(vectorOldCsv, vectorOldHeader);
                                csvViewOld.optionViewsInit();

                                jPanelCSV.add(panelCsvCompare);
                                gbc.fill = GridBagConstraints.HORIZONTAL;
                                gbc.gridx = 0;
                                gbc.gridy = 0;
                                panelCsvCompare.add(csvViewOld.getjScrollPane(), gbc);


                            }
                            ir.close();
                            JOptionPane.showConfirmDialog(null,
                                    "古い読み込み完了", "OK!", JOptionPane.DEFAULT_OPTION);
                        } catch (IOException ee) {
                            ee.printStackTrace();
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
        });

    }

    private void chooseNewCsv(){
        JFileChooser jFileChooserOldCSV = new JFileChooser();
        jButtonImportNewCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int returnVal = jFileChooserOldCSV.showOpenDialog(jPanelCSV);




                if(returnVal == JFileChooser.APPROVE_OPTION){

                    File f = jFileChooserOldCSV.getSelectedFile();
                    String stringPath = f.getPath();

                    if(stringPath.contains(".csv") || stringPath.contains(".CSV")){
                        try {
                            File file = new File(stringPath);
                            InputStreamReader ir = new InputStreamReader(new FileInputStream(file),"SJIS");
                            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);

                            int input= JOptionPane.showConfirmDialog(null, "インポートですか");

                            if(input==0){

                                vectorNewHeader = new Vector<>();
                                CSVRecord r = records.iterator().next();
                                Set<String> setHeader  = r.toMap().keySet();
                                setHeader.forEach(s->{
                                    vectorNewHeader.add(s);
                                });
                                vectorNewHeader.add("Check");


                                for(CSVRecord record:records){
                                    Vector<String> line = new Vector<>();
                                    record.toMap().forEach((k,v)->{
                                        line.add(v);
                                    });
                                    line.add("STATUS");
                                    vectorNewCsv.add(line);
                                }
                                ir.close();

                                for(int i=0;i<vectorNewHeader.size()-1;i++){
                                    jComboBoxKey.addItem(vectorNewHeader.get(i));
                                }

                                jButtonCompareCsv.setVisible(true);
                                jComboBoxKey.setVisible(true);


                                gbc.gridx = 1;
                                gbc.gridy = 0;
                                panelCsvCompare.add(csvViewNew.getjScrollPane(), gbc);
                                csvViewNew.getModel().setDataVector(vectorNewCsv,vectorNewHeader);
                                csvViewNew.optionViewsInit();


                            }

                            JOptionPane.showConfirmDialog(null,
                                    "新規読み込み完了", "OK!", JOptionPane.DEFAULT_OPTION);
                        } catch (IOException ee) {
                            ee.printStackTrace();
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
        });

    }

    private void CsvCompare(){

        jButtonCompareCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                int keyColumn = csvViewNew.getKeyColumn();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                System.out.println(dtf.format(LocalDateTime.now()));



                if(checkCompareColumn() && checkKeyColumn()) {

                    Map<String,Vector> mapNewSelCheckBase = new LinkedHashMap<>();
                    Map<String,Vector> mapOldSelCheckBase = new LinkedHashMap<>();

//                    Map<String,Vector> mapNewSelCheckResult = new LinkedHashMap<>();
//                    Map<String,Vector> mapOldSelCheckResult = new LinkedHashMap<>();




                    Map<String,Vector> mapNewResult = new LinkedHashMap<>();
                    Map<String,Vector> mapOldResult = new LinkedHashMap<>();
                    Vector<Vector> vectorNewCsvData = new Vector<>();
                    Vector<Vector> vectorOldCsvData = new Vector<>();


                    boolean checkKey = false;
                    List<Integer> noOldCheckColumns = csvViewOld.getNoCheckColumn().stream().toList();
                    List<Integer> noNewCheckColumns = csvViewNew.getNoCheckColumn().stream().toList();


                    vectorNewCsv.forEach(d -> {
                        String key = d.get(keyColumn).toString();
                        mapNewBase.put(key, d);
                    });

//                    mapNewBase.forEach((k,data)->{
//                        System.out.println("mapNewBase datauuuuu: " +data.toString());
//                    });

//                    for(int i=0;i<vectorNewCsv.size();i++){
//                        Vector item = vectorNewCsv.get(i);
////                        for(int j =noNewCheckColumns.size();j>0;j--){
////                            item.set(noNewCheckColumns.get(j-1),null);
////                        }
//                    }

                    vectorNewCsv.forEach(item -> {
                        String key = item.get(keyColumn).toString();
//                        for(int i =noNewCheckColumns.size();i>0;i--){
//                            item.set(noNewCheckColumns.get(i-1),null);
//
//                        }
                        mapNewSelCheckBase.put(key,item);
                    });



                    vectorOldCsv.forEach(data -> {
                        String key = data.get(keyColumn).toString();
                        mapOldBase.put(key, data);
                        System.out.println("mapOldBase data:" + mapOldBase.get(key));
                        Vector vectorOldItem = data;
//                        for(int i=noOldCheckColumns.size();i>0;i--){
//                            vectorOldItem.set(noNewCheckColumns.get(i-1),null);
//                           // vectorOldItem.removeElementAt(noOldCheckColumns.get(i-1));
//                        }
                        mapOldSelCheckBase.put(key,vectorOldItem);
                    });

                    int columnLength =   vectorNewCsv.get(0).size()  -1;


                    if (checkKey) {
                        JOptionPane.showConfirmDialog(null, "key一個しかない", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // find new item

                        mapNewSelCheckBase.forEach((key, d) -> {
                            if (mapOldSelCheckBase.containsKey(key) == false) {
                                Vector newData = mapNewBase.get(key);
                                mapNewResult.put(key, newData);
                                System.out.println("Add new item: "+mapNewResult.get(key));
                            }
                        });
                        rowStatus[0] = mapNewResult.size();

                        mapNewResult.forEach((k, d) -> {
                            d.set(columnLength, "new");
                            System.out.println("Add text new");
                            vectorNewCsvData.add(d);
                        });

                        // find delete item
                        mapOldSelCheckBase.forEach((k, d) -> {
                            if (mapNewSelCheckBase.containsKey(k) == false) {
                                mapOldResult.put(k, mapOldBase.get(k));
                            }
                        });
                        rowStatus[3] = mapOldSelCheckBase.size();


                        mapOldResult.forEach((k, d) -> {
                            d.set(columnLength, "delete");
                            System.out.println("Add text delete");
                            vectorOldCsvData.add(d);
                        });


//                         find old item

                        vectorChange = new Vector<>();
                        if (mapNewBase.size() > mapOldBase.size()) {
                            mapNewBase.forEach((key, data) -> {
                                Vector item = mapOldBase.get(key);

                                if (item != null) {

                                    if (data.equals(item)) {
                                        data.set(columnLength, "old");
                                        //System.out.println("data " + data);
                                        vectorNewCsvData.add(data);

                                        item.set(columnLength, "old");
                                        // System.out.println("item " + item);
                                        vectorOldCsvData.add(item);
                                    } else {
                                        String checkStatus = item.get(columnLength).toString();
                                        if (checkStatus.compareTo("STATUS") == 0) {
                                            data.set(columnLength, "change");
                                            //System.out.println("data " + data);
                                            vectorNewCsvData.add(data);

                                            item.set(columnLength, "change");
                                            //System.out.println("item " + item);
                                            vectorOldCsvData.add(item);
                                        }
                                        vectorChange.add(key);
                                    }
                                }


                            });


                        } else {
                            mapOldBase.forEach((key, data) -> {
                                Vector item = mapNewBase.get(key);
                                if (item != null) {

                                    if (data.equals(item)) {
                                        data.set(columnLength, "old");
                                       // System.out.println("data " + data);
                                        vectorNewCsvData.add(data);

                                        item.set(columnLength, "old");
                                        // System.out.println("item " + item);
                                        vectorOldCsvData.add(item);
                                    } else {
                                        String checkStatus = item.get(columnLength).toString();
                                        if (checkStatus.compareTo("STATUS") == 0) {
                                            data.set(columnLength, "change");
                                           // System.out.println("data " + data);
                                            vectorNewCsvData.add(data);

                                            item.set(columnLength, "change");
                                            System.out.println("item " + item);
                                            vectorOldCsvData.add(item);
                                            vectorChange.add(key);
                                        }
                                    }
                                }
                            });
                        }

                        vectorOldCsvData.forEach((d) -> {
                            if (d.get(columnLength).toString().contains("old")) {
                                rowStatus[1] = rowStatus[1] + 1;
                            } else if (d.get(columnLength).toString().contains("change")) {
                                rowStatus[2] = rowStatus[2] + 1;
                            }
                        });

                        // delete data csv
                        vectorNewCsv = vectorNewCsvData;
                        vectorOldCsv = vectorOldCsvData;

                        System.out.println("mapNewBaseView: " + mapNewSelCheckBase.size());
                        System.out.println("mapOldBaseView: " + mapOldSelCheckBase.size());

                        System.out.println("OK");
                        System.out.println(dtf.format(LocalDateTime.now()));
                        jComboBoxMode.setVisible(true);

                        String msg = "新規: " + rowStatus[0] + "数量 ";
                        msg = msg + "そのまま: " + rowStatus[1] + "数量 ";
                        msg = msg + "変更: " + rowStatus[2] + "数量 ";
                        msg = msg + "削除: " + rowStatus[3] + "数量 ";

                        JOptionPane.showConfirmDialog(null,
                                "Status: " + msg, "Compare is OK!", JOptionPane.DEFAULT_OPTION);


                      //  vectorNewHeader.removeElementAt(vectorNewHeader.size()-1);

//                        Vector<String> vectorCompare = vectorNewHeader;
//                        vectorCompare.removeElementAt(vectorCompare.size()-1);
                        csvViewNew.tableAction(vectorNewHeader, mapNewBase, mapOldBase);

                    }
                }
                else {
                    String msg = "警告: KEY を確認お願い致します。";
                    JOptionPane.showConfirmDialog(null, msg, "Column数量警告!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void selectComboKey(){
        jComboBoxKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxKey.getSelectedIndex()>=1){
                    String item = jComboBoxKey.getItemAt(jComboBoxKey.getSelectedIndex()).toString();
                    System.out.println("jComboBoxKey item : "+ item);
                    System.out.println("jComboBoxKey index : "+ jComboBoxKey.getSelectedIndex());
                    csvViewNew.setKeyColumn(jComboBoxKey.getSelectedIndex());
                }

            }
        });
    }
    private void compareView(){


        //csvViewNew.tableAction(csvViewNew,vectorNewHeader,mapNewBase,mapOldBase);
        jButtonCompareView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompareView compareView = new CompareView("Compare list") ;
                compareView.addVectorHeader(vectorNewHeader);

                Vector vectorChangeView = new Vector<>();
                vectorChangeView.add(vectorChange.get(1));
                vectorChangeView.add(vectorChange.get(2));
                vectorChangeView.add(vectorChange.get(3));
                vectorChangeView.add(vectorChange.get(4));
                vectorChangeView.add(vectorChange.get(5));
                vectorChangeView.add(vectorChange.get(6));
                vectorChangeView.add(vectorChange.get(7));
                vectorChangeView.add(vectorChange.get(8));
                vectorChangeView.add(vectorChange.get(9));
                vectorChangeView.add(vectorChange.get(10));
                vectorChangeView.add(vectorChange.get(11));
                vectorChangeView.add(vectorChange.get(12));
                vectorChangeView.add(vectorChange.get(13));
                vectorChangeView.add(vectorChange.get(14));
                vectorChangeView.add(vectorChange.get(15));


                compareView.listCompare(mapNewBase, mapOldBase,vectorChangeView);


                compareView.tableFinish();
                compareView.showWindow();

                System.out.println(vectorChange);



            }
        });
    }

    private boolean checkCompareColumn(){
        Set<Integer> setNewCsv = csvViewNew.getNoCheckColumn();
        Set<Integer> setOldCsv = csvViewOld.getNoCheckColumn();
        int selectNewCol = csvViewNew.getModel().getColumnCount() - setNewCsv.size();
        int selectOldCol = csvViewOld.getModel().getColumnCount() - setOldCsv.size();

        if(selectOldCol - selectNewCol==0)return true;

        String msg = "警告: ";
        msg = msg + "新規CSVの比較項目数量: "+  selectNewCol +"  ";
        msg = msg + "古いCSVの比較項目数量: "+  selectOldCol +"  ";
        msg = msg + "は不一致なのでご確認お願い致します";
        JOptionPane.showConfirmDialog(null, msg, "Column数量警告!", JOptionPane.ERROR_MESSAGE);
       return false;
    }

    private boolean checkKeyColumn(){
        int index = jComboBoxKey.getSelectedIndex();
        List<Integer> listNoCheck = csvViewNew.getNoCheckColumn().stream().toList();
        for(int i=0;i<listNoCheck.size();i++){
            if(listNoCheck.get(i) == index){
                return false;
            }
        }
        return true;
    }
}
