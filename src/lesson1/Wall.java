package lesson1;

public class Wall implements IRunJumpBarrier {

    private double height;

    public Wall(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean getOver(IRunnerJumper IRJ) {
        boolean result = IRJ.jump() >= height;
        System.out.printf("Прыжок через стену высотой %s метров -> %s%n", height, result ? "Успех" : "Неудача");
        return result;
    }

    public void printInfo() {
        System.out.println(this);
    }
    @Override
    public String toString() {
        return "Wall{" +
                "height=" + height +
                '}';
    }
}
