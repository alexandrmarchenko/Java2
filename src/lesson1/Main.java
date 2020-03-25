package lesson1;

public class Main {
    public static void main(String[] args) {
        IRunnerJumper[] runJump = new IRunnerJumper[]{
                new Human("Вася", 20, 2000, 2.5),
                new Human("Иван", 55, 500, 1.5),
                new Cat("Пушок", 550, 3),
                new Cat("Снежок"),
                new Robot("Eva","smart robot",1500, 4),
                new Robot("R2D2", "astromech droid", 2500, 1.5)
        };

        IRunJumpBarrier[] barriers = new IRunJumpBarrier[] {
                new Treadmill("001", "Torneo Smarta", 500),
                new Wall(1),
                new Treadmill("002", "UnixFit ST-530M", 2000),
                new Wall(3)
        };

        for (IRunnerJumper runnerJumper : runJump) {
            System.out.println(runnerJumper.toString());
            for (IRunJumpBarrier barrier : barriers) {
                if(!barrier.getOver(runnerJumper)) {
                    break;
                }
            }
        }
    }
}
