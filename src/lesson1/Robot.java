package lesson1;

public class Robot implements IRunnerJumper {

    private String name;
    private String model;
    private double maxRunDistance;
    private double maxJumpHeight;

    public Robot(String name, String model, double maxRunDistance, double maxJumpHeight) {
        this.name = name;
        this.model = model;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public double getMaxRunDistance() {
        return maxRunDistance;
    }

    public double getMaxJumpHeight() {
        return maxJumpHeight;
    }

    @Override
    public double run() {
        System.out.printf("Робот %s бежит!%n", name);
        return maxRunDistance;
    }

    @Override
    public double jump() {
        System.out.printf("Робот %s прыгает!%n", name);
        return maxJumpHeight;
    }

    public void printInfo() {
        System.out.println(this);
    }
    @Override
    public String toString() {
        return "Robot{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", maxRunDistance=" + maxRunDistance +
                ", maxJumpHeight=" + maxJumpHeight +
                '}';
    }
}
