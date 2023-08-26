import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnTime;
    int waitingTime;
    int startTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.print("Enter time quantum of CPU: ");
        int tq = scanner.nextInt();

        Process[] p = new Process[n];
        int[] burstArr = new int[n];
        int totalWaiting = 0;
        int totalTurn = 0;

        System.out.println();   
        System.out.println("Process ArrivalTime BurstTime :");
        for (int i = 0; i < n; i++) {
            //System.out.print("Enter arrival time of process " + (i + 1) + ": ");
            int process=scanner.nextInt();
            int arrivalTime = scanner.nextInt();

            //System.out.print("Enter burst time of process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            burstArr[i] = burstTime;
            p[i] = new Process(i + 1, arrivalTime, burstTime);

            System.out.println();
        }

        Queue<Integer> queue = new LinkedList<>();
        int currentTime = 0;
        queue.offer(0);
        int completed = 0;
        int[] mark = new int[100];

        while (completed != n) {
            int index = queue.poll();

            if (burstArr[index] == p[index].burstTime) {
                p[index].startTime = Math.max(currentTime, p[index].arrivalTime);
                currentTime = p[index].startTime;
            }

            if (burstArr[index] > tq) {
                burstArr[index] -= tq;
                currentTime += tq;
            } else {
                currentTime += burstArr[index];
                p[index].completionTime = currentTime;
                p[index].turnTime = p[index].completionTime - p[index].arrivalTime;
                p[index].waitingTime = p[index].turnTime - p[index].burstTime;
                totalWaiting += p[index].waitingTime;
                totalTurn += p[index].turnTime;
                completed++;
                burstArr[index] = 0;
            }

            for (int i = 1; i < n; i++) {
                if (burstArr[i] > 0 && p[i].arrivalTime <= currentTime && mark[i] == 0) {
                    mark[i] = 1;
                    queue.offer(i);
                }
            }

            if (burstArr[index] > 0) {
                queue.offer(index);
            }

            if (queue.isEmpty()) {
                for (int i = 1; i < n; i++) {
                    if (burstArr[i] > 0) {
                        mark[i] = 1;
                        queue.offer(i);
                        break;
                    }
                }
            }
        }

        float avgWaiting = (float) totalWaiting / n;
        float avgTurn = (float) totalTurn / n;

        System.out.println();
        System.out.println("Process\t\tArrival Time\t\tBurst Time\t\tCompletion Time\t\tWaiting Time\t\tTurnaround Time");
        for (Process process : p) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t\t" + process.burstTime +
                    "\t\t\t" + process.completionTime + "\t\t\t" + process.waitingTime + "\t\t\t" + process.turnTime);
        }

        System.out.println();
        System.out.println("Average Waiting Time = " + avgWaiting);
        System.out.println("Average Turnaround Time = " + avgTurn);
        System.out.println("Throughput : "+(avgTurn)/n);
    }
}