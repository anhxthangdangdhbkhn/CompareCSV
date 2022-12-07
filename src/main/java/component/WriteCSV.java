package component;

import hepper.CellOption;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class WriteCSV {
    private String fileName;
    private String path;
    private Map<Integer, CellOption> mapCellOption;
    private JFileChooser jFileChooserExport;

    public WriteCSV(String fileName) {
        this.fileName = fileName;
        this.mapCellOption = new HashMap<>();
    }

    public void write(Vector<String> header, Vector<Vector> vectorCsvData) {
        if(saveFile()){

            try (
                    BufferedWriter writer = Files.newBufferedWriter(Paths.get(path + "\\" + fileName), Charset.forName("SJIS"));
                    CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);)
            {
                for (int i = 0; i < vectorCsvData.size(); i++) {
                    Iterable iterable = new Vector(vectorCsvData.get(i));
                    csvPrinter.printRecord(iterable);


                }
//            csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
//            csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
//            csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");
//
//            csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));

                csvPrinter.flush();
                csvPrinter.close();



                JOptionPane.showConfirmDialog(null,
                        "新規CSV出力完了", "OK!", JOptionPane.DEFAULT_OPTION);

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            JOptionPane.showInternalMessageDialog(null,
                    "CSV出力定義失敗", "OK!", JOptionPane.ERROR_MESSAGE);
        }


    }


    private boolean saveFile(){
        jFileChooserExport = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooserExport.setDialogTitle("Choose a directory to save your file: ");
        jFileChooserExport.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = jFileChooserExport.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if(jFileChooserExport.getSelectedFile().isDirectory()){
                File exportFile = jFileChooserExport.getSelectedFile();
                path = exportFile.getPath();
                System.out.println("You selected the directory: " + jFileChooserExport.getSelectedFile());
                return  true;

            }return false;
        }
        return false;
    }
}
