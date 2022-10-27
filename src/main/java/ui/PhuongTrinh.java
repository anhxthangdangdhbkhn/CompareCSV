package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhuongTrinh extends  JFrame{

    private  JTextField jTextFieldA;
    private JTextField jTextFieldB;
    private JTextField jTextFieldResult;
    private JButton jButtonResult;
    private JButton jButtonExit;
    private JButton jButtonHelp;


    public PhuongTrinh(String title) {
       super(title);
       addControls();
       addEvents();
    }

    ActionListener eventResult = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                    eventResultFun();
        }
    };



    public void showWindow(){
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addControls() {
        Container container = getContentPane();
        JPanel jPanelMain = new JPanel();
        jPanelMain.setLayout(new BoxLayout(jPanelMain,BoxLayout.Y_AXIS));
        container.add(jPanelMain);

        JPanel pnTitle = new JPanel();
        pnTitle.setLayout(new FlowLayout());
        JLabel lblTieuDe = new JLabel("Giai phuong trinh bac 2");
        lblTieuDe.setForeground(Color.BLUE);
        Font fontTieuDe = new Font("arial",Font.BOLD,20);
        lblTieuDe.setFont(fontTieuDe);
        pnTitle.add(lblTieuDe);
        jPanelMain.add(pnTitle);

        JPanel jPanelA = new JPanel();
        jPanelA.setLayout(new FlowLayout());
        JLabel jLabelA = new JLabel("He so A:");
        jPanelA.add(jLabelA);
        jTextFieldA  = new JTextField(15);
        jPanelA.add(jTextFieldA);
        jPanelMain.add(jPanelA);

        JPanel jPanelB = new JPanel();
        jPanelB.setLayout(new FlowLayout());
        JLabel jLabelB = new JLabel("He so B:");
        jPanelB.add(jLabelB);
        jTextFieldB  = new JTextField(15);
        jPanelB.add(jTextFieldB);
        jPanelMain.add(jPanelB);

        JPanel jPanelButton = new JPanel();
        jPanelButton.setLayout(new FlowLayout());
        jButtonExit = new JButton("Exit");
        jButtonResult = new JButton("Result");
        jButtonHelp = new JButton("Help");
        jPanelButton.add(jButtonResult);
        jPanelButton.add(jButtonExit);
        jPanelButton.add(jButtonHelp);
        jPanelMain.add(jPanelButton);


        JPanel jPanelResult = new JPanel();
        jPanelResult.setLayout(new FlowLayout());
        JLabel jLabelResult = new JLabel("Ket qua: ");
        jTextFieldResult = new JTextField(15);
        jPanelResult.add(jLabelResult);
        jPanelResult.add(jTextFieldResult);
        jPanelMain.add(jPanelResult);

    }

    public void addEvents(){
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jButtonResult.addActionListener(eventResult);
        jButtonHelp.addActionListener(new HelEvent());
    }

    public  void  eventResultFun(){
        String A = jTextFieldA.getText();
        String B = jTextFieldB.getText();

        double a = Double.parseDouble(A);
        double b = Double.parseDouble(B);

        if(a==0 && b==0){
            jTextFieldResult.setText("Vo so nghiem");
        }
        else if(a==0 && b!=0){
            jTextFieldResult.setText("Vo nghiem");
        }else {
            double x= -b/a;
            jTextFieldResult.setText("X= "+x);
        }

    }

    class HelEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,"Chi tiet lien he dang thang");
        }
    }
}
