import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablePanel extends JPanel {
    private static TablePanel instance;

    private JTable eulerTable;
    private JTable midpointTable;

    private DefaultTableModel eulerTableModel;
    private DefaultTableModel midpointTableModel;

    private TablePanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 0, Color.BLACK));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Euler's Method Table
        String[] columnNames = {"t", "Sx", "Sy", "Vx", "Vy", "dSx", "dSy", "dVx", "dVy"};
        eulerTableModel = new DefaultTableModel(columnNames, 0);
        eulerTable = new JTable(eulerTableModel);
        JScrollPane eulerScrollPane = new JScrollPane(eulerTable);

        JPanel eulerPanel = new JPanel(new BorderLayout());
        eulerPanel.setBorder(BorderFactory.createTitledBorder("Euler's Method"));
        eulerPanel.add(eulerScrollPane, BorderLayout.CENTER);
        eulerPanel.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(eulerPanel, gbc);

        // Midpoint Method Table
        midpointTableModel = new DefaultTableModel(columnNames, 0);
        midpointTable = new JTable(midpointTableModel);
        JScrollPane midpointScrollPane = new JScrollPane(midpointTable);

        JPanel midpointPanel = new JPanel(new BorderLayout());
        midpointPanel.setBorder(BorderFactory.createTitledBorder("Midpoint Method"));
        midpointPanel.add(midpointScrollPane, BorderLayout.CENTER);
        midpointPanel.setBackground(Color.WHITE);

        gbc.gridy = 1;
        add(midpointPanel, gbc);
    }

    public void addEulerRow(Object[] row) {
        eulerTableModel.addRow(row);
        eulerTable.repaint();
        eulerTable.revalidate();
    }

    public void addMidpointRow(Object[] row) {
        midpointTableModel.addRow(row);
        midpointTable.repaint();
        midpointTable.revalidate();
    }

    public void reset() {
        eulerTableModel.setRowCount(0);
        midpointTableModel.setRowCount(0);
    }

    public static TablePanel getInstance() {
        if (instance == null)
            instance = new TablePanel();

        return instance;
    }
}
