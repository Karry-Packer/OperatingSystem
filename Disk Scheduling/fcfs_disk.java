import java.util.*;
public class fcfs_disk {
   public static void main(String[] args)
    {
        int head;
         Scanner sc=new Scanner(System.in);
        System.out.print("Enter the size of the disk :");
        int size = sc.nextInt();
        
        int diskno[]=new int[size];
        System.out.println("Enter the track number :");
        for(int i=0;i<size;i++){
        diskno[i]=sc.nextInt();
        }
    
        int seek_count = 0;
        int distance, cur_track;
         System.out.print("Enter the where the head is :");
         head=sc.nextInt();
        for(int i=0;i<size;i++)
        {
            cur_track =diskno[i];
            distance = Math.abs(cur_track - head);
            seek_count +=distance;
            head = cur_track;
        }
        System.out.println("Total number of "+"seek operations ="+seek_count);
        System.out.println("Throughput :"+(float)size/seek_count);
        
    }   
     
}   
