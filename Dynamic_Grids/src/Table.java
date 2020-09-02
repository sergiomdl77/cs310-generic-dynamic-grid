/**
 * Class that implements the DynamicArray and DynamicGrid classes to create and 
 * manage the elements of a grid, whose element values are the result of combining
 * the values of a column and a row.
 * 
 * @author Sergio Delgado
 *
 * @param <RowType> Type of elements of row heads.
 * @param <ColType> Type of elements of columns heads.
 * @param <CellType> Type of elements of grid (board).
 * @param <OpType> Type of the operator that will combine row-column values to get a result.
 */
public class Table<RowType, ColType, CellType, OpType extends Combiner<RowType,ColType,CellType>>{
	
	private DynamicArray<RowType> rowHead;	// rowHead as a list of RowType values
	private DynamicArray<ColType> colHead;	// colHead as a list of ColType values
	private DynamicGrid<CellType> board;	// a 2-D grid of CellType values determined by
											//		rowHead, colHead, and op
	private OpType op;						// op that defines a function f:
											//		f(RowType,ColType)-> CellType
	
    /**
     * Constructor with the operator object as parameter.  Instantiates  two Dynamic Arrays
     * to store row heads and column heads, and a Dynamic Grid to store values of the board. It
     * also sets the operator to the parameter received.
     * @param oper Generic type, object that executes the basic operation between two operands.
     */
	public Table(OpType oper)
	{
		rowHead = new DynamicArray<RowType>();
		colHead = new DynamicArray<ColType>();
		board = new DynamicGrid<CellType>();
		op = oper; 
		
	}
	
	
	/**
	 * Returns the number of rows in the grid. O(1).
	 * @return int value. Number of elements in rowHead.
	 */
	public int getSizeRow()
	{
		return rowHead.size();
	}


	/**
	 * Returns the number of columns in the grid. O(1).
	 * @return int value. Number of elements in colHead
	 */
	public int getSizeCol()
	{
		return colHead.size();
	}
	
	
	/**
	 * Returns the item at index r from rowHead. O(1). 
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.get(i)).
	 * @param r Position of the row in the grid whose value we are retrieving from rowHead.
	 * @return Generic type Value of the element at position r from rowHead.
	 */
	public RowType getRowHead(int r) 
	{
		return rowHead.get(r);
	}
	
	
	/**
	 * Return the item at index c from colHead. O(1).
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.get(i)).
	 * @param c Position of the column in the grid whose value we are retrieving from colCHead.
	 * @return Generic type Value of the element at position c from colHead.
	 */
	public ColType getColHead(int c) 
	{
		return colHead.get(c);
	}

	
	/**
	 * Returns the item at index (r,c) from board. O(1). 
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.get(i)).
	 * @param r Position of the row in the grid whose value we are retrieving from board.
	 * @param c Position of the column in the grid whose value we are retrieving from board.
	 * @return Generic type Value of the element at position (r,c) from board.
	 */
	public CellType getCell(int r, int c) 
	{
		return board.get(r,c);
	}
	
	
	/**
	 * Change the operation. Re-calculate and reset the cells of the board. O(CR) where C is the number of columns
	 * and R is the number of rows of the grid
	 * @param oper operator type object that is in charge or combining two operands and return a result.
	 */
	public void setOp(OpType oper) 
	{
		op = oper;
		
		// reseting the values of the cells of the board
		for (int row=0; row<rowHead.size(); row++)
		{
			for (int col=0; col<colHead.size(); col++)
			{
				board.set(row, col, op.combine( rowHead.get(row), colHead.get(col) ) );
			}
		}
		
	}


