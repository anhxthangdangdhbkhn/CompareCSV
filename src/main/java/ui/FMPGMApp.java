package ui;

import FMPGM.p134.Product134;
import PostgreSQL.CSVRow;
import database.DatabaseConnection;
import hepper.ABC;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class FMPGMApp extends JFrame {
    public FMPGMApp(String title) throws HeadlessException {
        super(title);
        addControls();
        addEvents();
    }

    JTabbedPane jTabbedPane;
    JButton jButtonCompanyCleanDatabase;
    JButton jButtonCompanyImportDatabase;

    JButton jButtonImportCsv;
    JButton jButtonClearCsv;

    //JScrollPane jScrollPaneTablePrint;
    JButton jButtonPreview;
    JButton jButtonLocal;
    JComboBox jComboBoxCustomer;
    String stringProductName;



    private void addControls() {

        Container container = getContentPane();
        jTabbedPane = new JTabbedPane();
        container.add(jTabbedPane);

        JPanel jPanelExport = new JPanel();
        JPanel jPanelCompany = new JPanel();
        JPanel jPanelCsv = new JPanel();
        JPanel jPanelHelp = new JPanel();

        jTabbedPane.add(jPanelExport,"Export");
        jTabbedPane.add(jPanelCompany,"Company");
        jTabbedPane.add(jPanelCsv,"Csv");
        jTabbedPane.add(jPanelHelp,"Help");



        JPanel jPanelHelpLayout = new JPanel();
        jPanelHelpLayout.setLayout(new BoxLayout(jPanelHelpLayout,BoxLayout.Y_AXIS));

//        jPanelHelpLayout.add(new JLabel("About"));
//        jPanelHelpLayout.add(new JLabel("1.竹田課長"));
//        jPanelHelpLayout.add(new JLabel("2.根本部長"));
//        jPanelHelpLayout.add(new JLabel("3.ダンタン"));
//        jPanelHelp.add(jPanelHelpLayout);


//        jPanelExport.setBackground(Color.lightGray);
//        jPanelCompany.setBackground(Color.PINK);
//        jPanelCsv.setBackground(Color.orange);

        jButtonClearCsv = new JButton("Clear CSV");
        jButtonImportCsv = new JButton("Import CSV");
        jPanelCsv.add(jButtonImportCsv);
        jPanelCsv.add(jButtonClearCsv);

        jButtonCompanyImportDatabase= new JButton("Import Company");
        jButtonCompanyCleanDatabase =new JButton("Clear Company");
        jPanelCompany.add(jButtonCompanyImportDatabase);
        jPanelCompany.add(jButtonCompanyCleanDatabase);


        //jScrollPaneTablePrint = new JScrollPane();
        jButtonPreview= new JButton("Preview");
        jButtonLocal= new JButton("Get data");
        jComboBoxCustomer = new JComboBox();
        //jPanelExport.add(jScrollPaneTablePrint);
        jPanelExport.add(jButtonLocal);
        jPanelExport.add(jComboBoxCustomer);
        jPanelExport.add(jButtonPreview);
        jComboBoxCustomer.setVisible(false);



    }
    private void addEvents() {
        showChooseFileCustomer();
        showChooseFileDBBFFFFP();





        jButtonClearCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "CSVデータベース削除ですか");
                // 0=yes, 1=no, 2=cancel
                System.out.println(input);
                if(input==0){
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    int deletedCheck = databaseConnection.csvDeleteAllTable();
                    if(deletedCheck==0){
                        JOptionPane.showMessageDialog(null,"全て削除済","CSVデータベース削除",JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,"全て削除済","CSVデータベース削除できません",JOptionPane.WARNING_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"保留中","CSVデータベース削除",JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        jButtonCompanyCleanDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "顧客コードデータベース削除ですか");
                // 0=yes, 1=no, 2=cancel
                System.out.println(input);
                if(input==0){
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    databaseConnection.companyDeleteAllTable();
                    jComboBoxCustomer.setVisible(false);
                    jComboBoxCustomer.removeAllItems();
                    JOptionPane.showMessageDialog(null,"全て削除済","顧客コードデータベース削除",JOptionPane.WARNING_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,"保留中","顧客コードデータベース削除",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        jButtonLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                List<String> stringList = databaseConnection.companyTable();
                if(stringList.size()==0){
                    jComboBoxCustomer.setVisible(false);
                    JOptionPane.showMessageDialog(null,"【Company】にインポートしてください","インポート必要",JOptionPane.WARNING_MESSAGE);
                }else{
                    jComboBoxCustomer.setVisible(true);
                    comboBoxView(stringList);
                }

                //System.out.println(databaseConnection.companyTable().toString());
            }
        });

        jButtonPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(jComboBoxCustomer.getSelectedIndex()>=1){
                        stringProductName = (String) jComboBoxCustomer.getItemAt(jComboBoxCustomer.getSelectedIndex());
                        stringProductName = (String) stringProductName.subSequence(0,3);
                       // System.out.println("Print select Index: "+ stringProductName);
                        if(stringProductName.compareTo("134")==0){
                            DatabaseConnection databaseConnection = new DatabaseConnection();
                            List<CSVRow> csvRowList = databaseConnection.csvSearchW(stringProductName,"0");

                            List<Product134> product134List = databaseConnection.csvP134SearchW(stringProductName,"0");

                            if(csvRowList.size()==0){
                                JOptionPane.showMessageDialog(null,
                                        "顧客伝票存在しない",
                                        "顧客伝票",
                                        JOptionPane.INFORMATION_MESSAGE);

                            }else{
                             //   P134Print(csvRowList);
                                P134Print(product134List);
                            }
                        }


                    }
            }
        });

    }

    public void comboBoxView(List<String> list){
        jComboBoxCustomer.removeAllItems();
        for(int i=0;i<list.size();i++){
            jComboBoxCustomer.addItem(list.get(i));
        }

    }

    private void showChooseFileDBBFFFFP(){
        JFileChooser jFileChooserDBBFFP = new JFileChooser();
        jButtonImportCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int returnVal = jFileChooserDBBFFP.showOpenDialog(null);

                if(returnVal == JFileChooser.APPROVE_OPTION){

                    File f = jFileChooserDBBFFP.getSelectedFile();
                    String stringPath = f.getPath();
                    System.out.println(stringPath);

                    if(stringPath.contains("csv")== true){
                        try {
                            File file = new File(stringPath);
                            InputStreamReader ir = new InputStreamReader(new FileInputStream(file),"SJIS");
                            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);
                            List<CSVRow> list = new ArrayList<>();
                            int insertCheck =0;

                            int input = JOptionPane.showConfirmDialog(null, "インポートですか");
                            // 0=yes, 1=no, 2=cancel
                            System.out.println(input);

                            if(input==0){
                                DatabaseConnection databaseConnection = new DatabaseConnection();

                                for(CSVRecord record:records){
                                    CSVRow csvRow = new CSVRow();
                                    csvRow.setA(record.get(0));
                                    csvRow.setB(record.get(1));
                                    csvRow.setC(record.get(2));
                                    csvRow.setD(record.get(3));
                                    csvRow.setE(record.get(4));
                                    csvRow.setF(record.get(5));
                                    csvRow.setG(record.get(6));
                                    csvRow.setH(record.get(7));
                                    csvRow.setI(record.get(8));
                                    csvRow.setJ(record.get(9));
                                    csvRow.setK(record.get(10));
                                    csvRow.setL(record.get(11));
                                    csvRow.setM(record.get(12));
                                    csvRow.setN(record.get(13));
                                    csvRow.setO(record.get(14));
                                    csvRow.setP(record.get(15));
                                    csvRow.setQ(record.get(16));
                                    csvRow.setR(record.get(17));
                                    csvRow.setS(record.get(18));
                                    csvRow.setT(record.get(19));
                                    csvRow.setU(record.get(20));
                                    csvRow.setV(record.get(21));
                                    csvRow.setW(record.get(22));
                                    csvRow.setX(record.get(23));
                                    csvRow.setY(record.get(24));
                                    csvRow.setZ(record.get(25));
                                    csvRow.setAA(record.get(26));
                                    csvRow.setAB(record.get(27));
                                    csvRow.setAC(record.get(28));
                                    csvRow.setAD(record.get(29));
                                    csvRow.setAE(record.get(30));
                                    csvRow.setAF(record.get(31));
                                    csvRow.setAG(record.get(32));
                                    csvRow.setAH(record.get(33));
                                    csvRow.setAI(record.get(34));
                                    csvRow.setAJ(record.get(35));
                                    csvRow.setAK(record.get(36));
                                    list.add(csvRow);
                                }
                                insertCheck = databaseConnection.csvInsertRows(list);
                                if(insertCheck==0) {
                                    JOptionPane.showMessageDialog(null,
                                            "CSVインポート出来ない",
                                            "CSVインポート",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(null,
                                            "CSVの"+ insertCheck +"行目がインポートされた",
                                            "CSVインポート",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }

                            ir.close();

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

    private void showChooseFileCustomer(){

        JFileChooser jFileChooserCustomer = new JFileChooser();
        java.util.List<Object> listSheet = new ArrayList<>();
        jButtonCompanyImportDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = jFileChooserCustomer.showOpenDialog(null);
                List<String> listCompany = new ArrayList<>();
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File f = jFileChooserCustomer.getSelectedFile();
                    String stringPath = f.getPath();
                    ////System.out.println("Path: "+stringPath);

                    if(stringPath.contains(".xlsx")== true || stringPath.contains(".xlsm")==true){
                        try {
                            File fileCustomer= new File(stringPath);
                            FileInputStream fileInputStreamCustomer = new FileInputStream(fileCustomer);
                            Workbook workbook = new XSSFWorkbook(fileInputStreamCustomer);

                            listSheet.clear();
                            listCompany.clear();

                     //       System.out.println("List size 1 "+ listSheet.size());
                            for(int i=0; i<workbook.getNumberOfSheets();i++){
                                listSheet.add(workbook.getSheetName(i));
                            }

                            String stringSelectSheet = (String) JOptionPane.showInputDialog(null, "シート名を選択してください", "ファイルのシート名リスト",
                                    JOptionPane.QUESTION_MESSAGE, null,listSheet.toArray(), "Joe");
                           // System.out.println("List size 2 "+ listSheet.size());
                            //System.out.println(stringSelectSheet);
                            Sheet sheetCurrent  = workbook.getSheet(stringSelectSheet);
                            int i = 0;
                            Iterator<Row> rowIterator = sheetCurrent.iterator();
                            while (rowIterator.hasNext())
                            {
                                Row row = rowIterator.next();
                                i =0;
                                Iterator <Cell> cellIterator = row.cellIterator();

                                while (cellIterator.hasNext())
                                {
                                    Cell cell = cellIterator.next();
                                    if(i==5){

                                        if(cell !=null ) {
                                            listCompany.add(row.getCell(5).toString());
                                            //System.out.println(row.getCell(5).toString());
                                        }
                                    }
                                    i = i+1;
                                }
                            }
                            workbook.close();

                            int input = JOptionPane.showConfirmDialog(null, "顧客コードをインポートしたいですか");
                            if(input==0 && listCompany.size()>0){
                                jFileChooserCustomer.setVisible(true);
                                DatabaseConnection databaseConnection = new DatabaseConnection();

                                int result = databaseConnection.companyInsertColumns(listCompany);
                                if(result==0){
                                    JOptionPane.showMessageDialog(null,"何もインポートされてない状態。","顧客コードのインポート",JOptionPane.WARNING_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(null,Integer.toString(result) +" 顧客コード数がインポートされた状態。","顧客コードのインポート",JOptionPane.WARNING_MESSAGE);
                                    comboBoxView(listCompany);
                                }

                            }
                            if(listCompany.size()==0){
                                JOptionPane.showMessageDialog(null,"何もインポートされてない状態。","顧客コードのインポート",JOptionPane.WARNING_MESSAGE);
                            }


                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,
                                ".xlsmや.xlsxを選択してください",
                                "顧客コードインポート",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "何も選択されてない",
                            "顧客コードインポート",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }

        });
    }

    private  void P134Print(List<Product134> product134List){

        /// template
        JasperReport jasperReport=null;
        JasperPrint jasperPrint=null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();




        String jasperFilePath =  getClass().getResource("/JasperReport_A4.jasper").getPath();




        // logic
        Set<String> setKey = new HashSet<>();
        Map<String,List<Product134>> map = new LinkedHashMap<>();

        for(Product134 product134: product134List){
            setKey.add(product134.getSlipID());
        }

        for(int i=0;i<setKey.size();i++){
            List<Product134> list = new ArrayList<>();
            for(Product134 product134: product134List){
                if(setKey.toArray()[i].toString().compareTo(product134.getSlipID())==0){
                    list.add(product134);
                }
            }
            map.put(setKey.toArray()[i].toString(),list);
        }

        for(Map.Entry m:map.entrySet()){
            List<Product134> product134ArrayList = new ArrayList<>();
            product134ArrayList = (List<Product134>) m.getValue();

            //chi co cuoi tuan
            int listSize = product134ArrayList.size();
            int weekendCheck =0;

            for(int i=0;i<product134ArrayList.size();i++){
                System.out.println(product134ArrayList.get(i).getSelect());
                if(product134ArrayList.get(i).getSelect().compareTo("ｼﾕｳｶﾝ ﾘﾖｳｷﾝ")==0){
                    weekendCheck= weekendCheck +1;
                }
            }

            //System.out.println("weekend: "+weekendCheck);


            if(weekendCheck ==listSize){

                int Total =0;
                String TotalMan ="";
                String TotalSen ="";
                String TotalYen ="";

                for(int i=0;i<product134ArrayList.size();i++)
                {
                    Product134 product134 = product134ArrayList.get(i);

                    product134.setQuantity("");
                    product134.setSlipID("レンタルユニフォーム");
                    System.out.println("In phieu chi co cuoi tuan");
                    String Customer = product134.getCustomer();
                    String Company = product134.getCompany();
                    String Date = product134.getDate();


                    Total = Total + Integer.parseInt(product134.getPrice());
                    String TotalString = Integer.toString(Total);

                    if(product134ArrayList.size()==i-1 ||product134ArrayList.size()==1){
                        int priceLen =  TotalString.length() -1;
                        for(int j=TotalString.length()-1;j>=0;j--){
                            if(j<=2 && i >=0)  TotalYen = TotalYen + TotalString.charAt(priceLen - j);
                            if(j <=5 && j >=3 )  TotalSen = TotalSen + TotalString.charAt(priceLen - j);
                            if(j > 5) TotalMan = TotalMan  + TotalString.charAt(priceLen - j);
                        }
                    }

                    product134.setPrice("");
                    String Weekend = "週間料金";

                    JRBeanCollectionDataSource product = new JRBeanCollectionDataSource(product134ArrayList);

                    Map<String,Object> parameters = new HashMap<>();

                    parameters.put("Customer",Customer);
                    parameters.put("Company",Company);
                    parameters.put("Date",Date);
                    parameters.put("TotalMan",TotalMan);
                    parameters.put("TotalSen",TotalSen);
                    parameters.put("TotalYen",TotalYen);
                    parameters.put("Weekend",Weekend);
                    parameters.put("P134",product);

                    try {
                        jasperPrint = JasperFillManager.fillReport(jasperFilePath,parameters,new JREmptyDataSource());
                        jasperPrintList.add(jasperPrint);
                        //System.out.println("print.....");

                    } catch ( JRException ee) {
                        ee.printStackTrace();
                    }
                    System.out.println("EmployeeTest");

                }


            }
            else if (0 == weekendCheck){

                System.out.println("In phieu chi co trong tuan");
                JRBeanCollectionDataSource product =null;
                Product134 product134 = null;

                String TotalMan ="";
                String TotalSen ="";
                String TotalYen ="";
                int Total =0;

                // get total
                for(int i=0;i<product134ArrayList.size();i++){
                    Total = Total +Integer.parseInt(product134ArrayList.get(i).getPrice());
                }

                // total => String
                String TotalString = Integer.toString(Total);

                int priceLen =  TotalString.length() -1;
                for(int i=TotalString.length()-1;i>=0;i--){
                    if(i<=2 && i >=0)  TotalYen = TotalYen + TotalString.charAt(priceLen - i);
                    if(i <=5 && i >=3 )  TotalSen = TotalSen + TotalString.charAt(priceLen - i);
                    if(i > 5) TotalMan = TotalMan  + TotalString.charAt(priceLen - i);
                }


                if(product134ArrayList.size()>1){
                    product134 = product134ArrayList.get(0);
                }

                    String Customer = product134.getCustomer();
                    String Company = product134.getCompany();
                    String Date = product134.getDate();

                    String Weekend = "";

                    product = new JRBeanCollectionDataSource(product134ArrayList);

                    Map<String,Object> parameters = new HashMap<>();

                    parameters.put("Customer",Customer);
                    parameters.put("Company",Company);
                    parameters.put("Date",Date);
                    parameters.put("TotalMan",TotalMan);
                    parameters.put("TotalSen",TotalSen);
                    parameters.put("TotalYen",TotalYen);
                    parameters.put("Weekend",Weekend);
                    parameters.put("P134",product);

                    try {
                        jasperPrint = JasperFillManager.fillReport(jasperFilePath,parameters,new JREmptyDataSource());
                        jasperPrintList.add(jasperPrint);

                    } catch ( JRException ee) {
                        ee.printStackTrace();
                    }
                    System.out.println("In phieu trong tuan");

            }else{
            }
        }
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

        for (int i=0;i<jasperPrintList.size();i++){
            jasperPrint.addPage(jasperPrintList.get(i).getPages().get(0));
        }
        if(jasperPrintList.size()>=1){ jasperPrint.removePage(0);}

        JasperViewer.viewReport(jasperPrint,false);

    }

    public void showWindown(){
        this.setSize(500,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
