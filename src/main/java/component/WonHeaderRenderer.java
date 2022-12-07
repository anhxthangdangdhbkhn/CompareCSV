package component;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class WonHeaderRenderer extends JLabel implements TableCellRenderer {

    public WonHeaderRenderer(Color c) {
        //  setFont(new Font("Consolas", Font.BOLD, 14));
        setOpaque(true);
        setForeground(Color.WHITE);
        setBackground(c);
        setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }

}
