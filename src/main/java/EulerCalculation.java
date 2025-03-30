import javax.swing.*;
import java.util.ArrayList;

public class EulerCalculation {
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

    public EulerCalculation(double t0, double dt, double initialX, double initialY, double initialSpdX, double initialSpdY, double k_resistance, double mass) {
        this.t0 = t0;
        this.dt = dt;
        this.K_resistance = k_resistance;
        this.mass = mass;

        this.posX = initialX;
        this.posY = initialY;
        this.prevSpdX = initialSpdX;
        this.prevSpdY = initialSpdY;
    }

    public double speedX() {
        return prevSpdX + accelerationX(prevSpdX) * dt;
    }

    public double speedY() {
        return prevSpdY + accelerationY(prevSpdY) * dt;
    }

    public double accelerationX(double vx) {
        return (mass * Gx - K_resistance * Math.pow(vx, 2)) / mass;
    }

    public double accelerationY(double vy) {
        return (mass * Gy - K_resistance * Math.pow(vy, 2)) / mass;
    }

    public void update() {
        double newSpdX = speedX();
        double newSpdY = speedY();

        posX += prevSpdX * dt;
        posY += prevSpdY * dt;

        prevSpdX = newSpdX;
        prevSpdY = newSpdY;
    }

    public void simulateChart() {

        while (posY >= -0.5) {
            ChartPanel.getInstance().addEulerPoint(posX, posY);
            update();
        }
    }

    public void simulateTable() {
        double time = t0;
        while (posY >= -0.5) {
            time += dt;
            Object[] data = {round(time), round(posX), round(posY), round(prevSpdX), round(prevSpdY),
                    round(speedX() * dt), round(speedY() * dt), round(accelerationX(prevSpdX) * dt), round(accelerationY(prevSpdY) * dt)};
            SwingUtilities.invokeLater(() -> TablePanel.getInstance().addEulerRow(data));
            update();
        }
    }

    private double round(double input) {
        return Math.round(input * 1000.0) / 1000.0;
    }
}
