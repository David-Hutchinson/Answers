package program;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
	public static void main(String[] args){
		try { 
			PriorityBlockingQueue<Integer> largestNumbersInFile = TopN.topN(8);
			while ( !largestNumbersInFile.isEmpty()) {
				System.out.println(largestNumbersInFile.poll());
			}
			
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
	}
}
