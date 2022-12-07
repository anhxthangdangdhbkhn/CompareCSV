import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class JEditorPaneExample {
    JFrame myFrame = null;

    public static void main(String[] a) {
        (new JEditorPaneExample()).test();
    }

    private void test() {

        URL url= JEditorPaneExample.class.getResource("test.htm");
        myFrame = new JFrame("JEditorPane Test");

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(400, 200);
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setContentType("text/html");
//        myPane.setText(appendText("<h1>Sleeping</h1><p>Sleeping is necessary for a healthy body."
//                + " But sleeping in unnecessary times may spoil our health, wealth and studies."
//                + " Doctors advise that the sleeping at improper timings may lead for obesity during the students days.</p>"));

//        jEditorPane.setText(appendText("<b>TM,A,00001003, , ,12, ,419,ﾔﾏﾓﾄﾊﾞﾝｷﾝ, , , ,0,20100615,31,130,5,0,0,0,0,5, ,0, ,1,0,000,289-2303, , ,0,0,   \u000F,00,0, , , , , ,20100615,419,0,000,20210406,0,4,0, , ,0,0,0,0,0,0,0,0,000</b><br>"));
//        myPane.setText(appendText("<h1>\n" +
//                "            Welcome To\n" +
//                "            <font color=\"#FF2626\">G</font>\n" +
//                "            <font color=\"#252A34\">e</font>\n" +
//                "            <font color=\"#753422\">e</font>\n" +
//                "            <font color=\"#FFD523\">k</font>\n" +
//                "            <font color=\"#71EFA3\">s</font>\n" +
//                "            <font color=\"#0F52BA\">for</font>\n" +
//                "            <font color=\"#66CC66\">G</font>\n" +
//                "            <font color=\"#FF9966\">e</font>\n" +
//                "            <font color=\"#FFCCCC\">e</font>\n" +
//                "            <font color=\"#00C1D4\">k</font>\n" +
//                "            <font color=\"#EFE3D0\">s</font>\n" +
//                "        </h1>"));
 //       jEditorPane.setText(appendText("<h1 style=\"color:blue;\">TM,A,00001003, , ,12, ,419,ﾔﾏﾓﾄﾊﾞﾝｷﾝ, , , ,0,20100615,31,130,5,0,0,0,0,5, ,0, ,1,0,000,289-2303, , ,0,0,   \u000F,00,0, , , , , ,20100615,419,0,000,20210406,0,4,0, , ,0,0,0,0,0,0,0,0,000</h1>"));


        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myFrame.setContentPane(jEditorPane);
        myFrame.setVisible(true);
    }

    private StringBuilder sb = new StringBuilder();

    public String appendText(String text) {
        return sb.append(text).toString();
    }
}