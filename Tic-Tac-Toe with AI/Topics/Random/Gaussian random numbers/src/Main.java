import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double gauss;
        int count;

        long k = scanner.nextLong();
        int n = scanner.nextInt();
        double m = scanner.nextDouble();

        while (true) {
            count = -1;
            Random random = new Random(k);
            do {
                count++;
                gauss = random.nextGaussian();
            } while (gauss <= m);

            if (count >= n) {
                System.out.println(k);
                break;
            } else {
                k++;
            }
        }
    }
}