package program;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class which is used as a helper for the transforming of one word to another. This class assumes that
 * both the source word and the destination word are of the same length. The main data structure used to hold the 
 * dictionary information is an adjacency list where each word in the dictionary is a node and there is an edge between 
 * any two given words if they're different by a single letter e.g. an edge will connect cat and bat but cat and cog will 
 * not be connected. 
 * @author David Hutchinson
 */
public class DictionaryHelper {
	
	/**
	 * Flag to indicate no previous nodes, used only for source node
	 */
	private static final String NO_PREVIOUS_NODES = "-1";
	
	
	/**
	 * Method which reads in a dictionary as a txt file and initializes an adjacency list from based on it. Each word within the dictionary
	 * will have its own key with its corresponding values being an empty ArrayList (for now). 
	 * @param word The source word, this is used to limit the dictionary keys to those of the size of the source word.
	 * @return The adjacency list with all words having a key, each with an empty ArrayList.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public HashMap<String, ArrayList<String>> readInDictionary(String word) throws FileNotFoundException, IOException{
		URL url = new URL("http://cs1.ucc.ie//~dgh1//dictionary.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		HashMap<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>>();
		while((line = reader.readLine()) != null) {
			if ( line.length() == word.length() ) {
				dictionary.put(line, new ArrayList<String>());
			}
		}
		reader.close();
		return dictionary;
	}
	
	/**
	 * Method is used fill in the empty ArrayLists corresponding to the keys in the adjacency list. Upon execution the values for a key will contain other words in the dictionary 
	 * which are one letter away from that key. E.g. Cat -> Cot, Car, Bar.
	 * @param dictionary The input dictionary containing only keys with empty ArrayLists as values.
	 * @return A completed adjacency list with a key for each word in the dictionary. The key's values will hold
	 * any word which is a single letter away from that key, according to the input dictionary.
	 */
	public HashMap<String, ArrayList<String>> constructSimilarWordsGraph(HashMap<String, ArrayList<String>> dictionary) {
		for ( String key : dictionary.keySet() ) {
			dictionary.put(key, getSimilarWords(key, dictionary));
		}

		return dictionary;
	}
	
	/**
	 * Helper method which will compute all words which lie one letter away from a given input word, according to an
	 * input dictionary.
	 * @param word The input word
	 * @param dictionary The dictionary to check the validity of a word generated before adding to the list of valid words.
	 * @return An ArrayList of all words which are a single letter away from the given input word.
	 */
	public ArrayList<String> getSimilarWords(String word, HashMap<String, ArrayList<String>> dictionary) {
		StringBuilder builder = new StringBuilder(word);
		ArrayList<String> similarWords = new ArrayList<String>();
		for ( int i = 0; i < word.length(); i++) {
			for ( char c = 'a'; c <= 'z'; c++) {
				builder.setCharAt(i, c);
				if ( dictionary.containsKey(builder.toString()) && !builder.toString().equals(word)) {
					similarWords.add(builder.toString());
				}
			}
			builder.setLength(0);
			builder.append(word);
		}
		return similarWords;
	}
	
	/**
	 * Helper method which creates a HashMap with each entry in the dictionary having a key. The
	 * corresponding value will be a boolean. The default value will be false, indicating that the node has not
	 * been visited yet. Method is used to ensure the search does not visit the same node/word twice.
	 * @param The input dictionary
	 */
	public HashMap<String, Boolean> makeVisitedMap(  HashMap<String, ArrayList<String>> dictionary ) {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		for ( String key : dictionary.keySet() ) {
			visited.put(key, false);
		}
		return visited;
	}
	
	/**
	 * Method which uses breadth-first search in order to get the shortest path between two given words. A LinkedList is
	 * used to hold which nodes are next to be expanded. A node is popped from the LinkedList and each of its neighbours expanded, marked
	 * as visited and its parent node stored, these neighbours will, in turn, also be expanded. This continues until the element popped is the 
	 * destination word/node, once this occurs the search will backtrack up the graph using the previousNodeMap in order to find the path from
	 * source to destination. 
	 * @param sourceWord The starting word.
	 * @param destinationWord The word to transform into.
	 * @param dictionary The input dictionary.
	 * @return The path from the source word to the destination word.
	 */
	public ArrayList<String> transformWord(String sourceWord, String destinationWord, HashMap<String, ArrayList<String>> dictionary) {
		HashMap<String, String> previousNodeMap = new HashMap<String, String>();
		previousNodeMap.put(sourceWord, NO_PREVIOUS_NODES);
		LinkedList<String> nodes = new LinkedList<String>();
		nodes.add(sourceWord);
		HashMap<String, Boolean> visited = makeVisitedMap(dictionary);
		visited.put(sourceWord, true);
		while( !visited.isEmpty())  {
			String currentWord = nodes.pop();
			if ( currentWord.equals(destinationWord)) {
				String word = destinationWord;
				ArrayList<String> output = new ArrayList<String>();
				output.add(word);
				while ( !word.equals(NO_PREVIOUS_NODES) ) {
					String previous = previousNodeMap.get(word);
					if( !previous.equals(NO_PREVIOUS_NODES)) {
						output.add(previous);
					}
					word = previous;
				}
				Collections.reverse(output);
				return output;
			}
			for ( String adjacentWord : dictionary.get(currentWord)) {
				if ( !visited.get(adjacentWord)) {
					previousNodeMap.put(adjacentWord, currentWord);
					visited.put(adjacentWord, true);
					nodes.add(adjacentWord);
				}
			}
		}
		return null;
	}
	
	/**
	 * Method used to calculate the Hamming Distance between any two given words.
	 * @param firstWord The source word
	 * @param secondWord The word to be compared against.
	 * @return The distance between both words.
	 */
	public int getDistance(String firstWord, String secondWord) {
		int distance = 0;
		for ( int i = 0; i < firstWord.length(); i++) {
			if ( firstWord.charAt(i) != secondWord.charAt(i)) {
				distance++;
			}
		}
		return distance;
	}
}
