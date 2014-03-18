package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Test;

import program.TopN;

public class TopNTests {
	@Test
	public void testFileAvailable() {
		File file = new File("C:\\Users\\David\\Desktop\\numbers.txt");
		assertTrue(file.exists());
	}
	
	@Test
	public void testTopN() {
		PriorityBlockingQueue<Integer> original = new PriorityBlockingQueue<Integer>();
		original.offer(1);
		original.offer(2);
		original.offer(3);
		PriorityBlockingQueue<Integer> reversed = TopN.reverseQueue(original);
		
		PriorityBlockingQueue<Integer> test = new PriorityBlockingQueue<Integer>(reversed.size(), Collections.reverseOrder());
		test.offer(1);
		test.offer(2);
		test.offer(3);
		assertEquals(reversed.poll(), test.poll());
	}
}
