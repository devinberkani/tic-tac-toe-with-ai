import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // write your code here
        int randomNumber;
        int max;
        int minMax = Integer.MAX_VALUE;
        int minSeed;

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        minSeed = a;

        for (int seed = a; seed <= b; seed++) {
            Random random = new Random(seed);
            max = Integer.MIN_VALUE;
            for (int l = 0; l < n; l++) {
                randomNumber = random.nextInt(k);
                if (randomNumber > max) {
                    max = randomNumber;
                }
            }
            if (max < minMax) {
                minMax = max;
                minSeed = seed;
            }
        }
        System.out.println(minSeed);
        System.out.println(minMax);
    }
}