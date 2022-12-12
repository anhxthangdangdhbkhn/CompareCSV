package component;



import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class CSVView {
    private DefaultTableModel model;
    private JTable jTableCsv;
    private JScrollPane jScrollPane;
    private JTableHeader jTableHeader;
    private int selectColumn;
    private int keyColumn;
    private int selectRow;
    private int columnLength;
    private OptionView[] optionViews;
    private Set<Integer> noCheckColumn;


    private TableRowSorter<TableModel> rowSorter;
  //  private List<RowSorter.SortKey> sortKeys;


    public CSVView() {
        this.model = new DefaultTableModel();
        this.rowSorter = new TableRowSorter<>(model);
        this.jTableCsv = new JTable(model){
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {

                Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);

                columnLength = model.getColumnCount();
//                System.out.println("columnLength "+columnLength);
//                System.out.println("rowIndex "+rowIndex);
//                System.out.println("noCheckColumn "+noCheckColumn.size());
                String key = getValueAt(rowIndex, columnLength-1).toString();

                key = key.replaceAll("\\s", "");

                    if(key.equals("new")){
                        component.setBackground(key.equals("new") ?  Color.CYAN :getBackground());
                    }else if (key.equals("old")){
                        component.setBackground(key.equals("old") ? Color.GREEN : getBackground());
                    }else if (key.equals("change")){
                        component.setBackground(key.equals("change") ? Color.YELLOW : getBackground());
                    } else if (key.equals("delete")){
                        component.setBackground(key.equals("delete") ? Color.RED : getBackground());
                    }else {
                        component.setBackground( getBackground());
                    }
                return component;
            }
        };

        this.jScrollPane=new JScrollPane(jTableCsv);
        this.jScrollPane.setColumnHeader(new JViewport());
        this.jScrollPane.setPreferredSize(new Dimension(900, 900));
        this.jTableHeader = this.jTableCsv.getTableHeader();
        this.selectColumn = -1;
        this.keyColumn = 0;
        this.noCheckColumn = new HashSet<>();
        this.selectRow =-1;
        //jTableCsv.addKeyListener(new MyKey());

//        jTableCsv.addKeyListener(new KeyListener() {
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    keyColumn = selectColumn;
//
//                    for(int i=0;i<jTableHeader.getColumnModel().getColumnCount();i++){
//                        jTableHeader.getColumnModel().getColumn(i).setHeaderRenderer(null);
//                    }
//
//                    jTableHeader.getColumnModel().getColumn(keyColumn).setHeaderRenderer(null);
//                    jTableHeader.getColumnModel().getColumn(keyColumn).setHeaderRenderer(new WonHeaderRenderer(Color.RED));
//
//                }
//            }
//
//            public void keyReleased(KeyEvent e) {
//
//            }
//
//            public void keyTyped(KeyEvent e) {
//
//            }
//        });


      //  this.sortKeys = new ArrayList<>();

        //sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
     //   rowSorter.setSortKeys(sortKeys);

        rowSorter.setRowFilter(null);
        this.jTableCsv.setRowSorter(rowSorter);

        addEvents();
    }

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

    public int getSelectColumn() {

        return selectColumn;
    }

    public void setSelectColumn(int selectColumn) {
        this.selectColumn = selectColumn;
    }

    public JTableHeader getjTableHeader() {
        return jTableHeader;
    }

    public void setjTableHeader(JTableHeader jTableHeader) {
        this.jTableHeader = jTableHeader;
    }

    public TableRowSorter<TableModel> getRowSorter() {
        return rowSorter;
    }

    public void setRowSorter(TableRowSorter<TableModel> rowSorter) {
        this.rowSorter = rowSorter;
    }

    public Set<Integer> getNoCheckColumn() {
        return noCheckColumn;
    }

    public void setNoCheckColumn(Set<Integer> noCheckColumn) {
        this.noCheckColumn = noCheckColumn;
    }

    public void addEvents(){

        jTableHeader.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectColumn = jTableHeader.columnAtPoint(e.getPoint());

//                for(int i=0;i<jTableHeader.getColumnModel().getColumnCount();i++){
//                        jTableHeader.getColumnModel().getColumn(i).setHeaderRenderer(null);
//                }

//                System.out.println("Mouse column: " + mouseColumn);
//                System.out.println("Selected KeyColumn: " + keyColumn);

                if(jTableHeader.getColumnModel().getColumn(selectColumn).getHeaderRenderer()==null){
                    noCheckColumn.add(selectColumn);
                    jTableHeader.getColumnModel().getColumn(selectColumn).setHeaderRenderer(new WonHeaderRenderer(Color.RED));
                }else {
                    System.out.println("Clear color");
                    noCheckColumn.remove(selectColumn);
                    jTableHeader.getColumnModel().getColumn(selectColumn).setHeaderRenderer(null);
                }

//                noCheckColumn.forEach(c->{
//                    System.out.println(c);
//                });

               // selectColumn = mouseColumn;
                //optionViews[selectColumn].showWindow();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
//                int mouseColumn = jTableHeader.columnAtPoint(e.getPoint());
//                jTableHeader.getColumnModel().getColumn(mouseColumn).setHeaderRenderer(null);
            }
        } );

    };


    public int getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(int keyColumn) {
        this.keyColumn = keyColumn;
    }

    public OptionView[] getOptionViews() {
        return optionViews;
    }

    public void optionViewsInit() {
        System.out.println("jTable columnLength:" +jTableCsv.getColumnCount());
        this.optionViews = new OptionView[model.getColumnCount()];
        for (int i=0;i< model.getColumnCount();i++){
            this.optionViews[i] = new OptionView();
            viewAction(i);
        }

    }

    public void setOptionViews(OptionView[] optionViews) {
        this.optionViews = optionViews;
    }

    public void viewAction(int selected){
        optionViews[selected].getButtonUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = optionViews[selected].getjTextFieldSearch().getText();
                System.out.println("Column: " + selected + " Search data: " +search);
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + search,selected));
            }
        });

        optionViews[selected].getButtonClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionViews[selected].getjTextFieldSearch().setText(null);
                rowSorter.setRowFilter(null);
            }
        });
    }

    public void tableAction(Vector<String>vectorNewHeader, Map<String,Vector> mapNewBase,Map<String,Vector> mapOldBase){

        jTableCsv.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
            //    System.out.println("me.getClickCount() " +me.getClickCount());
                if (me.getClickCount() == 1) {     // to detect doble click events
                    selectRow = jTableCsv.getSelectedRow();

                    if(selectRow>=0){
                        String key = jTableCsv.getValueAt(selectRow, 2).toString();
                        System.out.println("Key click: "+ key);
                        if(mapNewBase.containsKey(key) && mapOldBase.containsKey(key)){
                            CompareView compareView = new CompareView("Compare",false);
                            compareView.showWindow();
                            compareView.setVectorHeader(vectorNewHeader);

                            ArrayList<Integer> list = new ArrayList<>();


                            Vector newData = mapNewBase.get(key);
                            Vector oldData = mapOldBase.get(key);

                            for(int i=0;i<oldData.size();i++){
                                if(!oldData.get(i).equals(newData.get(i))) list.add(i);
                            }

                            System.out.println("list size: "+ list.size());


                            int changeCell[] = new int[list.size()];

                            for (int i = 0; i < list.size(); i++) changeCell[i] = list.get(i);



                            compareView.addLineVector(newData,changeCell);
                            compareView.addLineVector(oldData,changeCell);
                            compareView.tableFinish();
                        }

                    }

                }
            }
        });



    }




//    public void listCompare(Vector<String>vectorNewHeader, Map<String,Vector> mapNewBase,Map<String,Vector> mapOldBase,Vector vectorKey){
//                        CompareView compareView = new CompareView("Compare");
//                        compareView.showWindow();
//                        compareView.addVectorHeader(vectorNewHeader);
//
//                        vectorKey.forEach(k->{
//                            if(mapNewBase.containsKey(k) && mapOldBase.containsKey(k)){
//                                ArrayList<Integer> list = new ArrayList<>();
//
//                                Vector newData = mapNewBase.get(k);
//                                Vector oldData = mapOldBase.get(k);
//
//                                for(int i=0;i<oldData.size();i++){
//                                    if(!oldData.get(i).equals(newData.get(i))) list.add(i);
//                                }
//
//                                System.out.println("list size: "+ list.size());
//
//                                int changeCell[] = new int[list.size()];
//
//                                for (int i = 0; i < list.size(); i++) changeCell[i] = list.get(i);
//
//                                compareView.addLineVector(newData,changeCell);
//                                compareView.addLineVector(oldData,changeCell);
//                            }
//                        });
//
//                        compareView.tableFinish();
//    }














}