package component;

import eenum.ECsvCharset;
import eenum.JapaneseMode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
@Setter
@Getter
public class CSVSettingUI {
    private JFrame jFrameMain;
    private JPanel jPanelMain;
    private CSVSetting csvSetting;
    private JButton jButtonSave;
    private JButton jButtonExit;
    private JComboBox jComboBoxICharset;
    private JComboBox jComboBoxOCharset;

    public CSVSettingUI(String title) {
        this.jFrameMain = new JFrame(title);
        this.jPanelMain = new JPanel(new GridLayout(3,2));
        this.csvSetting = new CSVSetting();
        jFrameMain.add(jPanelMain);
        addEvents();
        addControls();
    }

    public CSVSettingUI(String title, CSVSetting csvSet) {
        this.jFrameMain = new JFrame(title);
        this.jPanelMain = new JPanel(new GridLayout(3,2));
        this.csvSetting = csvSet;
        jFrameMain.add(jPanelMain);
        addEvents();
        addControls();
    }

    private void addControls() {
        jComboBoxICharset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        jComboBoxOCharset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxICharset.getSelectedIndex()>=0){
                    int selected = jComboBoxICharset.getSelectedIndex();
                    csvSetting.setInputECharset(ECsvCharset.fromOrdinal(ECsvCharset.class,selected));
                    log.info("jComboBoxICharset {}",ECsvCharset.fromOrdinal(ECsvCharset.class,selected));
                }
                if(jComboBoxOCharset.getSelectedIndex()>=0){
                    int selected = jComboBoxOCharset.getSelectedIndex();
                    csvSetting.setOutECharset(ECsvCharset.fromOrdinal(ECsvCharset.class,selected));
                    log.info("jComboBoxOCharset {}",ECsvCharset.fromOrdinal(ECsvCharset.class,selected));
                }
            }
        });
    }

    private void addEvents() {

        jButtonSave = new JButton("Save");
        jButtonExit = new JButton("Exit");
        jComboBoxICharset = new JComboBox<>(csvSetting.getVectorCharset());
        jComboBoxOCharset = new JComboBox<>(csvSetting.getVectorCharset());

        jPanelMain.add(new JLabel("インプット文字コード"));
        jPanelMain.add(jComboBoxICharset);
        jPanelMain.add(new JLabel("OUT文字コード"));
        jPanelMain.add(jComboBoxOCharset);
        jPanelMain.add(jButtonSave);
        jPanelMain.add(jButtonExit);
    }

    public void showWindow(){
        //jFrameCompareView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameMain.setSize(240, 100);
        jFrameMain.setVisible(true);

    }
}
