import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrSize = scanner.nextInt();

        int[] array = new int[arrSize];

        for (int i = 0; i < array.length; i++) {
        array[i] = scanner.nextInt();
        }

        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();

        boolean doesOccur = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == number1) {
                if (i != 0) {
                    if (array[i - 1] == number2) {
                        doesOccur = true;
                        break;
                    }
                } else if (i != arrSize - 1) {
                    if (array[i + 1] == number2) {
                        doesOccur = true;
                        break;
                    }
                }
            } else if (array[i] == number2) {
                if (i != 0) {
                    if (array[i - 1] == number1) {
                        doesOccur = true;
                        break;
                    }
                } else if (i != arrSize - 1) {
                    if (array[i + 1] == number1) {
                        doesOccur = true;
                        break;
                    }
                }
            }
        }

        System.out.println(doesOccur);

    }
}
