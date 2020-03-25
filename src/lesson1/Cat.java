package lesson1;

public class Cat implements IRunnerJumper {

    private String nickname;

    private double maxRunDistance;
    private double maxJumpHeight;

    public Cat(String nickname, double maxRunDistance, double maxJumpHeight) {
        this.nickname = nickname;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    public Cat(String nickname) {
        this(nickname, DEFAULT_RUN_DISTANCE, DEFAULT_JUMP_HEIGHT);
    }

    public String getNickname() {
        return nickname;
    }

    public double getMaxRunDistance() {
        return maxRunDistance;
    }

    public double getMaxJumpHeight() {
        return maxJumpHeight;
    }

    @Override
    public double run() {
        System.out.printf("Кот %s бежит!%n", nickname);
        return maxRunDistance;
    }

    @Override
    public double jump() {
        System.out.printf("Кот %s прыгает!%n", nickname);
        return maxJumpHeight;
    }

    public void prinInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "nickname='" + nickname + '\'' +
                ", maxRunDistance=" + maxRunDistance +
                ", maxJumpHeight=" + maxJumpHeight +
                '}';
    }
}