	/**
	 * Inserts v to rowHead at index i. Inserts a new row to the grid at row index i.
	 * Calculates the new row based on v, existing colHead and op. 
	 * i may be equal to the size (indicating that you are appending a row).
	 * O(C+R) where R is the number of rows of the grid and C is the number of columns of the grid.
	 * @param i Position on the rowHead where to add a new element.
	 * @param v Generic type value of the element to be added at position i.
	 * @return Returns True only if addition of elment was successful.
	 */
	public boolean addRow(int i, RowType v)
	{
		boolean success = true;
		
		// if index (i) is not out of bounds with respect to rowHead
		if (i <= rowHead.size())
		{	
			rowHead.add(i,v);  // insert new row head in rowHead
			
			// create new row to insert into the 2D board
			DynamicArray<CellType> newRow = new DynamicArray<CellType>();
			for (int index=0; index<colHead.size(); index++)  // if there is no rows on the table yet, it will create an empty newRow for board
			{
				newRow.add( op.combine(v, colHead.get(index)));
			}
			
			// insert new row into the board
			board.addRow(i, newRow);  // If there were no rows it will send an empty newRow to add to the board.
			
		}
		else
			success = false;
			
		return success;
	}
	
	
	/**
	 * Inserts v to colHead at index i. Inserts a new column to the grid at column index i.
	 * Calculates the new column based on v, existing rowHead and op.
	 * i may be equal to the size (indicating that you are appending a column).
	 * O(CR) where R is the number of rows of the grid and C is the number of columns of the grid.
	 * @param i Position on colHead where to add a new element.
	 * @param v Generic type value of element to be added at position i.
	 * @return Returns True only if the addition of new element was successful.
	 */
	public boolean addCol(int i, ColType v)
	{
		boolean success = true;
		
		// if index (i) is not out of bounds with respect to colHead
		if (i <= colHead.size())
		{	
			colHead.add(i,v);  // insert new column head in colHead
			
			// create new column to insert into the 2D board
			DynamicArray<CellType> newCol = new DynamicArray<CellType>();
			for (int index=0; index<rowHead.size(); index++) // if there is no rows on the table yet, it will create an empty newCol for board
			{
				newCol.add( op.combine(rowHead.get(index), v) );
			}
			
			// insert new column into the board
			board.addCol(i, newCol);   // If there were no rows it will send an empty newCol to add to the board.
			
		}
		else
			success = false;
	
		return success;
	}
	
	
	/**
	 * Removes and return value from rowHead at index i. Removes row i from grid.
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.remove(i)).
	 * O(R) where R is the number of rows of the grid.
	 * @param i Position on the grid where to remove a row from.
	 * @return Generic type value of the element from rowHead at position i.
	 */
	public RowType removeRow(int i)
	{
		// removing the element from rowHead.
		RowType removedRow = rowHead.remove(i);
		
		// removing the row from the board
		board.removeRow(i);
		
		return removedRow;
	}

	
	/**
	 * Removes and return value from colHead at index i. Removes column i from grid.
	 * Throws IndexOutOfBoundsException for invalid index. O(CR) where R is the number of rows
	 * and C is the number of columns of the grid.
	 * @param i Position on the grid where to remove a column from.
	 * @return Generic type value of the element removed from colHead.
	 */
	public ColType removeCol(int i)
	{
		// removing element from colHead
		ColType removedCol = colHead.remove(i);
		
		// removing the column from the board
		board.removeCol(i);
		
		return removedCol;
	}
	
	
	/**
	 * Changes value of rowHead at index i to be v. Changes the ith row of grid using v, the ColTypes, and op.
	 * Return old value of rowHead from index i. Throws IndexOutOfBoundsException for invalid index.
	 * O(C) where C is the number of columns of the grid.
	 * @param i Position on rowHead where to change the value and of the board where to change row values.
	 * @param v Generic type value to be added to rowHead.
	 * @return Generic type value of the old element from rowHead.
	 */
	public RowType setRow(int i, RowType v)
	{
		// reseting position (i) in rowHead with new value (v)
		RowType oldRowElem = rowHead.set(i, v);
		
		// reseting each element of the board from row (i) with the new calculated value
		for(int index=0; index<colHead.size(); index++)
		{
			board.set(i, index, op.combine(v, colHead.get(index)));
		}
		
		return oldRowElem;
	}
	
	/**
	 * Changes value of colHead at index i to be v. Changes the ith column of grid using v, the RowTypes, and op.
	 * Return old value of colHead from index i. Throws IndexOutOfBoundsException for invalid index.
	 * O(R) where R is the number of rows of the grid.
	 * @param i Position on colHead where to change the value and of the board where to change column values.
	 * @param v Generic type value to be added to colHead.
	 * @return Generic type value of the old element from colHead.
	 */
	public ColType setCol(int i, ColType v){
		
		// reseting position (i) in rowHead with new value (v)
		ColType oldColElem = colHead.set(i, v);
		
		// reseting each element of the board from row (i) with the new calculated value
		for(int index=0; index<rowHead.size(); index++)
		{
			board.set(index, i, op.combine(rowHead.get(index), v) );
		}
		
		return oldColElem;
	}


	/**
	 *  Find the width we should use to print the specified column
	 *  @param colIndex column index to specify which column of the grid to check width
	 *  @return an integer to be used to for formatted printing of the column
	 */
	private int getColMaxWidth(int colIndex){
		int ans = -1;
		for (int i=0;i<this.getSizeRow();i++){
			int width = (this.getCell(i,colIndex)).toString().length();
			if (ans<width)
				ans = width;
		}
		return ans+1;	
	}

