package ui;


import panel.PanelCompareCsv;
import panel.PanelEditCsv;


import javax.swing.*;
import java.awt.*;

public class CompareCVS extends JFrame {

    public CompareCVS(String title) throws HeadlessException {
        super(title);
        addControls();
        addEvents();
    }

    private JTabbedPane jTabbedPane;

    private void addControls() {

        Container container = getContentPane();
        jTabbedPane = new JTabbedPane();
        container.add(jTabbedPane);

        jTabbedPane.add(new PanelCompareCsv().getJPanelCSV(),"Csv比較");
        jTabbedPane.add(new PanelEditCsv().getJPanelEditCsv(),"Csv処理");
        jTabbedPane.add(new JPanel(),"ツール設定");
        jTabbedPane.add(new JPanel(),"使用方法説明");

        JPanel jPanelHelpLayout = new JPanel();
        jPanelHelpLayout.setLayout(new BoxLayout(jPanelHelpLayout,BoxLayout.Y_AXIS));

    }

    public void showWindow(){
        this.setSize(1500,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addEvents() {


    }

}
