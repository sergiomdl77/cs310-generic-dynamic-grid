/**
 * This class manages all of the basic dynamic grid operations.
 * 
 * @author Sergio Delgado
 *
 * @param <T>  Type of the elements inside the grid cells.
 */
public class DynamicGrid<T>
{
	/**
	 * Dynamic array that holds all of the elements of the grid
	 */
	private DynamicArray<DynamicArray<T>> storage;	

	/**
	 * Constructor, which creates an empty table of 0 rows and 0 cols
	 */
	public DynamicGrid()
	{
		storage = new DynamicArray<DynamicArray<T>>();
	}

	
	/**
	 * Returns the number of rows in the grid. O(1).
	 * @return int value. Number of rows in the grid.
	 */
	public int getNumRow() 
	{
		return storage.size();
	}
	
	
	/**
	 * Returns the number of columns in the grid. O(1).
	 * @return int value. Number of columns in the grid.
	 */
	public int getNumCol() 
	{ 
		int numCols = 0;
		if (storage.size() > 0)    // if there is at least one row in grid
			numCols = storage.get(0).size();  // return size of first row (number of columns)
			
		return numCols;
	}
	
	
	/**
	 * It returns the value of the element of the grid at indexRow and indexCol. O(1).
	 * Throws an exception if index is out of bounds (handled by DynamicArray.get(i)).
	 * @param indexRow int value. Row index on the grid.
	 * @param indexCol int value. Column index on the grid.
	 * @return Generic type element at the cell pointed.
	 */
	public T get(int indexRow, int indexCol)
	{
		return storage.get(indexRow).get(indexCol);
	}
	
	
	/**
	 * Changes value at a cell of the grid located at a specific indexRow and indexCol. O(1).
	 * Throws an exception if the index out of bounds (handled by DynamicArray.set(i)).
	 * @param indexRow Row position in the grid.
	 * @param indexCol Column position in the grid.
	 * @param value Generic type value of the new element in the grid.
	 * @return Generic type value of the old element that got replaced at location pointed at by the parameters.
	 */
	public T set(int indexRow, int indexCol, T value)
	{
		return storage.get(indexRow).set(indexCol,value);
	}

	
	/**
	 * Copy values from newRow to add a row at the row index specified. O(C+R), where R is number of columns
	 * and R is the number of rows. Adds the new row, only if it matches the size of each row of the grid.
	 * @param index Position of the Row of the logic grid where we are adding a new value.
	 * @param newRow Dynamic array that holds the element values of the new Row to set.
	 * @return True only if the addition of the row happened.
	 */
	public boolean addRow(int index, DynamicArray<T> newRow)
	{
		boolean success = true;
		
		// make a deep copy of the newRow to insert
		DynamicArray<T> row = new DynamicArray<T>();
		for(int i=0; i<newRow.size(); i++)
			row.add( newRow.get(i) );
		
		// if there is at least one row in the grid
		if (storage.size() > 0)
		{
			// if size of newRow matches the size of first row
			if (row.size() == storage.get(0).size())
				storage.add(index, row);

			else
				success = false;
		}
		else
		{
			storage.add(row);
		}
		return success;
	}
	
	
	/**
	 * Copies values from newCol to add a column at the column index specified
	 * cannot add if the length of newCol does not match existing columns.
	 * O(RC) where R is the number of rows and C is the number of columns of the grid
	 * @param index Column of the grid in which to add the new column (newCol)
	 * @param newCol Array of values of the column that will be added to the grid
	 * @return boolean value. It returns True only if the addition on element was successful.
	 */
	public boolean addCol(int index, DynamicArray<T> newCol)
	{
		boolean success = true;
		
		// if size of newCol matches the size of grid column (meaning number of rows)
		if (newCol.size() == storage.size())
		{	
			for (int i=0; i<storage.size(); i++)
			{
				storage.get(i).add(index, newCol.get(i) );
			}
					
		}
		else
			success = false;

		return success;

	}
	
	
	/**
	 * Removes and returns a row at index x. It shifts rows to remove the gap.
	 * O(R) where R is the number of rows of the grid
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.remove(i)).
	 * @param index Row position to be removed from the grid.
	 * @return Dinamic Array of the generic type, containing elements of removed row.
	 */
	public DynamicArray<T> removeRow(int index)
	{
		return storage.remove(index);
	}

	
	/**
	 * Removes and returns a col at index x. It shifts columns to remove the gap.
	 * O(RC) where R is the number of rows and C is the number of columns.
	 * Throws IndexOutOfBoundsException for invalid index (handled by DynamicArray.remove(i)).
	 * @param index Position of the column to be removed from the grid.
	 * @return Dinamic Array of Generic type, containing elements of the removed column.
	 */
	public DynamicArray<T> removeCol(int index)
	{
		// removing the column (index) element from each row and add it to removedColumn
		DynamicArray<T> removedColumn = new DynamicArray<T>();
		for (int i=0; i<storage.size(); i++)
		{
			removedColumn.add(storage.get(i).remove(index));
		}
		
		return removedColumn;
	}


