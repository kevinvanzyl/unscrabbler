import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;


public class DictionaryTrie {
	
	private TrieNode root;
	//references for processing to reduce heap space
	private TrieNode currentnode;
	private int treesize;
	
	public DictionaryTrie(){
		TrieNode r = new TrieNode('0', 0);
		root = r;
	}
	
	/**
	 * get nethod that returns the root node of the trie
	 * @return the root node of the trie
	 * */
	public TrieNode getRoot(){
		return root;
	}
	
	/**
	 * get method that returns the number of nodes in the trie
	 * @return the number of nodes in the trie
	 * */
	public int getTreesize(){
		return treesize;
	}
	
	/**
	 * processes a dictionary into a trie structure
	 * @param  filename the location of the dictoinary file used for processing
	 * */
	public void processDictionary(String filename){
		//FileInputStream in = new FileInputStream(filename);
		InputStream in = this.getClass().getResourceAsStream(filename);
		Scanner input = new Scanner(in);
		
		while (input.hasNext()) {
			String word = input.next();
			
			char chararray[] = word.toCharArray();
			Arrays.sort(chararray);
			this.currentnode = root;
			processSearch(chararray[0], 0, chararray, word);
		}
		input.close();
	}
	
	/**
	 * searches down the trie as it is being constructed, if the node we are looking for is not there it is added using processInsert
	 * @param  letter  the element of node we are searching for
	 * @param  count keeps track of the depth
	 * @param  chararray the word sorted alphabetically and broken into a char array
	 * @param  word the word to be filed in the trie
	 * */
	public void processSearch(char letter, int count, char[] chararray, String word) {
		if (currentnode.getChildcount() != 0) {
			for (TrieNode child: currentnode.getChildren())
			{				
				if (child.getElement() == letter)
				{
					count++;
					if (count != chararray.length) {
						currentnode = child;
						processSearch(chararray[count], count, chararray, word);
					}
					else {
						child.addWord(word);
						return;
					}
				}			
			}
		}
		
		processInsert(chararray[count], count, chararray, word);
	}
	
	/**
	 * trie node is added using processInsert
	 * @param  letter  the element of node we are searching for
	 * @param  count keeps track of the depth
	 * @param  chararray the word sorted alphabetically and broken into a char array
	 * @param  word the word to be filed in the trie
	 * */
	public void processInsert(char letter, int count, char[] chararray, String word) {
		TrieNode newnode = new TrieNode(letter, count+1, currentnode);
		treesize++;
		currentnode.addChild(newnode);
		if (count+1 < chararray.length) {
			count++;
			currentnode = newnode;
			processInsert(chararray[count], count, chararray, word);
		}
		else {
			newnode.addWord(word);
		}
	}
	
	/**
	 * Get the paths that need to be searched which will provide us with all combinations of words
	 * @return The paths that need to be searched in order to get all combinations of letters given
	 * @param  chararray the word sorted alphabetically and broken into a char array
	 * */
	public String[] findPaths(char[] chararr){
		String[] store = new String[(int)Math.pow(2, chararr.length-1)];
		store = arrJoin(new String[]{String.valueOf(chararr[chararr.length-1])}, findPath(new String[]{String.valueOf(chararr[chararr.length-1])}, chararr));

		return store;
	}
	
	public String[] findPath(String[] store, char[] arr){
		String[] temp = new String[500];
		int count = 0;
		for (String item: store)
			if (item != null)
				for (char i: arr)
					if (item.charAt(0) > i)
					{
						temp[count] = String.valueOf(i) + item;
						count++;
					}
		if (temp[0] != null)
		{
			int i = 0;
			String[] n = new String[count];
			for(String item: temp)
			{
				if (item != null)
				{
					n[i] = item;
					i++;
				}
			}
			temp = n;
				
			return arrJoin(temp, findPath(temp, arr));
		}
		else
			return new String[]{};
	}
	
	/**
	 * Joins 2 string arrays
	 * @return a new string array containing the elements of both arrays
	 * @param a string array 1
	 * @param b string array 2
	 * */
	public String[] arrJoin(String[] a, String[] b)
	{
			   int aLen = a.length;
			   int bLen = b.length;
			   String[] c = new String[aLen+bLen];
			   System.arraycopy(a, 0, c, 0, aLen);
			   System.arraycopy(b, 0, c, aLen, bLen);
			   return c;
	}
	
	/**
	 * Traces the paths found by findPaths, collecting words along the way
	 * @return an array of words as strings
	 * @param paths The paths represented as a series of strings that need to be traversed to get all combinations
	 * */
	public String[] tracePaths(String[] paths){
		String[] words = new String[]{};
		
		for(String str: paths){
			char[] path = str.toCharArray();
			TrieNode node = root;
			for(char i: path){
				for(TrieNode child: node.getChildren()){
					if (child.getElement() == i){
						words = arrJoin(words, child.getWords());
						node = child;
						break;
					}
				}
			}
		}
		
		return arrayUnique(words);
	}
	
	/**
	 * Reduces a string array to contain only unique elements
	 * @return a new string array containing only unique elements
	 * @param words An array of strings that may contain duplicates
	 * */
	public String[] arrayUnique(String[] words){
		for(int i = 0;  i < words.length; i++){             

		    String cur = words[i];

		    if(cur != null){
		        for(int j=i+1; j<words.length; j++){
		            if(words[j] == cur){
		                words[j] = null;
		            }
		        }
		    }
		}
		String[] tmp = new String[]{};
		for (int i=0; i< words.length; i++)
		{
			if (words[i] != null)
				tmp = arrJoin(tmp, new String[]{words[i]});
		}
		return tmp;
	}
	
	/**
	 * adds up the score for each word and counts the letters for it
	 * @return A 2d int array containing scores and letter-counts ordered the same as the word results
	 * @param words An array of words to be scored and letter-counted
	 * */
	public int[][] scoreWords(String[] words){
		int[][] scores = new int[words.length][2];
		int count = 0;
		for(String word: words){
			char[] arr = word.toCharArray();
			int score = 0;
			int countletters = 0;
			for(char i: arr){
				countletters++;
				if(i=='k')
					score+=5;
				else if (i=='d' || i=='g')
					score+=2;
				else if (i=='j' || i=='x')
					score+=8;
				else if (i=='q' || i=='z')
					score+=10;
				else if (i=='b' || i=='c' || i=='m' || i=='p')
					score+=3;
				else if (i=='f' || i=='h' || i=='v' || i=='w' || i=='y')
					score+=4;
				else
					score++;
			}
			scores[count][0] = score;
			scores[count][1] = countletters;
			count++;
		}
		return scores;
	}
	

}
