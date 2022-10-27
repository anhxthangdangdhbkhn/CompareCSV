package ui;


import PostgreSQL.CSVRow;
import database.DatabaseConnection;
import hepper.JsonHelp;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class FMPGM extends JFrame {

    JButton jButtonImportDBBFFFFP;
    JButton jButtonImportCustomer;
    JButton jButtonPreview;
    JButton jButtonExit;
    JButton jButtonInformation;
    JTable jTablePrint;

    JLabel jLabelImage = new JLabel();

    JScrollPane jScrollPaneTablePrint;
    DefaultTableModel csv_data=  new DefaultTableModel();
    JComboBox<String> jComboBoxCustomer = new JComboBox<>();

    JFileChooser jFileChooserDBBFFP;
    JFileChooser jFileChooserCustomer;


    JSONArray jsonArrayDBBFFP = new JSONArray();
    JSONArray jsonArrayPrint = new JSONArray();
    JsonHelp jsonHelpDBBFFP = new JsonHelp(jsonArrayDBBFFP);
    String stringProductName;

    DatabaseConnection databaseConnection = new DatabaseConnection();

    boolean booleanFileDBBFFFFP =false;

    public FMPGM(String title){
        super(title);
        databaseConnection.connect();
        addControls();
        addEvents();
    }



    public void showWindow(){
        setSize(900,900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addControls() {

        Container container = getContentPane();
        JPanel jPanelMain = new JPanel();
        jPanelMain.setLayout(new BoxLayout(jPanelMain,BoxLayout.Y_AXIS));
        container.add(jPanelMain);

        //JPanel jPanelHeader = new JPanel();

        JPanel jPanelTitle = new JPanel();
        jPanelTitle.setLayout(new FlowLayout());
        JLabel jLabelTitle = new JLabel("指定納品書発行");
        jPanelTitle.add(jLabelTitle);
        jPanelMain.add(jPanelTitle);


        JPanel jPanelHeader = new JPanel();
        jPanelHeader.setLayout(new FlowLayout());
        jButtonImportDBBFFFFP = new JButton("Import DBBFFFFP");
        jButtonImportCustomer= new JButton("Import Customer");
        jButtonPreview= new JButton("Preview");
        jButtonExit= new JButton("Exit");
        jButtonInformation= new JButton("Information");
        jPanelHeader.add(jButtonImportDBBFFFFP);
        jPanelHeader.add(jButtonImportCustomer);
        jPanelHeader.add(jButtonPreview);
        jPanelHeader.add(jButtonExit);
        jPanelHeader.add(jButtonInformation);

        jPanelMain.add(jPanelHeader);


      //  jComboBoxCustomer.addItem("--------------------------顧客ｺｰﾄﾞ・店舗名を選択--------------------------");


        JPanel jPanelCustomerSelect = new JPanel();
        jPanelCustomerSelect.setLayout(new FlowLayout());
        jPanelCustomerSelect.add(jComboBoxCustomer);
        jPanelMain.add(jPanelCustomerSelect);
        jComboBoxCustomer.setVisible(false);


        JPanel jPanelPrint = new JPanel();
        jPanelPrint.setLayout(new FlowLayout());



        ImageIcon imageIcon = new ImageIcon("C:\\Users\\jsys04\\Documents\\ThangDD\\Project\\FM定納品発生PGM\\image\\134xx.PNG"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(960, 520,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);
        jLabelImage.setIcon(imageIcon);
        jPanelPrint.add(jLabelImage);
        jPanelMain.add(jPanelPrint);


        jTablePrint = new JTable();
        jScrollPaneTablePrint = new JScrollPane(jTablePrint);
        jPanelPrint.add(jScrollPaneTablePrint);
        jPanelMain.add(jPanelPrint);

        csv_data.addColumn("顧客名1");
        csv_data.addColumn("日付");
        csv_data.addColumn("商品名");
        csv_data.addColumn("数量");
        csv_data.addColumn("金額");













    }

    private void showChooseFileCustomer(){
        jFileChooserCustomer = new JFileChooser();
        java.util.List<Object> listSheet = new ArrayList<>();
        jButtonImportCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = jFileChooserCustomer.showOpenDialog(null);


                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File f = jFileChooserCustomer.getSelectedFile();
                    String stringPath = f.getPath();
                    ////System.out.println("Path: "+stringPath);

                    if(stringPath.contains("xlsx")== true || stringPath.contains(".xlsm")==true){
                        try {
                            File fileCustomer= new File(stringPath);
                            FileInputStream fileInputStreamCustomer = new FileInputStream(fileCustomer);
                            Workbook  workbook = new XSSFWorkbook(fileInputStreamCustomer);

                            listSheet.clear();

                            System.out.println("List size 1 "+ listSheet.size());
                            for(int i=0; i<workbook.getNumberOfSheets();i++){
                                listSheet.add(workbook.getSheetName(i));
                            }

                            String stringSelectSheet = (String) JOptionPane.showInputDialog(null, "Please choose a sheet", "ファイルのシート名リスト",
                                    JOptionPane.QUESTION_MESSAGE, null,listSheet.toArray(), "Joe");
                            System.out.println("List size 2 "+ listSheet.size());
                            System.out.println(stringSelectSheet);
                            Sheet sheetCurrent  = workbook.getSheet(stringSelectSheet);

                            Iterator<Row> rowIterator = sheetCurrent.iterator();
                            jComboBoxCustomer.removeAllItems();
                            while (rowIterator.hasNext())
                            {
                                Row row = rowIterator.next();
                                int i =0;
                                Iterator <Cell> cellIterator = row.cellIterator();

                                while (cellIterator.hasNext())
                                {
                                    Cell cell = cellIterator.next();
                                    if(i==5){

                                        if(cell !=null ) {
                                            jComboBoxCustomer.addItem(row.getCell(5).toString());
                                        }
                                    }
                                    i = i+1;
                                }
                            }
                            workbook.close();
                            jComboBoxCustomer.setVisible(true);

                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        System.out.println("Dinh dang file bi loi");
                    }
                }


            }

        });

    }



    private void showChooseFileDBBFFFFP(){
        jFileChooserDBBFFP = new JFileChooser();
        jButtonImportDBBFFFFP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = jFileChooserDBBFFP.showOpenDialog(null);

                if(returnVal == JFileChooser.APPROVE_OPTION){

                    File f = jFileChooserDBBFFP.getSelectedFile();
                    String stringPath = f.getPath();
                    //System.out.println(stringPath);

                    if(stringPath.contains("csv")== true){
                        booleanFileDBBFFFFP = true;
                        jsonHelpDBBFFP.ConvertCSVJToJson(stringPath);
                    }
                }
            }
        });
    }


    public void addEvents(){
        showChooseFileCustomer();
        showChooseFileDBBFFFFP();


//        jButtonImportCustomer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//
//        });

        jComboBoxCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxCustomer.getSelectedIndex()>=1){
                    stringProductName = jComboBoxCustomer.getItemAt(jComboBoxCustomer.getSelectedIndex()).toString();
                    System.out.println("Get index: "+ jComboBoxCustomer.getSelectedIndex());
                }

            }
        });

