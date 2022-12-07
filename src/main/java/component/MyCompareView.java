package component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.io.IOException;
import java.util.Vector;


@Slf4j
@Setter
@Getter
public class MyCompareView {
    private JFrame jFrameCompareView;
    private JEditorPane editorPane;
    private StringBuilder stringBuilder;
    private StyleSheet styleSheet;
    private HTMLEditorKit htmlEditorKit ;
    private HTMLDocument htmlDocument;



    public MyCompareView(String title) {
        jFrameCompareView = new JFrame(title);
        editorPane = new JEditorPane();
        htmlEditorKit= new HTMLEditorKit();
        editorPane.setEditorKit(htmlEditorKit);


        stringBuilder = new StringBuilder();
        styleSheet = htmlEditorKit.getStyleSheet();
        htmlDocument = new HTMLDocument(styleSheet);
        editorPane.setDocument(htmlDocument);
        styleSheet.addRule("table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}");

    }
    public void showWindow(){
        //jFrameCompareView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameCompareView.setSize(400, 200);
        jFrameCompareView.setVisible(true);
        jFrameCompareView.setContentPane(editorPane);

    }
    public String appendText(String text) {
        return stringBuilder.append(text).toString();
    }
    public  void addAline(){
//        Element elem = htmlDocument.getElement("table");
//        try {
//            htmlDocument.insertBeforeEnd(elem,String.format("<p>%s</p>", LocalTime.now().toString()));
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        try {
//            htmlEditorKit.insertHTML(htmlDocument, htmlDocument.getLength(), "<font color='red'><u>world</u></font>", 0, 0, null);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
    public void addRow(String row){



        editorPane.setText(appendText(row));

//        try {
//            htmlEditorKit.insertHTML(htmlDocument, htmlDocument.getLength(), row, 0, 0, null);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void addVectorHeader(Vector vector){
        String line = "<table id =\"table\" style=\"width:100%\"> ";
        line = line + "<tr>";

        for(int i=0;i<vector.size();i++){
            line = line + "<th> "+  vector.get(i) +"</th>";
        }
        line = line + " </tr>";
        editorPane.setText(appendText(line));
    }
    public void tableFinish(){
        //editorPane.setText(appendText(" </table>")); try {

        try {
            htmlEditorKit.insertHTML(htmlDocument,htmlDocument.getLength(), " </table>", 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addLineVector(Vector vector, int change){
        String line = "<tr> ";

        for(int i=0;i<vector.size()-1;i++){
            line = line +"<td>";
            String cell = vector.get(i).toString();
            if(change==1){
                cell = "<font color=\"#FF2626\"> "  + cell + " </font>";
                change = 5;
            }else {
                //  cell = "<font color=\"#00C1D4\"> "  + cell + " </font>";
            }
            line = line + cell;
            line = line +"</td>";
        }
        line = line + " </tr>";
        editorPane.setText(appendText(line));

    }

    public void addLineVector(Vector vector, int change[]){
        String line = "<tr> ";

        for(int i=0;i<change.length;i++){
            String cell = vector.get(change[i]).toString();
            cell = "<font color=\"#FF2626\"> "  + cell + " </font>";
            vector.set(change[i],cell);
        }

        for(int i=0;i<vector.size()-1;i++){
            line = line +"<td>";
            String cell = vector.get(i).toString();
            //  cell = "<font color=\"#00C1D4\"> "  + cell + " </font>";
            line = line + cell;
            line = line +"</td>";
        }
        line = line + " </tr>";
        editorPane.setText(appendText(line));
    }

    public void addDocument(Vector vector){

//        Element[] roots = htmlDocument.getRootElements();
//        Element body = null;
//        for (int i=0;i<roots[0].getElementCount();i++){
//            Element element = roots[0].getElement( i );
//
//            if( element.getAttributes().getAttribute( StyleConstants.NameAttribute ) == HTML.Tag.BODY ) {
//                body = element;
//                break;
//            }
//        }
//
//
//        log.info("Element {}",body.getElementCount());

        String line = "<table id =\"table\" style=\"width:100%\"> ";
        line = line + "<tr>";

        for(int i=0;i<vector.size();i++){
            line = line + "<th> "+  vector.get(i) +"</th>";
        }
        line = line + " </tr>";

        try {
            htmlEditorKit.insertHTML(htmlDocument,htmlDocument.getLength(), line, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addAlineTable(String line){
        log.info("html a line: {}",line);

        try {
            htmlEditorKit.insertHTML(htmlDocument,htmlDocument.getLength(), line, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
