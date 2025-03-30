import javax.swing.*;

public class MidpointCalculation {
    // constants
    double t0;
    double dt;

    // gravitational accelerations
    double Gx = 0;
    double Gy = -9.8;

    // other
    double K_resistance; // resistance coefficient
    double mass;

    // current position and speed values
    double posX;
    double posY;
    double prevSpdX;
    double prevSpdY;

    public MidpointCalculation(double t0, double dt, double initialX, double initialY, double initialSpdX, double initialSpdY, double k_resistance, double mass) {
        this.t0 = t0;
        this.dt = dt;
        this.K_resistance = k_resistance;
        this.mass = mass;

        this.posX = initialX;
        this.posY = initialY;
        this.prevSpdX = initialSpdX;
        this.prevSpdY = initialSpdY;
    }

    public double accelerationX(double speedX) {
        return (mass * Gx - K_resistance * Math.pow(speedX, 2)) / mass;
    }

    public double accelerationY(double speedY) {
        return (mass * Gy - K_resistance * Math.pow(speedY, 2)) / mass;
    }

    public void update() {
        double midSpdX = prevSpdX + accelerationX(prevSpdX) * (dt / 2);
        double midSpdY = prevSpdY + accelerationY(prevSpdY) * (dt / 2);

        double midAccX = accelerationX(midSpdX);
        double midAccY = accelerationY(midSpdY);

        double newSpdX = prevSpdX + midAccX * dt;
        double newSpdY = prevSpdY + midAccY * dt;

        posX += midSpdX * dt;
        posY += midSpdY * dt;

        prevSpdX = newSpdX;
        prevSpdY = newSpdY;
    }

    public void simulateChart() {
        while (posY >= -0.5) {
            ChartPanel.getInstance().addMidpointPoint(posX, posY);
            update();
        }
    }

    public void simulateTable() {
        double time = t0;
        while (posY >= -0.5) {
            time += dt;
            Object[] data = {round(time), round(posX), round(posY), round(prevSpdX), round(prevSpdY),
                    round(prevSpdX * dt), round(prevSpdY * dt), round(accelerationX(prevSpdX) * dt), round(accelerationY(prevSpdY) * dt)};
            SwingUtilities.invokeLater(() -> TablePanel.getInstance().addMidpointRow(data));
            update();
        }
    }

    private double round(double input) {
        return Math.round(input * 1000.0) / 1000.0;
    }
}