//        jButtonPreview.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(jComboBoxCustomer.getSelectedIndex()>=1 && booleanFileDBBFFFFP==true){
//                    stringProductName = jComboBoxCustomer.getItemAt(jComboBoxCustomer.getSelectedIndex());
//                    jsonArrayPrint = jsonHelpDBBFFP.SearchJson((String) stringProductName.subSequence(0,3),"0");
//                    jsonHelpDBBFFP.SortJson(jsonArrayPrint,"G");
//
//
//                    boolean readHeader = true;
//                    for (Object obj:jsonArrayPrint){
//                        Vector row = new Vector();
//                        JSONObject jo = (JSONObject) obj;
//                        if(readHeader){
//                            readHeader = false;
//                            p134 = new P134(jo.get("AG").toString(),jo.get("Z").toString(),jo.get("Z").toString());
//                        }
//                        System.out.println(jo.get("AG"));
//                        row.add(jo.get("AG").toString().trim());
//                        row.add(jo.get("Z"));
//                        row.add(jo.get("U"));
//                        row.add(jo.get("Q"));
//                        row.add(jo.get("R"));
//                        p134.addNewProduct(new Product134(jo.get("U").toString(),jo.get("Q").toString(),jo.get("R").toString()));
//
//                        csv_data.addRow(row);
//                        System.out.println(obj.toString());
//                    }
//
//                    jTablePrint.setModel(csv_data);
//
//
//                    JRBeanCollectionDataSource product134List = new JRBeanCollectionDataSource(p134.getProduct134List());
//
//                    Map<String,Object> parameters = new HashMap<>();
//                    parameters.put("Customer",p134.getCustomer());
//                    parameters.put("Company",p134.getCompany());
//                    parameters.put("date",p134.getDate());
//
//                    parameters.put("P134",product134List);
//
//
//
//                    String jasperFilePath =  FMPGM.class.getResource("/JasperReport_A4.jrxml").getPath();
//
//                    try {
//                        InputStream inputStream = new FileInputStream(jasperFilePath);
//                        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
//                        jasperPrint.addPage(jasperPrint.getPages().get(0));
//                        jasperPrint.addPage(jasperPrint.getPages().get(0));
//                        JRPrintPage jrPrintPage;
//
//
//                        JasperExportManager.exportReportToPdfFile(jasperPrint,"P1345.pdf");
//                        JasperViewer.viewReport(jasperPrint);
//
//
//                    } catch (FileNotFoundException | JRException ee) {
//                        ee.printStackTrace();
//                    }
//                    System.out.println("EmployeeTest");
//                }
//            }
//        });

