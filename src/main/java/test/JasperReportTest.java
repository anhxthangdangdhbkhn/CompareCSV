package test;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import ui.FMPGM;

import java.util.HashMap;

public class JasperReportTest {
    public static void main(String args[]){

        String jasperFilePath =  JasperReportTest.class.getResource("/JasperReport_A4.jasper").getPath();
        System.out.println(jasperFilePath);

        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        JasperPrint jasperPrint= null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperFilePath, parameterMap, new JREmptyDataSource());
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.setVisible(true);


        } catch (JRException e) {
            e.printStackTrace();
        }
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, "jasper.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }


//        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
//        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperFilePath, parameterMap, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(jasperPrint, "jasper.pdf");
//
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(new ClassPathResource("/jasper_report_template.jrxml").getInputStream(), parameters, new JREmptyDataSource());
//        JRPdfExporter pdfExporter = new JRPdfExporter();
//        pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportOutput));
//        try {
//            pdfExporter.exportReport();
//        } catch (JRException e) {
//            e.printStackTrace();
//        }


    }
}
