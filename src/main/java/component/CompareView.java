package component;

import javax.swing.*;

import javax.swing.text.NumberFormatter;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static javax.swing.ScrollPaneConstants.*;

public class CompareView  implements KeyListener {
    private JFrame jFrameCompareView;
    private JPanel jPanelMain;
    private JEditorPane editorPane;
    JScrollPane scrollPane;
    private StringBuilder stringBuilder;
    private StyleSheet styleSheet;
    private HTMLEditorKit htmlEditorKit ;
    private int pages =0;
    private int pageSelect =0;
    private int pageSize =10;
    private JButton buttonNext;
    private JLabel jLabelCurrent;
    private JButton buttonPrevious ;
    private JFormattedTextField  jumPage;
    private JButton buttonJum ;

    private Map<String,Vector> mapNew;
    private Map<String,Vector> mapOld;
    private Vector<String> vectorKeyChange;
    private int changeLineSize;
    private Vector vectorHeader;


    public CompareView(String title,boolean listOption) {
        jFrameCompareView = new JFrame(title);
        jPanelMain = new JPanel();

        buttonNext = new JButton("Next");
        buttonPrevious = new JButton("Previous");
        jLabelCurrent = new JLabel("");
        buttonJum = new JButton("Jum");

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        //formatter.setAllowsInvalid(false);
        jumPage = new JFormattedTextField (formatter);
        jumPage.setColumns(4);




        editorPane = new JEditorPane();
        htmlEditorKit= new HTMLEditorKit();

        editorPane.setContentType("text/html");
        editorPane.setEditorKit(htmlEditorKit);

        jPanelMain.add(buttonPrevious);
        jPanelMain.add(jLabelCurrent);
        jPanelMain.add(buttonNext);

        jPanelMain.add(jumPage);
        jPanelMain.add(buttonJum);



        scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        jPanelMain.add(scrollPane);
        jFrameCompareView.add(jPanelMain);


        stringBuilder = new StringBuilder();
        styleSheet = htmlEditorKit.getStyleSheet();

        mapNew = new HashMap<>();
        mapOld = new HashMap<>();
        vectorKeyChange = new Vector<>();
        vectorHeader = new Vector<>();
        if(listOption == false){
            buttonPrevious.setVisible(false);
            buttonNext.setVisible(false);
            jLabelCurrent.setVisible(false);
            jumPage.setVisible(false);
            buttonJum.setVisible(false);
        }

        controller();

    }
    public void showWindow(){
        //jFrameCompareView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameCompareView.setSize(1000, 1000);
        jFrameCompareView.setVisible(true);
        cssInit();






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
//        Element elem = htmlDocument.getElement("tr");
//        try {
//            htmlDocument.insertBeforeEnd(elem,String.format("<p>%s</p>", LocalTime.now().toString()));
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//
//        try {
//            htmlEditorKit.insertHTML(htmlDocument, 200, "<td>world</td>", 0, 0, null);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    public void createVectorHeader(){
        String line = "<table id =\"table\" style=\"width:100%\"> ";
        line = line + "<tr>";

        for(int i=0;i<vectorHeader.size();i++){
            line = line + "<th> "+  vectorHeader.get(i) +"</th>";
        }
        line = line + " </tr>";
        editorPane.setText(appendText(line));
        editorPane.setText(appendText("<tbody>"));
    }
    public void tableFinish(){
        editorPane.setText(appendText("</tbody>"));
        editorPane.setText(appendText(" </table>"));
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
                "} tbody {overflow: scroll;}");

