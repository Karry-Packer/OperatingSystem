

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SCAN1 {

	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        // Accepting input from the user
	        System.out.print("Enter the initial head position: ");
	        int HeadPosition = scanner.nextInt();
	        scanner.nextLine(); // Clear the input buffer

	        System.out.println("Enter total number of sequences : ");
	        int n = scanner.nextInt();
	        
	        int[] locations = new int[n];
	        ArrayList<Integer> left  = new ArrayList<Integer>();
	        ArrayList<Integer> right  = new ArrayList<Integer>(); 
	        ArrayList<Integer >seekTime = new ArrayList<Integer>();
	        int totalSeekTime = 0;
	        int SeekTime = 0 ;
	        
	        System.out.print("Enter the sequence list : ");
	        for(int i=0;i<n;i++) {
	        	//locations[i] = scanner.nextInt();
	        	int num = scanner.nextInt();
	        	if(num <= HeadPosition) {
	        		left.add(num);
	        	}else {
	        		right.add(num);
	        	}
	        }
	        
	        
	        System.out.print("Enter the direction (left or right): ");
	        String direction = scanner.next().toLowerCase();

	        if(direction.equals("left")) {
	        	left.add(0);
	        	Collections.sort(left,Collections.reverseOrder());
	        	Collections.sort(right);
	        	
	        	for(int j = 0 ; j<left.size() ; j++) {
	        		SeekTime = Math.abs(HeadPosition - left.get(j));
	        		totalSeekTime += SeekTime;
	        		seekTime.add(SeekTime);
	        		HeadPosition = left.get(j);
	        	}
	        	
	        	for(int j = 0 ; j<right.size() ; j++ ) {
	        		SeekTime = Math.abs(HeadPosition - right.get(j));
	        		totalSeekTime += SeekTime;
	        		seekTime.add(SeekTime);
	        		HeadPosition = right.get(j);
	        	}
	        	
	        	
	        	
	        	int k = 0;
	        	System.out.println("\nRequest \tSeek Time");
	        	for(int i =0 ; i<left.size() ; i++) {
	        		System.out.println(left.get(i) + "\t\t" + seekTime.get(k));
	        		k++;
	        	}
	        	
	        	
	        	System.out.println("---------Changed Direction---------");
	        	
	        	for(int i = 0 ; i<right.size() ; i++) {
	        		System.out.println(right.get(i) + "\t\t" + seekTime.get(k));
	        		k++;
	        	}
	        	
	        	System.out.println("Total Seek Time : " + totalSeekTime);
	        	System.out.println("Throughput : "+ (float)n/totalSeekTime);
	        	
	        	
	        }else if(direction.equals("right")) {
	        	right.add(200);
	        	Collections.sort(right);
	        	Collections.sort(left,Collections.reverseOrder());
	        	
	        	for(int j = 0 ; j<right.size() ; j++ ) {
	        		SeekTime = Math.abs(HeadPosition - right.get(j));
	        		totalSeekTime += SeekTime;
	        		seekTime.add(SeekTime);
	        		HeadPosition = right.get(j);
	        	}
	        	
	        	for(int j = 0 ; j<left.size() ; j++) {
	        		SeekTime = Math.abs(HeadPosition - left.get(j));
	        		totalSeekTime += SeekTime;
	        		seekTime.add(SeekTime);
	        		HeadPosition = left.get(j);
	        	}
	        	
	        	int k =0;
	        	System.out.println("\nRequest \tSeek Time");
	        	for(int i = 0 ; i<right.size() ; i++) {
	        		System.out.println(right.get(i) + "\t\t" + seekTime.get(k));
	        		k++;
	        	}
	        	
	        	System.out.println("---------Changed Direction---------");
	        	
	        	for(int i =0 ; i<left.size() ; i++) {
	        		System.out.println(left.get(i) + "\t\t" + seekTime.get(k));
	        		k++;
	        	}
	        	
	        	System.out.println("Total Seek Time : " + totalSeekTime);
	        	System.out.println("Throughput : "+ (float)n/totalSeekTime);
	        }else {
	            System.out.println("Invalid direction entered. Please choose either 'left' or 'right'.");
	            return;
	        }
	        
	        
	        
	        
	        
	        
	      
}
}
