package component;


import hepper.JapaneseMode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

@Setter
@Getter
@Slf4j
public class JComboTOption {
    private JComboBox jComboBox;
    private JapaneseMode japaneseMode;
  //  private int selected;

    public JComboTOption() {
        Vector vector = new Vector<>();
        vector.add("制約なし");
        vector.add("全角のみ");
       // vector.add("全角カタカナのみ");
        vector.add("半角のみ");
//        vector.add("半角カタカのみ");
//        vector.add("半角英数のみ");
//        vector.add("半角英数大文字のみ");
//        vector.add("半角英数小文字のみ");
//        vector.add("複在不可");
        this.jComboBox = new JComboBox<>(vector);
        this.jComboBox.setPrototypeDisplayValue("なし");

        addEvents();

    }
    void addEvents(){
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBox.getSelectedIndex()>=1){
                    int selected = jComboBox.getSelectedIndex();
                    japaneseMode = JapaneseMode.fromOrdinal(JapaneseMode.class,selected);
                    log.info("jComboBox {}",selected);
                    log.info("jComboBox japaneseMode {}",japaneseMode);
                }
            }
        });
    }
}
