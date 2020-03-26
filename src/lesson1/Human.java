package lesson1;

public class Human implements IRunnerJumper {

    private String name;
    private int age;
    private double maxRunDistance;
    private double maxJumpHeight;

    public Human(String name, int age, double maxRunDistance, double maxJumpHeight) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    public double getMaxRunDistance() {
        return maxRunDistance;
    }

    public double getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public double run() {
        System.out.printf("Человек %s бежит!%n", name);
        return maxRunDistance;
    }

    @Override
    public double jump() {
        System.out.printf("Человек %s прыгает!%n", name);
        return maxJumpHeight;
    }

    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", maxRunDistance=" + maxRunDistance +
                ", maxJumpHeight=" + maxJumpHeight +
                '}';
    }
}
