import javax.swing.table.DefaultTableModel;


class MyTable extends DefaultTableModel
{
	
	public MyTable(String[][] a, String[] b){
		super(a, b);
	}
	
	//make table readonly
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
}