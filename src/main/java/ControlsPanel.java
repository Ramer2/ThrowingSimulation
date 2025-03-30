import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlsPanel extends JPanel {
    JTextField Et0 = new JTextField("0", 10);
    JTextField Edt = new JTextField("0.05", 10);
    JTextField EInitX = new JTextField("0", 10);
    JTextField EInitY = new JTextField("0", 10);
    JTextField EInitSpdX = new JTextField("10", 10);
    JTextField EInitSpdY = new JTextField("10", 10);
    JTextField Ek = new JTextField("0.47", 10);
    JTextField Em = new JTextField("10", 10);

    JTextField Mt0 = new JTextField("0", 10);
    JTextField Mdt = new JTextField("0.05", 10);
    JTextField MInitX = new JTextField("0", 10);
    JTextField MInitY = new JTextField("0", 10);
    JTextField MInitSpdX = new JTextField("10", 10);
    JTextField MInitSpdY = new JTextField("10", 10);
    JTextField Mk = new JTextField("0.47", 10);
    JTextField Mm = new JTextField("10", 10);

    public ControlsPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Euler's method
        JPanel eulerPanel = getEulerPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(eulerPanel, gbc);

        // Midpoint method
        JPanel midpointPanel = getMidpointPanel();
        gbc.gridy = 1;
        add(midpointPanel, gbc);

        // buttons section
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new FlowLayout());
        JButton calculateGraphButton = new JButton("Calculate graph");
        calculateGraphButton.addMouseListener(getCalculateChartMouseAdapter());
        JButton calculateTableButton = new JButton("Calculate table");
        calculateTableButton.addMouseListener(getCalculateTableMouseAdapter());
        JButton exitButton = new JButton("Exit");
        exitButton.addMouseListener(getExitMouseAdapter());

        buttonsPanel.add(calculateGraphButton);
        buttonsPanel.add(calculateTableButton);
        buttonsPanel.add(exitButton);

        gbc.gridy = 2;
        add(buttonsPanel, gbc);
    }

    private JPanel getEulerPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Euler's Method"));
        panel.setLayout(new GridLayout(8, 2, 5, 5));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("t0:"));
        panel.add(Et0);
        panel.add(new JLabel("Delta t:"));
        panel.add(Edt);
        panel.add(new JLabel("Initial x:"));
        panel.add(EInitX);
        panel.add(new JLabel("Initial y:"));
        panel.add(EInitY);
        panel.add(new JLabel("Initial speed x:"));
        panel.add(EInitSpdX);
        panel.add(new JLabel("Initial speed y:"));
        panel.add(EInitSpdY);
        panel.add(new JLabel("k resistance:"));
        panel.add(Ek);
        panel.add(new JLabel("Mass of object:"));
        panel.add(Em);
        
        return panel;
    }

    private JPanel getMidpointPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Midpoint Method"));
        panel.setLayout(new GridLayout(8, 2, 5, 5));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("t0:"));
        panel.add(Mt0);
        panel.add(new JLabel("Delta t:"));
        panel.add(Mdt);
        panel.add(new JLabel("Initial x:"));
        panel.add(MInitX);
        panel.add(new JLabel("Initial y:"));
        panel.add(MInitY);
        panel.add(new JLabel("Initial speed x:"));
        panel.add(MInitSpdX);
        panel.add(new JLabel("Initial speed y:"));
        panel.add(MInitSpdY);
        panel.add(new JLabel("k resistance:"));
        panel.add(Mk);
        panel.add(new JLabel("Mass of object:"));
        panel.add(Mm);

        return panel;
    }

    private MouseAdapter getExitMouseAdapter() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        };
    }

    private MouseAdapter getCalculateChartMouseAdapter() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    GUI.getInstance().switchToChart();
                    double valEt0 = Double.parseDouble(Et0.getText());
                    double valEdt = Double.parseDouble(Edt.getText());
                    double valEInitX = Double.parseDouble(EInitX.getText());
                    double valEInitY = Double.parseDouble(EInitY.getText());
                    double valEInitSpdX = Double.parseDouble(EInitSpdX.getText());
                    double valEInitSpdY = Double.parseDouble(EInitSpdY.getText());
                    double valEk = Double.parseDouble(Ek.getText());
                    double valEm = Double.parseDouble(Em.getText());

                    double valMt0 = Double.parseDouble(Mt0.getText());
                    double valMdt = Double.parseDouble(Mdt.getText());
                    double valMInitX = Double.parseDouble(MInitX.getText());
                    double valMInitY = Double.parseDouble(MInitY.getText());
                    double valMInitSpdX = Double.parseDouble(MInitSpdX.getText());
                    double valMInitSpdY = Double.parseDouble(MInitSpdY.getText());
                    double valMk = Double.parseDouble(Mk.getText());
                    double valMm = Double.parseDouble(Mm.getText());

                    (new Thread(() -> {
                        ChartPanel.getInstance().reset();
                        EulerCalculation eulerCalculation =
                                new EulerCalculation(valEt0, valEdt, valEInitX, valEInitY, valEInitSpdX, valEInitSpdY, valEk, valEm);

                        MidpointCalculation midpointCalculation =
                                new MidpointCalculation(valMt0, valMdt, valMInitX, valMInitY, valMInitSpdX, valMInitSpdY, valMk, valMm);

                        eulerCalculation.simulateChart();
                        midpointCalculation.simulateChart();
                    })).start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please, check your input data.");
                }

            }
        };
    }

    private MouseAdapter getCalculateTableMouseAdapter() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    GUI.getInstance().switchToTable();
                    double valEt0 = Double.parseDouble(Et0.getText());
                    double valEdt = Double.parseDouble(Edt.getText());
                    double valEInitX = Double.parseDouble(EInitX.getText());
                    double valEInitY = Double.parseDouble(EInitY.getText());
                    double valEInitSpdX = Double.parseDouble(EInitSpdX.getText());
                    double valEInitSpdY = Double.parseDouble(EInitSpdY.getText());
                    double valEk = Double.parseDouble(Ek.getText());
                    double valEm = Double.parseDouble(Em.getText());

                    double valMt0 = Double.parseDouble(Mt0.getText());
                    double valMdt = Double.parseDouble(Mdt.getText());
                    double valMInitX = Double.parseDouble(MInitX.getText());
                    double valMInitY = Double.parseDouble(MInitY.getText());
                    double valMInitSpdX = Double.parseDouble(MInitSpdX.getText());
                    double valMInitSpdY = Double.parseDouble(MInitSpdY.getText());
                    double valMk = Double.parseDouble(Mk.getText());
                    double valMm = Double.parseDouble(Mm.getText());

                    (new Thread(() -> {
                        TablePanel.getInstance().reset();
                        EulerCalculation eulerCalculation =
                                new EulerCalculation(valEt0, valEdt, valEInitX, valEInitY, valEInitSpdX, valEInitSpdY, valEk, valEm);

                        MidpointCalculation midpointCalculation =
                                new MidpointCalculation(valMt0, valMdt, valMInitX, valMInitY, valMInitSpdX, valMInitSpdY, valMk, valMm);

                        eulerCalculation.simulateTable();
                        midpointCalculation.simulateTable();
                    })).start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please, check your input data.");
                }
            }
        };
    }
}
