package test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SwingTableTest {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Java Swing Table");
        JLabel selected_file = new JLabel();
        frame.setSize(900, 900);
        JButton btn_selectfile = new JButton("Select File");
        btn_selectfile.setBounds(0, 0, 95, 30);
        selected_file.setBounds(0, 5, 100, 200);
        frame.add(btn_selectfile);
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 900, 900);
        JTable jTable1 = new JTable();
        JScrollPane sp = new JScrollPane(jTable1);
        frame.add(selected_file);
        panel.add(sp);
        frame.add(panel);

        btn_selectfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser();

                int i = filechooser.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = filechooser.getSelectedFile();
                    String filepath = f.getPath();
                    String fi = f.getName();
                    //Parsing CSV Data
                    System.out.print(filepath);
                    selected_file.setText(fi);
                    DefaultTableModel csv_data = new DefaultTableModel();

                    try {

                        int start = 0;
                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath));
                        CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);
                        for (CSVRecord csvRecord : csvParser) {
                            if (start == 0) {
                                start = 1;
                                csv_data.addColumn(csvRecord.get(0));
                                csv_data.addColumn(csvRecord.get(1));
                                csv_data.addColumn(csvRecord.get(2));
                                csv_data.addColumn(csvRecord.get(3));
                            } else {
                                Vector row = new Vector();
                                row.add(csvRecord.get(0));
                                row.add(csvRecord.get(1));
                                row.add(csvRecord.get(2));
                                row.add(csvRecord.get(3));
                                csv_data.addRow(row);
                            }
                        }

                    } catch (Exception ex) {
                        System.out.println("Error in Parsing CSV File");
                    }

                    jTable1.setModel(csv_data);

                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
