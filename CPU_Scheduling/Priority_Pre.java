import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int tempBT;
    int burstTime;
    int priority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean executed;

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.tempBT = burstTime;
        this.priority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.executed = false;
    }
}

public class Priority_Pre{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process> processes = new ArrayList<>();
        System.out.println("Enter Process Arrival , Burst , Priority : ");
        for (int i = 0; i < n; i++) {
            //System.out.println("for process " + (i + 1) + ":");
            int pid =scanner.nextInt();
            int arrivalTime = scanner.nextInt();
          
            int burstTime = scanner.nextInt();
           
            int priority = scanner.nextInt();

            Process process = new Process(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int highestPriority = Integer.MAX_VALUE;
            int selectedProcessIndex = -1;

            for (int i = 0; i < n; i++) {
                Process process = processes.get(i);

                if (!process.executed && process.arrivalTime <= currentTime && process.priority < highestPriority) {
                    highestPriority = process.priority;
                    selectedProcessIndex = i;
                }
            }

            if (selectedProcessIndex == -1) {
                currentTime++;
                continue;
            }

            Process selectedProcess = processes.get(selectedProcessIndex);
            selectedProcess.executed = true;
            selectedProcess.startTime = currentTime;
            selectedProcess.completionTime = currentTime + 1;
            
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.tempBT;

            currentTime++;

            if (selectedProcess.burstTime > 1) {
                selectedProcess.burstTime--;
                selectedProcess.executed = false;
            } else {
                completedProcesses++;
            }
        }
        
        float AverageWaitingTime = 0f , AverageTurnAroundTime = 0f;
        for(Process p : processes) {
        	AverageTurnAroundTime += p.turnaroundTime;
        	AverageWaitingTime += p.waitingTime;
        }

        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tpriority\t\tCompletion\t\tWaiting\t\tTurnaround");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        for (Process process : processes) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.tempBT + "\t\t" +process.priority+"\t\t" + process.completionTime + "\t\t\t" + process.waitingTime +
                    "\t\t\t" + process.turnaroundTime);
        }
        
        System.out.println("Average Waiting time : " + AverageWaitingTime/n);
        System.out.println("Average TurnAroundTime : "+ AverageTurnAroundTime/n);
        System.out.println("Throughput :"+(AverageTurnAroundTime/n)/n);

        scanner.close();
    }
}