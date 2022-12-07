package component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OptionView {

    private JTextField jTextFieldSearch;
    private JButton buttonUpdate;
    private JButton buttonClear;
    private JComboBox<String> jComboBoxCustomer;
    JFrame frame = new JFrame("Option");
    JPanel panel = new JPanel();

    public OptionView() {
        this.jTextFieldSearch = new JTextField();
        this.buttonUpdate = new JButton("Update");
        this.buttonClear = new JButton("Clear");
        init();
        action();
    }

    void init(){

//        Object[] options = {"Metric","Imperial"};
//        int n = JOptionPane.showOptionDialog(null,
//                "A Message",
//                "A Title",
//                JOptionPane.YES_NO_CANCEL_OPTION,
//                JOptionPane.DEFAULT_OPTION,
//                null,
//                options,
//                options[1]);



//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        JLabel label = new JLabel("Option");
//        JButton button = new JButton();
//        button.setText("Button");
//        panel.add(label);
//        panel.add(button);


        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Search"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(jTextFieldSearch, gbc);

//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        panel.add(new JLabel("Search"), gbc);
//
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        panel.add(new JCheckBox("Delete", false), gbc);



        jComboBoxCustomer = new JComboBox<>();

        jComboBoxCustomer.addItem("-----");
        jComboBoxCustomer.addItem("new");
        jComboBoxCustomer.addItem("old");
        jComboBoxCustomer.addItem("change");
        jComboBoxCustomer.addItem("delete");

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Change "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(jComboBoxCustomer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(buttonClear, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        panel.add(buttonUpdate, gbc);


    }

    public void showWindow(){
        frame.add(panel);
        frame.setSize(350, 400);
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public JTextField getjTextFieldSearch() {
        return jTextFieldSearch;
    }

    public void setjTextFieldSearch(JTextField jTextFieldSearch) {
        this.jTextFieldSearch = jTextFieldSearch;
    }

    public JButton getButtonUpdate() {
        return buttonUpdate;
    }

    public void setButtonUpdate(JButton buttonUpdate) {
        this.buttonUpdate = buttonUpdate;
    }

    public JButton getButtonClear() {
        return buttonClear;
    }

    public void setButtonClear(JButton buttonClear) {
        this.buttonClear = buttonClear;
    }

    public void action(){
        jComboBoxCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxCustomer.getSelectedIndex()>=1){
                    System.out.println("Get index: "+ jComboBoxCustomer.getSelectedIndex());
                }
            }
        });
    }

    //    public void optionAction(CSVView csvView){
//
//        System.out.println("Select column:" +csvView.getSelectColumn());
//
//        csvView.getOptionViews()[csvView.getSelectColumn()-1].getButtonUpdate().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Column: " + csvView.getSelectColumn());
//                String search = csvView.getOptionViews()[csvView.getSelectColumn()].getjTextFieldSearch().toString();
//                System.out.println("Column: " + csvView.getSelectColumn() +"  Search: " +search );
//            }
//        });
//    }
}
