package component;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class CompareView  implements KeyListener {
    private JFrame jFrameCompareView;
    private JEditorPane editorPane;
    private StringBuilder stringBuilder;
    private StyleSheet styleSheet;
    private HTMLEditorKit htmlEditorKit ;
    private HTMLDocument htmlDocument;
    private int pages =0;
    private int pageSelect =0;
    private JButton buttonNext = new JButton("Next");
    private JButton buttonPrevious = new JButton("Previous");



    public CompareView(String title) {
        jFrameCompareView = new JFrame(title);
        jFrameCompareView.add(buttonPrevious);
        jFrameCompareView.add(buttonNext);
        editorPane = new JEditorPane();
        htmlEditorKit= new HTMLEditorKit();

        editorPane.setContentType("text/html");
        editorPane.setEditorKit(htmlEditorKit);


        stringBuilder = new StringBuilder();
        styleSheet = htmlEditorKit.getStyleSheet();
        htmlDocument = (HTMLDocument) editorPane.getDocument();

    }
    public void showWindow(){
        //jFrameCompareView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameCompareView.setSize(400, 200);
        jFrameCompareView.setVisible(true);
        cssInit();



        jFrameCompareView.add(editorPane);
       // editorPane.setText("<html><body id='body'>image</body></html>");
//        editorPane.setText(appendText("<h1>\n" +
//                "            Welcome To\n" +
//                "            <font color=\"#FF2626\">情</font>\n" +
//                "            <font color=\"#252A34\">報</font>\n" +
//                "            <font color=\"#753422\">シ</font>\n" +
//                "            <font color=\"#FFD523\">ス</font>\n" +
//                "            <font color=\"#71EFA3\">テ</font>\n" +
//                "            <font color=\"#0F52BA\">ム</font>\n" +
//                "            <font color=\"#66CC66\">課</font>\n" +
//                "            <font color=\"#FF9966\">T</font>\n" +
//                "            <font color=\"#FFCCCC\">h</font>\n" +
//                "            <font color=\"#00C1D4\">a</font>\n" +
//                "            <font color=\"#EFE3D0\">n</font>\n" +
//                "            <font color=\"#EFE3A0\">g</font>\n" +
//                "        </h1>"));



//        editorPane.setText(appendText("<h1><font color=\"#FF2626\">Dang</font> <font color=\"#252A34\">Duc</font> </h1>"));
//        editorPane.setText(appendText("<h1 style=\"color:blue;\">This is a heading</h1>"));

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
        editorPane.setText(appendText(" </table>"));

        editorPane.setText(appendText(" <h2>" + pageSelect+"/"+pages +"</h2>"));
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
    public void cssInit(){
        styleSheet.addRule("table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}");
    }


    public void listCompare(Map<String,Vector> mapNewBase, Map<String,Vector> mapOldBase, Vector vectorKey){
        vectorKey.forEach(k->{
            if(mapNewBase.containsKey(k) && mapOldBase.containsKey(k)){
                ArrayList<Integer> list = new ArrayList<>();

                Vector newData = mapNewBase.get(k);
                Vector oldData = mapOldBase.get(k);

                for(int i=0;i<oldData.size();i++){
                    if(!oldData.get(i).equals(newData.get(i))) list.add(i);
                }

                System.out.println("list size: "+ list.size());

                int changeCell[] = new int[list.size()];

                for (int i = 0; i < list.size(); i++) changeCell[i] = list.get(i);

                addLineVector(newData,changeCell);
                addLineVector(oldData,changeCell);
            }
        });

    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSelect() {
        return pageSelect;
    }

    public void setPageSelect(int pageSelect) {
        this.pageSelect = pageSelect;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("ENTER");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("ENTER");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
