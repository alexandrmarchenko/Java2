package lesson2;

public class Main {

    public final static int ARRAY_SIZE = 4;

    public static void main(String[] args) {
        String[][] array = new String[][] {
                {"1","1","1","1"},
                {"1","1","1","1"},
                {"1","1","1","1"},
                {"1","1","1","1"}};

        try {
            int sum = sumMyArray(array);
            System.out.println(sum);
        } catch (MySizeArrayException | MyArrayDataException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int sumMyArray(String[][] array) {
        checkSize(array);
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Ошибка преобразования ячейки ["+i+"]["+j+"]");
                }
            }
        }
        return sum;
    }

    public static void checkSize(String[][] array) {
        if (array.length != ARRAY_SIZE) {
            throw new MySizeArrayException("Размер массива не равен "+ ARRAY_SIZE);
        } else {
            for (String[] row : array) {
                if (row.length != ARRAY_SIZE) {
                    throw new MySizeArrayException("Размер массива не равен "+ ARRAY_SIZE);
                }
            }
        }
    }
}