//        jButtonPreview.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(jComboBoxCustomer.getSelectedIndex()>=1){
//                    stringProductName = jComboBoxCustomer.getItemAt(jComboBoxCustomer.getSelectedIndex());
//                    jsonArrayPrint = jsonHelpDBBFFP.SearchJson((String) stringProductName.subSequence(0,3),"0");
//
//                    List<CSVRow> csvRowList = databaseConnection.csvSearchW("134","0");
//                    Set<String> setKey = new HashSet<String>();
//                    Map<String,List<CSVRow>> map = new LinkedHashMap<>();
//
//                    for(CSVRow csvRow: csvRowList){
//                        setKey.add(csvRow.getC());
//                    }
//
//
//                    for(int i=0;i<setKey.size();i++){
//                        List<CSVRow> list = new ArrayList<>();
//                        for(CSVRow csvRow: csvRowList){
//                            if(setKey.toArray()[i].toString().compareTo(csvRow.getC())==0){
//                                list.add(csvRow);
//                            }
//                        }
//                        map.put(setKey.toArray()[i].toString(),list);
//                    }
//
//
//                    String jasperFilePath =  FMPGM.class.getResource("/JasperReport_A4.jrxml").getPath();
//                    InputStream inputStream = null;
//                    JasperDesign jasperDesign = null;
//                    JasperPrint jasperPrint=null;
//                    JasperPrint jasperPrintAll=null;
//                    JasperReport jasperReport=null;
//                    try {
//                        inputStream = new FileInputStream(jasperFilePath);
//                        jasperDesign = JRXmlLoader.load(inputStream);
//                        jasperReport = JasperCompileManager.compileReport(jasperDesign);
//
//                    } catch (FileNotFoundException | JRException ex) {
//                        ex.printStackTrace();
//                    }
//
//                    for(Map.Entry m:map.entrySet()){
//                        List<CSVRow> list = new ArrayList<>();
//                        list = (List<CSVRow>) m.getValue();
//
//                        //chi co cuoi tuan
//                        int listSize = list.size();
//                        int weekend =0;
//                        for(int i=0;i<list.size();i++){
//                            if(list.get(i).getU().compareTo("ｼﾕｳｶﾝ ﾘﾖｳｷﾝ")==0){
//                                weekend= weekend +1;
//                            }
//                        }
//
//                        if(weekend ==listSize){
//                            System.out.println("Chi co cuoi tuan");
//
//
//
//                            for(int i=0;i<list.size();i++){
//                                P134 p134 = new P134(list.get(i).getAG(),"",list.get(i).getZ());
//
//                                JRBeanCollectionDataSource product134List = new JRBeanCollectionDataSource(p134.getProduct134List());
//                                Map<String,Object> parameters = new HashMap<>();
//                                parameters.put("Customer",p134.getCustomer());
//                                parameters.put("Company",p134.getCompany());
//                                parameters.put("date",p134.getDate());
//                                parameters.put("P134",product134List);
//
//
//                                try {
//                                    jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
//                                    if(i==0) jasperPrintAll = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
//
//                                    jasperPrintAll.addPage(jasperPrint.getPages().get(0));
//                                } catch ( JRException ee) {
//                                    ee.printStackTrace();
//                                }
//                                System.out.println("EmployeeTest");
//
//                            }
//
//
//
//
//                        }else if (0< weekend && weekend < listSize){
//                            System.out.println("Co ca hai");
//                        }else{
//                            System.out.println("khong co cuoi tuan");
//                        }
//
//                    }
//
//
//                    JasperViewer.viewReport(jasperPrintAll);
//
//                }
//            }
//        });



    }

}
