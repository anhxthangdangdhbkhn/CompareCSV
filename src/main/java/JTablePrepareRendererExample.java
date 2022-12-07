import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class JTablePrepareRendererExample {

    // Age Group colors
    Color ageGroup1Color = Color.decode("#FCF3CF");    // Light Yellow
    Color ageGroup2Color = Color.decode("#EBDEF0");    // Light Magenta
    Color ageGroup3Color = Color.decode("#D4EFDF");    // Light Green
    Color ageGroup4Color = Color.decode("#FAD7A0");    // Light Orange
    Color ageGroup5Color = Color.decode("#D4AC0D");    // Gold
    Color ageGroup6Color = Color.decode("#566573");    // Dark Gray

    // Status Group Colors
    Color openColor = Color.decode("#D4E6F1");         // Light Red
    Color closeColor = Color.decode("#FADBD8");        // Light Blue

    // Row Selection Color
    Color selection = Color.decode("#7FB3D5");         // A lighter Blue

    // Table Default Colors
    Color cellsOrigBackColor = new JTable().getBackground();
    Color cellsOrigForeColor = new JTable().getForeground();


    public static void main(String[] args) {
        // So we don't need statics...
        new JTablePrepareRendererExample().startDemo(args);
    }

    private void startDemo(String[] args) {
        initializeForm();
    }

    private void initializeForm() {
        // Create a JFrame Form (with title).
        JFrame frame = new JFrame("JTable Conditional Formatting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set its' Close Operation
        frame.setAlwaysOnTop(true);  // Set that form will be on top of all other applications.
        frame.setSize(new Dimension(400, 250));     // Set the form size.
        frame.setLayout(new BorderLayout());  // Set the form to utilize the Border Layout Manager.

        // Table Column Names
        Object[] columnNames = {"Name", "Age", "Alive", "Case Status"};

        // Table Data...
        Object data[][] = {
                {"Tracey Johnson", 24, "Yes", "Unknown"},
                {"Frank Thetank", 108, "Barely", "Closed"},
                {"Denis Therman", 41, "Yes", "Open"},
                {"Joe Blow", 60, "Yes", "Unknown"},
                {"Fred Flintston", "@1", "No", "Closed"},
                {"John Doe", 73, "Yes", "Open"},
                {"James Brown", 87, "No", "Closed"} };

        // Create a JTable Model and fill the table model with the above Object data...
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        // Declare a JTable and set the Table Model to that table.
        JTable table = new JTable(model) {
            private static final long serialVersionUID = 1L;
            // Here is where all the cell formatting is done in this example
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
                Color cellBackColor = cellsOrigBackColor;  // Original Cells Backgound color.
                Color cellForeColor = cellsOrigForeColor;  // Original Cells Foregound (text) color.


                JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);


                if (columnIndex == 1) {
                    String sAge = getValueAt(rowIndex, 1).toString();
                    int age = -1; // Age Default.
                    /* Make sure the data value in the current Age cell
                       being rendered is indeed a string representation
                       of a Integer (int) numerical value.           */
                    if (sAge.matches("\\d+")) {
                        // Convert the table cell value to Integer
                        age = Integer.parseInt(sAge);
                    }
                    else {
                        /* If it's not a Integer type value then indicate
                           ERROR in cell and highlight RED  */
                        cellBackColor = Color.RED;
                        component.setBackground(cellBackColor);
                        cellForeColor = properTextColor(cellBackColor);
                        component.setForeground(cellForeColor);
                        model.setValueAt("** ERROR **", rowIndex, 1);
                        return component;
                    }

                    if (age <= 25) {
                        cellBackColor = ageGroup1Color;
                    }
                    else if (age >= 26 && age <= 45) {
                        cellBackColor = ageGroup2Color;
                    }
                    else if (age >= 46 && age <= 65) {
                        cellBackColor = ageGroup3Color;
                    }
                    else if (age >= 66 && age <= 80) {
                        cellBackColor = ageGroup4Color;
                    }
                    else if (age >= 81 && age <= 95) {
                        cellBackColor = ageGroup5Color;
                    }
                    else if (age > 95) {
                        cellBackColor = ageGroup6Color;
                    }
                    else {
                        cellBackColor = cellsOrigBackColor;
                    }
                }

                // The 'Status' Field Color Conditions...
                // ======================================
                // Get the 'Status' field value
                String status = getValueAt(rowIndex, 3).toString();
                if (columnIndex == 3) {
                    if (status.equalsIgnoreCase("closed")) {
                        cellBackColor = closeColor;
                    }
                    else if (status.equalsIgnoreCase("open")) {
                        cellBackColor = openColor;
                    }
                    else {
                        cellBackColor = cellsOrigBackColor;
                    }
                }

                /* Set Age cell's determined Background color
                   based on any of the particular conditions
                  stipulated above.               */
                component.setBackground(cellBackColor);
                /* Set cell's Text (foreground) Color based on
                   color and brightness of Cell's Background
                   color.           */
                cellForeColor = properTextColor(cellBackColor); // Helper method
                component.setForeground(cellForeColor);

                // If a row is selected, maintain the conditional formatting...
                if (isRowSelected(rowIndex)) {
                    component.setBackground(selection);
                } else {
                    component.setBackground(cellBackColor);
                }
                return component;
            }
        };

        /* Set the table size, columns not selectable,
           rows selectable via single selection   */
        table.setPreferredSize(new Dimension(400, 250));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* Create a JScrollPane, set its size to Table and
           add the Table to the JScrollPane ViewPort.   */
        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(table.getPreferredSize());
        sp.getViewport().add(table);

        // Create a JPanel to hold JScrollPane (and JTable)
        JPanel tablePanel = new JPanel();
        tablePanel.add(sp);   // Add ScrollPane to JPanel

        /* Create another JPanel to hold a JLabel
           and the JButtons 'Add' and 'Close'  */
        JPanel buttonsPanel = new JPanel();
        // Set this JPanel to utilize a 'Flow Layout Manager'
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center any compnents in panel.

        // Create a JLabel. Shown how to utilize HTML here for a JLabel.
        JLabel doLabel = new JLabel("<html><pre><font color=blue>"
                + "Select a row and edit a cell.      </font></pre>"
                + "</html>");

        // Create the 'Add' JButton.
        JButton addButton = new JButton("Add");
        addButton.setToolTipText(" Add a Table Row ");  // Set a ToolTip for 'Add' Button.
        // Apply an Action Listener to 'Add' Button.
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{"", "0", "", ""});
            }
        });

        // Create the 'Close' JButton.
        JButton closeButton = new JButton("Close");
        closeButton.setToolTipText(" Close Application ");  // Set a ToolTip for 'Close' Button.
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }); // Apply an Action Listener to 'Close' Button.

        buttonsPanel.add(doLabel);      // Add JLabel to Buttons JPanel.
        buttonsPanel.add(addButton);    // Add 'Add' JButton to Buttons JPanel.
        buttonsPanel.add(closeButton);  // Add 'Close' JButton to Buttons JPanel.

        frame.add(tablePanel, BorderLayout.NORTH);   // Add The Table JPanel to top of JFrame Form.
        frame.add(buttonsPanel, BorderLayout.SOUTH); // Add The Buttons JPanel to bottom of JFrame Form.

        frame.pack();   // Pack the components on JFrame.
        frame.setVisible(true); // Make the JFrame form visible.
        frame.setLocationRelativeTo(null);  // Make sure JFrame displays at center Screen.
    }

    /**
     * Returns either the Color WHITE or the Color BLACK dependent upon the
     * brightness of what the supplied background color might be. If the
     * background color is too dark then WHITE is returned. If the background
     * color is too bright then BLACK is returned.<br><br>
     *
     * @param currentBackgroundColor (Color Object) The background color text
     *                               will reside on.<br>
     *
     * @return (Color Object) The color WHITE or the Color BLACK.
     */
    public static Color properTextColor(Color currentBackgroundColor) {
        double L; // Holds the brightness value for the supplied color
        Color determinedColor;  // Default

        // Calculate color brightness from supplied color.
        int r = currentBackgroundColor.getRed();
        int g = currentBackgroundColor.getGreen();
        int b = currentBackgroundColor.getBlue();
        L = (int) Math.sqrt((r * r * .241) + (g * g * .691) + (b * b * .068));

        // Return the required text color to suit the
        // supplied background color.
        if (L > 129) {
            determinedColor = Color.decode("#000000");  // White
        }
        else {
            determinedColor = Color.decode("#FFFFFF");  // Black
        }
        return determinedColor;
    }

}
