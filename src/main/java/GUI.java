import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private static GUI instance;
    GridBagConstraints gbc;

    JPanel chart;
    JPanel controls;
    JPanel table;

    boolean isChart;

    private GUI() {
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        chart = ChartPanel.getInstance();
        table = TablePanel.getInstance();
        add(chart, gbc);
        isChart = true;
        gbc.gridx = 1;
        gbc.weightx = 0.2;

        controls = new ControlsPanel();
        add(controls, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchToChart() {
        getContentPane().remove(table);
        gbc.gridx = 0;
        gbc.weightx = 0.75;
        add(chart, gbc);
        isChart = true;
        revalidate();
        repaint();
    }

    public void switchToTable() {
        getContentPane().remove(chart);
        gbc.gridx = 0;
        gbc.weightx = 0.75;
        add(table, gbc);
        isChart = false;
        revalidate();
        repaint();
    }

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI();

        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::getInstance);
    }
}