        //styleSheet.addRule("tbody {overflow: scroll;}");
    }


    public void listCompare(Vector vectorKey){
        pages = changeLineSize/10;
        jLabelCurrent.setText(pageSelect+"/"+pages);
        vectorKey.forEach(k->{
            if(mapNew.containsKey(k) && mapOld.containsKey(k)){
                ArrayList<Integer> list = new ArrayList<>();

                Vector newData = mapNew.get(k);
                Vector oldData = mapOld.get(k);

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

    public void tableCompare(Vector vectorKey){
        pages = changeLineSize/pageSize;
        jLabelCurrent.setText(pageSelect+"/"+pages);

        stringBuilder = new StringBuilder();
        editorPane.setText("");

        createVectorHeader();


        vectorKey.forEach(k->{
            if(mapNew.containsKey(k) && mapOld.containsKey(k)){
                ArrayList<Integer> list = new ArrayList<>();

                Vector newData = mapNew.get(k);
                Vector oldData = mapOld.get(k);

                for(int i=0;i<oldData.size();i++){
                    if(!oldData.get(i).equals(newData.get(i))) list.add(i);
                }

//                System.out.println("list size: "+ list.size());

                int changeCell[] = new int[list.size()];

                for (int i = 0; i < list.size(); i++) changeCell[i] = list.get(i);

                addLineVector(newData,changeCell);
                addLineVector(oldData,changeCell);
            }
        });
        tableFinish();
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

    void controller(){

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pageSelect<pages){

                    pageSelect = pageSelect+1;
                    Vector key = new Vector<>();
                    for(int i=pageSelect*10;i<pageSelect*10+10 ;i++){
                        if(i <= changeLineSize) key.add(vectorKeyChange.get(i));
                    }
                    tableCompare(key);
                    jLabelCurrent.setText(pageSelect+"/"+pages);
                }

            }
        });
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pageSelect>0){
                    pageSelect = pageSelect-1;
                    Vector key = new Vector<>();
                    for(int i=pageSelect*pageSize;i<pageSelect*pageSize+pageSize ;i++){
                        if(i <= changeLineSize) key.add(vectorKeyChange.get(i));
                    }
                    tableCompare(key);
                    jLabelCurrent.setText(pageSelect+"/"+pages);
                }

            }
        });
        buttonJum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jumPage.getValue()==null){
                    JOptionPane.showMessageDialog(null, "移動したいページ欄を入力する必要");
                }else{
                    int index =Integer.parseInt(jumPage.getValue().toString());
                    if( 0<index && index<changeLineSize/pageSize){
                        pageSelect = index;
                        Vector key = new Vector<>();
                        for(int i=pageSelect*pageSize;i<pageSelect*pageSize+pageSize ;i++){
                            if(i <= changeLineSize) key.add(vectorKeyChange.get(i));
                        }
                        tableCompare(key);
                        jLabelCurrent.setText(pageSelect+"/"+pages);
                    }else {
                        JOptionPane.showMessageDialog(null, "移動したいページの範囲は不可");
                    }
                }

            }
        });

        buttonNext.setMnemonic(KeyEvent.VK_UP);
        buttonPrevious.setMnemonic(KeyEvent.VK_DOWN);

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

    public Map<String, Vector> getMapNew() {
        return mapNew;
    }

    public void setMapNew(Map<String, Vector> mapNew) {
        this.mapNew = mapNew;
    }

    public Map<String, Vector> getMapOld() {
        return mapOld;
    }

    public void setMapOld(Map<String, Vector> mapOld) {
        this.mapOld = mapOld;
    }

    public Vector<String> getVectorKeyChange() {
        return vectorKeyChange;
    }

    public void setVectorKeyChange(Vector<String> vectorKeyChange) {
        this.vectorKeyChange = vectorKeyChange;
    }

    public int getChangeLineSize() {
        return changeLineSize;
    }

    public void setChangeLineSize(int changeLineSize) {
        this.changeLineSize = changeLineSize;
    }

    public Vector getVectorHeader() {
        return vectorHeader;
    }

    public void setVectorHeader(Vector vectorHeader) {
        Vector vector = vectorHeader;
//        String check = vector.get(vector.size()-1).toString();
//        if(check.equals("Check")){
//            vector.removeElementAt(vector.size()-1);
//        }
        String line = "<table id =\"table\" style=\"width:100%\"> ";
        line = line + "<tr>";

        for(int i=0;i<vector.size();i++){
            line = line + "<th> "+  vector.get(i) +"</th>";
        }
        line = line + " </tr>";
        editorPane.setText(appendText(line));
        this.vectorHeader = vector;
    }
}
