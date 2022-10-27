package component;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;



public class CSVView {
    private DefaultTableModel model;
    private JTable jTableCsv;
    private JScrollPane jScrollPane;
    private JTableHeader jTableHeader;

    public CSVView(Vector<String> vec) {
        this.model = new DefaultTableModel(vec,0);
        this.jTableCsv = new JTable(model);
        this.jScrollPane=new JScrollPane(jTableCsv);
        this.jScrollPane.setColumnHeader(new JViewport());
        this.jScrollPane.setPreferredSize(new Dimension(1400, 900));
        this.jTableHeader = this.jTableCsv.getTableHeader();
        addEvents();
    }

    public void init(Vector<String> vec) {
        this.model = new DefaultTableModel(vec,0);
        this.jTableCsv = new JTable(model);

    }

//    public void showWindown(){
//        setSize(900,300);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }


    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JTable getjTableCsv() {
        return jTableCsv;
    }

    public void setjTableCsv(JTable jTableCsv) {
        this.jTableCsv = jTableCsv;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public void setjScrollPane(JScrollPane jScrollPane) {
        this.jScrollPane = jScrollPane;
    }

    public void addRow(Vector<String> line) {
        this.model.addRow(line);
    }

    public void addEvents(){
        jTableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int selectedColumn = jTableHeader.columnAtPoint(e.getPoint());
                TableColumn tableColumn = jTableCsv.getColumnModel().getColumn(selectedColumn);

                if(tableColumn.getCellRenderer() ==null){
                    tableColumn.setCellRenderer(new ColumnColorRenderer(Color.lightGray, Color.red));
                }else{
                    tableColumn.setCellRenderer(null);
                }


                //jTableCsv.removeColumn(tableColumn);
               // jTableCsv.getColumnModel().getColumn(col).

            }
        });
    }
}
class ColumnColorRenderer extends DefaultTableCellRenderer {
    Color backgroundColor, foregroundColor;
    public ColumnColorRenderer(Color backgroundColor, Color foregroundColor) {
        super();
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(backgroundColor);
        cell.setForeground(foregroundColor);
        return cell;
    }
}

class columnRenderer extends  DefaultTableColumnModel{

}