package tests;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import program.DictionaryHelper;

public class DictionaryHelperTest {
	DictionaryHelper helper = new DictionaryHelper();
	private static final String TEST_WORD = "cat";
	private static final int AMOUNT_OF_THREE_CHARACTER_WORDS = 1220;
	private static final String SECOND_TEST_WORD = "dog";
	
	@Test
	public void testFileAvailable() {
		File file = new File("C:\\Users\\David\\Desktop\\dictionary.txt");
		assertTrue(file.exists());
	}
	
	@Test
	public void testReadDictionaryCorrectly() {
		try { 
			assertEquals(AMOUNT_OF_THREE_CHARACTER_WORDS,  helper.readInDictionary(TEST_WORD).size());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
	
	@Test
	public void testWordTransform() {
		HashMap<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>> ();
		try {
			dictionary = helper.readInDictionary(TEST_WORD);
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		dictionary = helper.constructSimilarWordsGraph(dictionary);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("cat");
		expected.add("cot");
		expected.add("dot");
		expected.add("dog");
		assertEquals(expected, helper.transformWord(TEST_WORD, SECOND_TEST_WORD, dictionary));
	}
	
	@Test
	public void testGetDistance() {
		assertEquals(3, helper.getDistance(TEST_WORD, SECOND_TEST_WORD));
	}
}
