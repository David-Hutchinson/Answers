package program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Class which contains methods to assist in determining the top N numbers in a file.
 * @author David Hutchinson
 */
public class TopN {
	
	/**
	 * Method which determines the top N numbers in a given file. The method assumes that each line contains a 
	 * single numbers with no spaces.
	 * @param n The number of highest numbers to find.
	 * @return The list of highest numbers, highest first.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static PriorityBlockingQueue<Integer> topN(int n) throws FileNotFoundException, IOException{
		URL url = new URL("http://cs1.ucc.ie//~dgh1//numbers.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
		String line;
		int currentNumber;
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>();
		while (( line = reader.readLine()) != null ) {
			currentNumber = Integer.parseInt(line);
			
			if ( queue.size() < n ) {
				queue.offer(currentNumber);
			} else if ( currentNumber > queue.peek()) {
				queue.poll();
				queue.offer(currentNumber);
			}
		}
		reader.close();
		return reverseQueue(queue);
	}
	
	/**
	 * Method to reverse a PriorityQueue.
	 * @param queue The queue to be reversed.
	 * @return The reversed queue.
	 */
	public static PriorityBlockingQueue<Integer> reverseQueue(PriorityBlockingQueue<Integer> queue) {
		PriorityBlockingQueue<Integer> reversed = new PriorityBlockingQueue<Integer>(queue.size(), Collections.reverseOrder());
		reversed.addAll(queue);
		return reversed;
	}
}
