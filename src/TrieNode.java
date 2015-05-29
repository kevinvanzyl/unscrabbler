
public class TrieNode {
	
	private char element;
	private int level;
	private boolean complete;
	private TrieNode parent;
	private TrieNode[] children;
	private int childcount;
	private String[] words;
	private int wordcount;
	
	public TrieNode(){
		wordcount = 0;
		childcount = 0;
		complete = false;
		words = new String[]{};
		children = new TrieNode[]{};
	}
	
	public TrieNode(char el, int l) {
		this();
		element = el;
		level = l;
	}
	
	public TrieNode(char el, int l, TrieNode p){
		this();
		element = el;
		level = l;
		parent = p;
	}
	
	/**
	 * A Get method to check if a node contains any words
	 * @return true if complete is set to true after adding words
	 * */
	public boolean isComplete(){
		return complete;
	}
	
	/**
	 * A Get method to check how many children this node has
	 * @return the number of children that the node has
	 * */
	public int getChildcount(){
		return childcount;
	}
	
	/**
	 * A Set method to set the element for a node
	 * @param e the new element
	 * */
	public void setElement(char e){
		element = e;
	}
	
	/**
	 * A Get method to get the element of a node
	 * @return the element
	 * */
	public char getElement(){
		return element;
	}
	
	/**
	 * A Set method to set the level for a node
	 * @param l the new level
	 * */
	public void setLevel(int l){
		level = l;
	}
	
	/**
	 * A Get method to check the level of this node
	 * @return the level
	 * */
	public int getLevel(){
		return level;
	}
	
	/**
	 * A Get method to fetch all the words that this node contains
	 * @return the words contained by the node
	 * */
	public String[] getWords(){
		return words;
	}
	
	/**
	 * A method to add a new child node to this node
	 * @param kid The new child to be added
	 * */
	public void addChild(TrieNode kid){
		if (childcount == 0) children = new TrieNode[1];
		if (childcount == children.length){
			TrieNode[] kids = new TrieNode[childcount+1];
			int count = 0;
			for(TrieNode c: children)
			{
				kids[count] = c;
				count++;
			}
			children = kids;
		}
		children[childcount] = kid;
		childcount++;
	}
	
	/**
	 * A Get method to fetch all the children of this node
	 * @return the children of the node
	 * */
	public TrieNode[] getChildren(){
		if (children.length == 0)
			return new TrieNode[0];
		return children;
	}
	
	/**
	 * A Set method to set the parent for a node
	 * @param folks The new parent node
	 * */
	public void setParent(TrieNode folks){
		parent = folks;
	}
	
	/**
	 * A Get method to fetch the parent of this node
	 * @return the parent
	 * */
	public TrieNode getParent(){
		return parent;
	}
	
	/**
	 * A method to add a new word to this node
	 * @param word The new word to be added
	 * */
	public void addWord(String word){
		if (!complete) complete = true;
		if (wordcount == 0) words = new String[1];
		if (wordcount == words.length)
		{
			String[] collection = new String[wordcount*2];
			int count = 0;
			for(String w: words)
			{
				collection[count] = words[count];
				count++;
			}
			words = collection;
		}
		words[wordcount] = word;
		wordcount++;
	}
}
