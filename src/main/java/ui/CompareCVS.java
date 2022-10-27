package ui;

import PostgreSQL.CSVRow;
import component.CSVView;
import database.DatabaseConnection;
import hepper.ABC;
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

public class CompareCVS extends JFrame {

    public CompareCVS(String title) throws HeadlessException {
        super(title);
        addControls();
        addEvents();
    }

    JTabbedPane jTabbedPane;
    JButton jButtonCompanyCleanDatabase;
    JButton jButtonNewCsv;

    JButton jButtonImportCsv;
    JButton jButtonClearCsv;

    //JScrollPane jScrollPaneTablePrint;
    JButton jButtonPreview;
    JButton jButtonLocal;
    JComboBox jComboBoxCustomer;
    String stringProductName;

    JPanel jPanelNewCSV ;

    CSVView csvView ;


    private void addControls() {

        Container container = getContentPane();
        jTabbedPane = new JTabbedPane();
        container.add(jTabbedPane);

        jPanelNewCSV = new JPanel();
        JPanel jPanelCompany = new JPanel();
        JPanel jPanelCsv = new JPanel();
        JPanel jPanelHelp = new JPanel();

        jTabbedPane.add(jPanelNewCSV,"NewCSV");
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
       // jPanelCsv.add(jButtonImportCsv);
        jPanelCsv.add(jButtonClearCsv);

        jButtonNewCsv= new JButton("Import New");
        jButtonCompanyCleanDatabase =new JButton("Clear Company");
       // jPanelNewCSV.add(jButtonNewCsv);
        jPanelNewCSV.add(jButtonImportCsv);
        jPanelCompany.add(jButtonCompanyCleanDatabase);


        //jScrollPaneTablePrint = new JScrollPane();
        jButtonPreview= new JButton("Preview");
        jButtonLocal= new JButton("Get data");
        jComboBoxCustomer = new JComboBox();
        //jPanelExport.add(jScrollPaneTablePrint);





        //jPanelNewCSV.add(jButtonLocal);
        jPanelNewCSV.add(jComboBoxCustomer);
       // jPanelNewCSV.add(jButtonPreview);
        jComboBoxCustomer.setVisible(false);






    }

    private void addEvents() {
       // showChooseFileCustomer();
        chooseFileCsv();


    }

    public void showWindown(){
        this.setSize(900,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void showChooseFileCustomer(){

        JFileChooser jFileChooserCustomer = new JFileChooser();
        java.util.List<Object> listSheet = new ArrayList<>();
        jButtonNewCsv.addActionListener(new ActionListener() {
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
                                  //  comboBoxView(listCompany);
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

    private void chooseFileCsv(){
        JFileChooser jFileChooserDBBFFP = new JFileChooser();
        jButtonImportCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int returnVal = jFileChooserDBBFFP.showOpenDialog(jPanelNewCSV);




                if(returnVal == JFileChooser.APPROVE_OPTION){

                    File f = jFileChooserDBBFFP.getSelectedFile();
                    String stringPath = f.getPath();
                    System.out.println(stringPath);

                    if(stringPath.contains(".csv")== true || stringPath.contains("CSV")== true){
                        try {
                            File file = new File(stringPath);
                            InputStreamReader ir = new InputStreamReader(new FileInputStream(file),"SJIS");
                            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ABC.class).parse(ir);



                          //  List<CSVRow> list = new ArrayList<>();
                            int insertCheck =0;

                            int input=0;

                            input = JOptionPane.showConfirmDialog(null, "インポートですか");
                            // 0=yes, 1=no, 2=cancel
                            //System.out.println(input);

                           Vector<String> vec = new Vector<>();
                           Vector<String> data = new Vector<>();
                           // String[] data;

                            CSVRecord r = records.iterator().next();
                            Set<String> setHeader  = r.toMap().keySet();
                            setHeader.forEach(s->{
                                vec.add(s);
                            });
                            r.toMap().forEach((h,d)->{
                                data.add(d);
                            });

                            csvView = new CSVView(vec);
                            jPanelNewCSV.add(csvView.getjScrollPane());

                            if(input==0){

                                for(CSVRecord record:records){
                                    Vector<String> line = new Vector<>();
                                    record.toMap().forEach((k,v)->{
                                        line.add(v);
                                    });
                                    csvView.addRow(line);
                                }



//
//                                if(insertCheck==0) {
//                                    JOptionPane.showMessageDialog(null,
//                                            "CSVインポート出来ない",
//                                            "CSVインポート",
//                                            JOptionPane.INFORMATION_MESSAGE);
//                                }else{
//                                    JOptionPane.showMessageDialog(null,
//                                            "CSVの"+ insertCheck +"行目がインポートされた",
//                                            "CSVインポート",
//                                            JOptionPane.INFORMATION_MESSAGE);
//                                }


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
}