	@Override
	public String toString(){
		return "dynamic grid";
	}
	
	
	
	/**
	 * This main method is used to run a trial that tests the performance of the class DynamicGrid.
	 * @param args Command line parameters for the, which are not necessary for this particular class.
	 */
	public static void main(String[] args){
		// make some simple grids
		DynamicGrid<String> sgrid = new DynamicGrid<>();
		DynamicArray<String> srow = new DynamicArray<>();
		srow.add("English");
		srow.add("Spanish");
		srow.add("German");
		if (sgrid.getNumRow() == 0 && sgrid.getNumCol() == 0 && sgrid.addRow(0,srow)
			/*&& sgrid.getNumRow() == 1 && sgrid.getNumCol() == 3*/){
			System.out.println("Yaya 1");
		}
		
		if (sgrid.get(0,0).equals("English") && sgrid.set(0,1,"Espano").equals("Spanish") 
			&& sgrid.get(0,1).equals("Espano")) {
			System.out.println("Yaya 2");
		}

		// more complicated grids
		DynamicGrid<Integer> igrid = new DynamicGrid<Integer>();
		boolean ok = true;

		for (int i=0; i<3; i++){
			DynamicArray<Integer> irow = new DynamicArray<>();
			irow.add( (i+1) *10);
			ok = ok && igrid.addRow(igrid.getNumRow(),irow);
		}
		if (ok && igrid.getNumRow() == 3 && igrid.getNumCol() == 1 && igrid.get(2,0)==30) {
			System.out.println("Yaya 3");
		}
		
		DynamicArray<Integer> icol = new DynamicArray<>();
		icol.add(-10);
		icol.add(-20);
		ok = igrid.addCol(1,icol);
		icol.add(-30);
		if (!ok && igrid.addCol(1,icol) && igrid.getNumRow() == 3 && igrid.getNumCol() == 2){
			System.out.println("Yaya 4");		
		}
		
		DynamicArray<Integer> irow = new DynamicArray<>();

		irow.add(5); irow.add(10);
		
		
		if (/*!igrid.addRow(5,irow) &&*/  igrid.addRow(0,irow) && igrid.getNumRow() == 4 &&
			igrid.get(0,0) == 5 && igrid.get(3,1) == -30){
			System.out.println("Yay 5");		
		}
		//System.out.println(igrid.toString());
		
		irow = igrid.removeRow(2);
		if (igrid.getNumRow() == 3 && igrid.getNumCol() == 2 && irow.get(0) == 20 &&
			igrid.get(0,1) == 10 && igrid.get(2,0) == 30){
			System.out.println("Yay 6");		
		}		
	
		// removing a column
		irow = igrid.removeCol(0);   //using irow to take the value of the removed column
		if (igrid.getNumRow() == 3 && igrid.getNumCol() == 1 && irow.get(0) == 5 &&
			igrid.get(0,0) == 10 && igrid.get(2,0) == -30)
			{
			System.out.println("Yay 7");	
		}	
	}	

}