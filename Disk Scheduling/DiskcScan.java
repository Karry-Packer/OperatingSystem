import java.util.Scanner;

public class DiskcScan {
    static int finds(int[] a, int n, int item) {
        for (int i = 0; i < n; i++) {
            if (a[i] == item) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] request = new int[51];
        int[] news = new int[51];
        int comp, sizes, n;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the disk size: ");
        sizes = scanner.nextInt();
        System.out.print("Enter the current position of the pointer: ");
        request[0] = scanner.nextInt();
        int cp = request[0];

        System.out.print("Enter the number of pending requests: ");
        n = scanner.nextInt();
        System.out.print("Enter the pending request entries: ");
        for (int i = 1; i <= n; i++) {
            System.out.print("Request " + i + ": ");
            request[i] = scanner.nextInt();
        }

        char direction;
        System.out.print("Enter the direction of movement (L for left, R for right): ");
        direction = scanner.next().charAt(0);

        for (int i = 0; i <= n - 1; i++) {
            for (int j = 0; j <= n - 1 - i; j++) {
                if (request[j] > request[j + 1]) {
                    int temp = request[j];
                    request[j] = request[j + 1];
                    request[j + 1] = temp;
                }
            }
        }
        int ind = finds(request, n, cp);
        int i = ind, j = 0, pos1, pos2;
        news[0] = request[ind];
        pos1 = i + 1;
        pos2 = i - 1;
        j++;
        int ctr1 = 0, ctr2 = 0;
        for (int k = pos1; k <= n; k++) {
            ctr1++;
        }
        for (int l = pos2; l >= 0; l--) {
            ctr2++;
        }

        if (direction == 'R' || direction == 'r') {
            if (ctr1 > ctr2) {
                for (int k = pos1; k <= n; k++) {
                    news[j] = request[k];
                    j++;
                }
                news[j] = sizes;
                j++;
                news[j] = 0;
                j++;
                for (int l = 0; l <= pos2; l++) {
                    news[j] = request[l];
                    j++;
                }
                comp = Math.abs(cp - (sizes - 1)) + (sizes - 1) + request[pos2];
            } else {
                for (int l = pos2; l >= 0; l--) {
                    news[j] = request[l];
                    j++;
                }
                news[j] = 0;
                j++;
                news[j] = sizes - 1;
                j++;
                for (int k = n; k >= pos1; k--) {
                    news[j] = request[k];
                    j++;
                }
                comp = cp + (sizes - 1) + ((sizes - 1) - request[pos1]);
            }
        } else if (direction == 'L' || direction == 'l') {
            if (ctr2 > ctr1) {
                for (int l = pos2; l >= 0; l--) {
                    news[j] = request[l];
                    j++;
                }
                news[j] = sizes;
                j++;
                news[j] = sizes - 1;
                j++;
                for (int k = n; k >= pos1; k--) {
                    news[j] = request[k];
                    j++;
                }
                comp = Math.abs(cp - (sizes - 1)) + (sizes - 1) + request[pos2];
            } else {
                for (int l = pos2; l >= 0; l--) {
                    news[j] = request[l];
                    j++;
                }
                news[j] = 0;
                j++;
                news[j] = 100; // Replace the second "0" with "100"
                j++;
                for (int k = n; k >= pos1; k--) {
                    news[j] = request[k];
                    j++;
                }
                comp = cp + (sizes - 1) + ((sizes - 1) - request[pos1]);
            }
        } else {
            System.out.println("Invalid direction!");
            return;
        }

        System.out.print("Pointer Movement: ");
        for (int k = 0; k < j - 1; k++) {
            System.out.print(news[k] + " -> ");
        }
        System.out.println(news[j - 1]);
        System.out.println("Total head movement: " + comp + " cylinders.");
        System.out.println("Throughput: " + (float) n / comp);
    }
}