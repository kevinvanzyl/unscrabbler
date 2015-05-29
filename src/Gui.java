import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

public class Gui extends JFrame
{		
	private JFormattedTextField[] tiles;
	
	private String[] columns = {"Word", "# of Letters", "Score"};
	private String[][] tabledata = {{"","",""}};
	public JTable results = new JTable(new MyTable(tabledata, columns));
	public DictionaryTrie dic;
	public char chararray[];
	public String inputstring;
	
	public Gui(){
		chararray = new char[12];
		inputstring = "";
		tiles = new JFormattedTextField[12];
	}
	
	public Gui(String title, int x, int y, Component relativity, int closeop, boolean beVisible, DictionaryTrie dic)
	{
		this();
		this.dic = dic;
		this.setTitle(title);
		this.setSize(x, y);
		this.setLocationRelativeTo(relativity);
		this.setDefaultCloseOperation(closeop);
		this.setVisible(beVisible);
		paint();
	}
	
	/**
	 * Sets the layout and adds some panels
	 * */
	public void paint(){
		this.setLayout(new FlowLayout());
		this.add(paintTiles());
		this.add(paintActions());
		this.add(paintResults());
		this.revalidate();
	}
	
	/**
	 * Creates the panel holding the tile elements
	 * @return a JPanel with all the tiles
	 * */
	public JPanel paintTiles(){
		JPanel tileSection = new JPanel();
		tileSection.setLayout(new FlowLayout());
		
		tileSection.setBorder(new TitledBorder("Fill in any number of tiles below."));
		for (int i=0; i<12; i++)
		{
			try {
				tiles[i] = new JFormattedTextField(new MaskFormatter("?"));
				tiles[i].setPreferredSize(new Dimension(25,30));
				tileSection.add(tiles[i]);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		tileSection.add(new JLabel("                   "));
		
		tileSection.setAlignmentY(0);
		return tileSection;
	}
	
	/**
	 * Creates a panel for all the buttons
	 * @return a Jpanel containing all the buttons needed
	 * */
	public JPanel paintActions(){
		JPanel actionSection = new JPanel();
		actionSection.setLayout(new GridLayout(4, 1));
		
		//find button
		JButton cmdFind = new JButton("Find!");
		cmdFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				char[] chararr = new char[12];
				String input = "";
				for (int i=0; i< 12; i++){
					if (!tiles[i].getText().trim().equals("")){
						input += tiles[i].getText().toLowerCase();
					}
				}
				
				chararr = input.toCharArray();
				input = "";
				for(char i: chararr)
					if (i != ' '){
						input += i;
					}
				Arrays.sort(chararr);
				String[] paths = dic.findPaths(chararr);
				String[] words = dic.tracePaths(paths);
				int[][] data = dic.scoreWords(words);
				
				DefaultTableModel tm = (MyTable)results.getModel();
				
				int count = 0;
				for(String word: words){
					Object[] o = new Object[]{word, data[count][1], data[count][0]};
					tm.addRow(o);
					count++;
				}
			}
		});
		
		//clear button
		JButton cmdClear = new JButton("Clear!");
		cmdClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				for (int i=0; i<12; i++)
					tiles[i].setText("");
				DefaultTableModel tm = (MyTable)results.getModel();
				tm.setRowCount(0);
			}
		});
		
		actionSection.add(cmdFind);
		actionSection.add(cmdClear);
		
		return actionSection;
	}
	
	/**
	 * Creates the JPanel containing the table of results
	 * @return A JPanel of results within a table
	 * */
	public JPanel paintResults(){
		JPanel resultSection = new JPanel();
		resultSection.setLayout(new FlowLayout());

		resultSection.add(new JScrollPane(results));
		
		return resultSection;
	}
	
	
}
