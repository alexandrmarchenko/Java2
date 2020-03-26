package lesson1;

import java.sql.PreparedStatement;

public class Treadmill implements IRunJumpBarrier {

    private String serialNumber;
    private String model;
    private double runDistance;

    public Treadmill(String serialNumber, String model, double runDistance) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.runDistance = runDistance;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getModel() {
        return model;
    }

    public double getLength() {
        return runDistance;
    }

    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public boolean getOver(IRunnerJumper IRJ) {
        boolean result = IRJ.run() >= this.runDistance;
        System.out.printf("Забег на %s метров -> %s%n", runDistance, result ? "Успех" : "Неудача");
        return result;
    }

    @Override
    public String toString() {
        return "Treadmill{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", length=" + runDistance +
                '}';
    }
}
