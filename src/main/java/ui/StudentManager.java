package ui;

import database.StudentConnect;
import student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManager extends JFrame {

    JTextField jTextFieldName;
    JTextArea jTextAreaAddress;
    JButton jButtonSave;

    JCheckBox jCheckBoxSwim,jCheckBoxMovie;
    JRadioButton jRadioButtonMale,jRadioButtonFemale;
    ButtonGroup buttonGroupGender;

    JFileChooser jFileChooserDBBFFP;

    public StudentManager(String title){
        super(title);
        addControls();
        addEvents();
    }

    public void showWindow(){
        setSize(450,250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addControls(){
        Container container = getContentPane();
        JPanel jPanelMain = new JPanel();
        jPanelMain.setLayout(new BoxLayout(jPanelMain,BoxLayout.Y_AXIS));
        container.add(jPanelMain);

        JPanel jPanelInformation = new JPanel();
        jPanelInformation.setLayout(new BoxLayout(jPanelInformation,BoxLayout.Y_AXIS));

        JPanel jPanelName = new JPanel();
        jPanelName.setLayout(new FlowLayout());
        JLabel jLabelName = new JLabel("Enter a name");
        jTextFieldName = new JTextField(17);
        jPanelName.add(jLabelName);
        jPanelName.add(jTextFieldName);
        jPanelInformation.add(jPanelName);
        jPanelMain.add(jPanelInformation);



        JPanel jPanelAddress = new JPanel();
        jPanelAddress.setLayout(new FlowLayout());
        JLabel jLabelAddress = new JLabel("Enter a address");
        jTextAreaAddress = new JTextArea(5,20);
        jPanelAddress.add(jTextAreaAddress);

        JScrollPane jScrollPaneAddress = new JScrollPane(jTextAreaAddress,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        jPanelAddress.add(jLabelAddress);

        jPanelAddress.add(jScrollPaneAddress);
        jPanelInformation.add(jPanelAddress);
        jPanelMain.add(jPanelInformation);



        JPanel jPanelInterestAndGender = new JPanel();
        jPanelInterestAndGender.setLayout(new BoxLayout(jPanelInterestAndGender,BoxLayout.X_AXIS));


        JPanel jPanelInterest = new JPanel();
        jPanelInterest.setLayout(new BoxLayout(jPanelInterest,BoxLayout.Y_AXIS));
        jCheckBoxSwim = new JCheckBox("Swim");
        jCheckBoxMovie = new JCheckBox("Movie");
        jPanelInterest.add(jCheckBoxSwim);
        jPanelInterest.add(jCheckBoxMovie);
        jPanelInterestAndGender.add(jPanelInterest);
        jPanelMain.add(jPanelInterestAndGender);


        JPanel jPanelGender = new JPanel();
        jPanelGender.setLayout(new BoxLayout(jPanelGender,BoxLayout.Y_AXIS));
        jRadioButtonFemale = new JRadioButton("Female");
        jRadioButtonMale = new JRadioButton("Male");
        buttonGroupGender = new ButtonGroup();
        jPanelGender.add(jRadioButtonFemale);
        jPanelGender.add(jRadioButtonMale);
        jPanelInterestAndGender.add(jPanelGender);
        jPanelMain.add(jPanelInterestAndGender);



        JPanel jPanelSave = new JPanel();
        jPanelSave.setLayout(new FlowLayout());
        jButtonSave = new JButton("Save");
        jPanelSave.add(jButtonSave);
        jPanelMain.add(jPanelSave);


    }

    public void addEvents(){

        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentConnect studentConnect = new StudentConnect();
                Student student = new Student("dang","thang","anhxthangdang");
                long re = studentConnect.insertStudent(student);
                System.out.println("Re " + re);
            }
        });
    }


}
