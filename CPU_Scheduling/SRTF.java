import java.util.Scanner;

class Process {
    int pid; // Process ID
    int bt; // Burst Time
    int art; // Arrival Time
    int ct; // Completion Time

    public Process(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}

public class SRTF {
    // Method to find the waiting time, turn around time, and completion time for all processes
    static void findWaitingTime(Process proc[], int n, int wt[]) {
        int rt[] = new int[n];

        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++)
            rt[i] = proc[i].bt;

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Process until all processes get completed
        while (complete != n) {

            // Find process with minimum remaining time among the
            // processes that arrive till the current time
            for (int j = 0; j < n; j++) {
                if ((proc[j].art <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            rt[shortest]--;

            // Update minimum
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (rt[shortest] == 0) {
                // Increment complete
                complete++;
                check = false;

                // Find finish time of the current process
                finish_time = t + 1;

                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].bt - proc[shortest].art;

                if (wt[shortest] < 0)
                    wt[shortest] = 0;

                // Set completion time
                proc[shortest].ct = finish_time;
            }
            // Increment time
            t++;
        }
    }

    // Method to calculate average time
    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        // Function to find waiting time, turn around time, and completion time of all processes
        findWaitingTime(proc, n, wt);

        // Function to calculate turn around time for all processes
        findTurnAroundTime(proc, n, wt, tat);

        // Display processes along with all details
        System.out.println("Processes\tBurst time\tArrival time\tWaiting time\tTurnaround time\tCompletion time");

        // Calculate total waiting time and total turnaround time
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            System.out.println(proc[i].pid + "\t\t\t" + proc[i].bt + "\t\t" + proc[i].art + "\t\t" + wt[i] + "\t\t" + tat[i] + "\t\t" + proc[i].ct);
        }

        System.out.println("\nAverage waiting time = " + (float) total_wt / (float) n);
        System.out.println("Average turnaround time = " + (float) total_tat / (float) n);
        System.out.println("Throughput : "+((float) total_tat / (float) n)/n);

    }

    // Method to calculate turn around time
    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        // calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].bt + wt[i];
    }

    // Driver Method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] proc = new Process[n];

        // Input arrival time and burst time for each process
        System.out.println(" Process ArrivalTime BurstTime: ");
        for (int i = 0; i < n; i++) {
            //System.out.println("Enter details for Process " + (i + 1));
            int p=scanner.nextInt();
            int arrivalTime = scanner.nextInt();
            //System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            proc[i] = new Process(i + 1, burstTime, arrivalTime);
        }

        findavgTime(proc, proc.length);
    }
}