package program;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) {
		DictionaryHelper helper = new DictionaryHelper();
		try { 
			HashMap<String, ArrayList<String>> dict = helper.readInDictionary("cat");
			dict = helper.constructSimilarWordsGraph(dict);
			ArrayList<String> path = helper.transformWord("cat", "dog", dict);
			for ( String word : path ) {
				System.out.println(word);
			}
		} catch (IOException e) {
			System.out.println();
		}
	}
}
