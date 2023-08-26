import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int tempBT;
    int priority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.tempBT = burstTime;
        this.priority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

class ProcessComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        if (p1.arrivalTime < p2.arrivalTime)
            return -1;
        else if (p1.arrivalTime == p2.arrivalTime && p1.priority < p2.priority)
            return -1;
        return 1;
    }
}

public class NPre {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.println("Enter Arrival, Burst, Priority:");
        for (int i = 0; i < n; ++i) {
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process process = new Process(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        Collections.sort(processes, new ProcessComparator());

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            Process selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !process.completed &&
                        process.priority < highestPriority) {
                    highestPriority = process.priority;
                    selectedProcess = process;
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            selectedProcess.startTime = currentTime;
            selectedProcess.completionTime = selectedProcess.startTime +
                    selectedProcess.burstTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime =
                    selectedProcess.completionTime - selectedProcess.arrivalTime;
            selectedProcess.waitingTime =
                    selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;

            completedProcesses++;
        }

        float averageWaitingTime = 0, averageTurnaroundTime = 0;

        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tPriority\tStart\t\tCompletion\tWaiting\t\tTurnaround");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        for (Process process : processes) {
            averageTurnaroundTime += process.turnaroundTime;
            averageWaitingTime += process.waitingTime;
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.tempBT + "\t\t" + process.priority + "\t\t"
                    + process.startTime + "\t\t" + process.completionTime + "\t\t" + process.waitingTime + "\t\t" + process.turnaroundTime);
        }
        float T_ATAT = averageTurnaroundTime / n;
        System.out.println("Average Waiting Time: " + averageWaitingTime / n);
        System.out.println("Average Turnaround Time: " + T_ATAT);
        System.out.println("Throughput Time: " + T_ATAT / n);
    }
}