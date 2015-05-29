import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Unscrabbler
{
	
	public static void main(String[] args)
	{		
		DictionaryTrie dic = new DictionaryTrie();
		dic.processDictionary("res/dictionary.txt");
		Gui frame = new Gui("The Unscrabbler", 520, 700, null, JFrame.EXIT_ON_CLOSE, true, dic);
		
	}
}