	/**
	 *  Find the width we should use to print the rowHead 
	 *  @return an integer to be used to for formatted printing of the rowHead
	 */
	private int getRowHeadMaxWidth(){
		int ans = -1;
		for (int i=0;i<this.getSizeRow();i++){
			int width = (rowHead.get(i)).toString().length();
			if (ans<width)
				ans = width;
		}
		return ans+1;	
	}

	
	/**
	 *  Construct a string representation of the table
	 *  @return a string representation of the table
	 */
	@Override
	public String toString(){
		
		if(getSizeRow() == 0 && getSizeCol()==0 ){ return "Empty Table"; }

		// basic info of op and size
    	StringBuilder sb = new StringBuilder("============================\nTable\n");
    	sb.append("Operation: "+op.getClass()+"\n");
    	sb.append("Size: "+ getSizeRow() + " rows, " + getSizeCol()+ " cols\n");

		// decide how many chars to use for rowHead 
    	int rowHeadWidth = getRowHeadMaxWidth(); 
    	int totalWidth = rowHeadWidth;
    	DynamicArray<Integer> colWidths = new DynamicArray<>();
    	sb.append(String.format(String.format("%%%ds",rowHeadWidth)," "));

		// colHead 
    	for (int i=0; i<getSizeCol(); i++){		
    		int colWidth = getColMaxWidth(i);
    		colWidths.add(colWidth);
    		totalWidth += colWidth+1;
    		sb.append(String.format(String.format("|%%%ds",colWidth),colHead.get(i)));
    	}

    	sb.append("\n"+String.format(String.format("%%%ds", totalWidth), " ").replace(" ","-")+"\n");

		// row by row
    	for(int i=0; i<getSizeRow(); i++){		
    		sb.append(String.format(String.format("%%%ds",rowHeadWidth),rowHead.get(i)));
    		for (int j=0;j<getSizeCol(); j++){
	    		int colWidth = colWidths.get(j);
      			sb.append(String.format(String.format("|%%%ds",colWidth),board.get(i,j)));
      		}
      		sb.append("\n");
    	}
    	sb.append("============================\n");
    	return sb.toString();

	}

	
	/**
	 * This main method is used to run a trial that tests the performance of the class Table.
	 * @param args Command line parameters for the, which are not necessary for this particular class.
	 */
	public static void main(String[] args){
		StringAdder sa = new StringAdder();
		Table<String, String, String, StringAdder> stable = new Table<>(sa);
		stable.addCol(0,"apple");

		stable.addRow(0,"red");
		stable.addRow(1,"yellow");
//		stable.addCol(0,"apple");
		
		if (stable.getSizeRow() == 2 && stable.getSizeCol() == 1 &&
			stable.getCell(0,0).equals("red apple") && stable.getCell(1,0).equals("yellow apple")) {
			//stable.getCell(0, 0);
			System.out.println("Yay 1");
		}
		System.out.println(stable.toString());		
		
		stable.addCol(1,"banana");
		stable.addCol(1,"kiwi");
		stable.addRow(2,"green");
		if (stable.getSizeRow()==3 && stable.getSizeCol()==3 && stable.getRowHead(2).equals("green")
			&& stable.getColHead(2).equals("banana") && stable.getCell(2, 1).equals("green kiwi")){
			System.out.println("Yay 2");			
		}
		System.out.println(stable.toString());
		
		stable.removeRow(0);
		stable.setCol(2,"orange");
		if (stable.getSizeRow()==2 && stable.getSizeCol()==3 && stable.getRowHead(0).equals("yellow")
			&& stable.getColHead(2).equals("orange") && stable.getCell(0, 2).equals("yellow orange")){
			System.out.println("Yay 3");			
		}		
		System.out.println(stable.toString());

		Table<Integer,Integer, Integer, IntegerComb> itable = new Table<>(new IntegerAdder());
		for (int i=0;i<5; i++){
			itable.addRow(itable.getSizeRow(),i+1);
			itable.addCol(0,(i+1)*10);
		}
		if (itable.getSizeRow()==5 && itable.getSizeCol()==5 && itable.getCell(0, 0)==51
			&& itable.getCell(4, 0)==55 && itable.getCell(3, 4)==14 ){
			System.out.println("Yay 4");			
		}
		System.out.println(itable.toString());
		
		itable.setOp(new IntegerTimer());
		if (itable.getSizeRow()==5 && itable.getSizeCol()==5 && itable.getCell(0, 0)==50
			&& itable.getCell(4, 0)==250 && itable.getCell(3, 4)==40 ){
			System.out.println("Yay 5");			
		}
		System.out.println(itable.toString());
					
	}
	
}