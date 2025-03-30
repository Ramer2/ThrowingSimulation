import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChartPanel extends JPanel {
    private static ChartPanel instance;
    private ArrayList<Point> EulerPoints;
    private ArrayList<Point> MidpointPoints;
    private int maxX = 1;
    private int maxY = 1;

    private ChartPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 0, Color.BLACK));
        EulerPoints = new ArrayList<>();
        MidpointPoints = new ArrayList<>();
    }

    public void addEulerPoint(double posX, double posY) {
        SwingUtilities.invokeLater(() -> {
            int x = (int) (posX * 100);
            int y = (int) (posY * 100);
            EulerPoints.add(new Point(x, y));
            updateMaxValues(x, y);
            repaint();
        });
    }

    public void addMidpointPoint(double posX, double posY) {
        SwingUtilities.invokeLater(() -> {
            int x = (int) (posX * 100);
            int y = (int) (posY * 100);
            MidpointPoints.add(new Point(x, y));
            updateMaxValues(x, y);
            repaint();
        });
    }

    private void updateMaxValues(int x, int y) {
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
    }

    public void reset() {
        EulerPoints.clear();
        MidpointPoints.clear();
        maxX = 1;
        maxY = 1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth() - 100;
        int height = getHeight() - 100;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(50, height + 50, width + 75, height + 50); // x-axis
        g2d.drawLine(50, 25, 50, height + 50); // y-axis

        drawGraph(g2d, EulerPoints, Color.BLUE, width, height);
        drawGraph(g2d, MidpointPoints, Color.ORANGE, width, height);
        drawAxisLabels(g2d, width, height);
        drawLegend(g2d, width);
    }

    private void drawGraph(Graphics2D g2d, ArrayList<Point> points, Color color, int width, int height) {
        g2d.setColor(color);
        if (points.size() > 1) {
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = scalePoint(points.get(i), width, height);
                Point p2 = scalePoint(points.get(i + 1), width, height);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

                Point scaled = scalePoint(points.get(i), width, height);
                g2d.fillOval(scaled.x - 2, scaled.y - 2, 4, 4);
            }
        }
    }

    private Point scalePoint(Point p, int width, int height) {
        int scaledX = 50 + (p.x * width / maxX);
        int scaledY = height + 50 - (p.y * height / maxY);
        return new Point(scaledX, scaledY);
    }

    private void drawAxisLabels(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        for (int i = 0; i <= 10; i++) {
            int xPos = 50 + (i * width / 10);
            int yPos = height + 50 - (i * height / 10);
            double xValue = (maxX / 10.0) * i / 100.0;
            double yValue = (maxY / 10.0) * i / 100.0;

            g2d.drawString(String.format("%.2f", xValue), xPos, height + 70);
            g2d.drawString(String.format("%.2f", yValue), 20, yPos);

            g2d.fillOval(xPos - 2, height + 50 - 2, 4, 4);
            g2d.fillOval(50 - 2, yPos - 2, 4, 4);
        }
    }

    private void drawLegend(Graphics2D g2d, int width) {
        int legendX = width - 150;
        int legendY = 20;
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        g2d.setColor(Color.BLUE);
        g2d.drawLine(legendX, legendY, legendX + 30, legendY);
        g2d.drawString("Euler's method", legendX + 40, legendY + 5);

        g2d.setColor(Color.ORANGE);
        g2d.drawLine(legendX, legendY + 20, legendX + 30, legendY + 20);
        g2d.drawString("Midpoint method", legendX + 40, legendY + 25);
    }

    public static ChartPanel getInstance() {
        if (instance == null)
            instance = new ChartPanel();
        return instance;
    }
}
