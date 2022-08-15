
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int i = scanner.nextInt();
        int j = scanner.nextInt();

        int[][] userArr = createUserArr(i, j);

        int swapOne = scanner.nextInt();
        int swapTwo = scanner.nextInt();

        int[][] newArr = modifyArr(userArr, swapOne, swapTwo);
        printArr(newArr);

    }

    public static int[][] modifyArr(int[][] arr, int swapOne, int swapTwo) {

        ArrayList<Integer> swapOneNumbers = new ArrayList<>();
        ArrayList<Integer> swapTwoNumbers = new ArrayList<>();

        // collect needed values

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (j == swapOne) {
                    swapOneNumbers.add(arr[i][j]);
                } else if (j == swapTwo) {
                    swapTwoNumbers.add(arr[i][j]);
                }
            }
        }

        // modify arr

        int swapTwoIndex = 0;
        int swapOneIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (j == swapOne && swapTwoNumbers.size() > 0) {
                    arr[i][j] = swapTwoNumbers.get(swapTwoIndex);
                    swapTwoIndex++;
                } else if (j == swapTwo && swapOneNumbers.size() > 0) {
                    arr[i][j] = swapOneNumbers.get(swapOneIndex);
                    swapOneIndex++;
                }
            }
        }

        return arr;
    }

    public static int[][] createUserArr(int rows, int columns) {
        int[][] userArr = new int[rows][columns];

        for (int i = 0; i < userArr.length; i++) {
            for (int j = 0; j < userArr[i].length; j++) {
                userArr[i][j] = scanner.nextInt();
            }
        }
        return userArr;
    }

    public static void printArr(int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
                if (j != arr[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}