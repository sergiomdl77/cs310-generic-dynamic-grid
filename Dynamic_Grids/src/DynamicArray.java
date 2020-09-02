/**
 * This is a class that manages all basic dynamic array operations.

 * @version 1.0
 * @author Sergio Delgado.
 * 
 * @param <T> type of the array's element
 */
public class DynamicArray<T>{
	/**
	 * default capacity of array (which is 2)
	 */
	private static final int INITCAP = 2;
	/**
	 * Underlying array (holds the values of the array's elements)
	 */
	private T[] storage;	
	/**
	 * number of elements reserved in memory for the array
	 */
	private int capacity;
	/**
	 * number of existing elements in the array
	 */
	private int size; 
	
		
	
	/**
	 * Constructor with initial capacity of 2
	 */
	@SuppressWarnings("unchecked")  // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	public DynamicArray()
	{
		capacity = INITCAP;
		size = 0;
		storage = (T[]) new Object[capacity];

	}


	
	/**
	 * Constructor that receives int parameter for desired capacity.
	 * 
	 * @param initCapacity int desired capacity for the new dynamic array
	 * @throws  IllegalArgumentException if capacity desired for new array is too small.
	 */
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects 
	public DynamicArray(int initCapacity){
		
		if (initCapacity > 0) // if desired capacity greater than 0
		{	
				capacity = initCapacity;
				size = 0;
				storage = (T[]) new Object[capacity];
		}
		
		else  
			throw new IllegalArgumentException("Capacity is too small.");
	}

	/**
	 * Returns True if the index is within the range of the array size.
	 * @param index int value of index to be checked on.
	 * @return returns True only if (index is greater than or equal to 0) and (less or equal to size of array).
	 */
	private boolean isGoodIndexForAdd(int index)
	{
		return (index>=0 && index<=size);
	}
	
	/**
	 * Returns True if the index is within the range of the array size.
     * @param index int value of index to be checked on.
	 * @return returns True only if (index is greater than or equal to 0) and (less than size of array).
	 */
	private boolean isGoodIndex(int index)
	{
		return (index>=0 && index<size);
	}

	/**
	 * Returns the number of elements in the list. O(1).
     * @return Returns int number of elements in array.
	 */
	public int size()
	{	
		return size;
	}
		
	/**
	 * Returns the max number of elements before the next expansion. O(1).
	 * @return int value.  Returns max number of elements in array.
	 */
	public int capacity() { 
		return capacity;
	}
		
	/**
	 * Changes item x at index i to be value. O(1).
	 * @param index int value. Position on the array.
	 * @param value Generic type value of element at index
	 * @return Returns Generic type value of old array element at index.
	 */
	public T set(int index, T value)
	{		
		if (isGoodIndex(index))  // if index is within range, set new value at index
		{
			T oldValue = storage[index];
			storage[index] = value;
			return oldValue;
		}
		
		else
			throw new IndexOutOfBoundsException("Index out of Bounds.");

	}

	/**
	 * Return the item at index. O(1).
	 * @param index int value. Position on the array.
	 * @return Generic type value of array element at index.
	 */
	public T get(int index)
	{
		if (isGoodIndex(index))  // if index is within range, return element.
			return storage[index];
		else
			throw new IndexOutOfBoundsException("Index out of Bounds.");
	}

	/**
	 * Verifies that there is capacity for a new value on array. If not enough space, it grows 
	 * the array to twice its size.
	 */
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	private void checkCapacity()
	{
		if (size == capacity)   // if current capacity of array is maxed out, 
		{	// create new array (twice as big).
			T[] newStorage = (T[]) new Object[capacity * 2];
			
			// copy contents of the old data array (storage) onto (newStorage)
			for (int i=0; i<capacity; i++)
				newStorage[i] = storage[i];
			
			storage = newStorage;
			capacity = capacity*2;
		}
		
		return;
	}

	/**
	 * Verifies that the size array is not below one third of its capacity . If so , it shrinks 
	 * the array to half its size. 
	 */
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	private void checkEmptiness()
	{	
		// if size is less than 1/3 of capacity and  (capacity/2) won't fall below INITCAP
		if ((size < (float)(capacity)/3) && ( (capacity/2) >= INITCAP) )
		{	// create new array (half its size).
			T[] newStorage = (T[]) new Object[capacity / 2];
			
			// copy contents of the old data array (storage) onto (newStorage)
			for (int i=0; i<size; i++)
				newStorage[i] = storage[i];
			
			storage = newStorage;
			capacity = capacity/2;
			
		}
		return;
	}
	
	
	/**
	 * Adds element to end of the array (append). Doubles the capacity of no space available. O(1).
	 * @param value Generic type value of element to be added to array.
	 * @return Returns True.
	 */
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	public boolean add(T value)
	{
		add(size,value);
		return true;
	}
		
	
	/**
	 * Adds element at position (index) in the array. Doubles the capacity if no space available. O(N).
	 * @param index int value. Position where to add new element in the array.
	 * @param value Generic type value of element to be added to array.
	 */	
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	public void add(int index, T value)
	{
		if (isGoodIndexForAdd(index))
		{
			checkCapacity();

			// moving all elements (after index) one position forward
			for (int i=size; i>index; i--)
				storage[i] = storage[i-1];
			
			// inserting the new value
			storage[index] = value;
			size++;
			return;
		}
			
		else
			throw new IndexOutOfBoundsException("Index out of Bounds.");

	}
	
	

		
	/**
	 * Removes element of the array at index. Halves capacity of array if size drops below 1/3
	 * of current capacity. Capacity should not go below INITCAP. O(N).
	 * @param index Generic type element that was removed from array at index.
	 * @return Returns True if the removal only if an element was done successfully
	 */
	@SuppressWarnings("unchecked") // Tag meant to skip giving warnings due to unsafe (T) casting on Objects
	public T remove(int index)
	{
		if (isGoodIndex(index))
		{
			T removedValue = storage[index];
			
			// moving all elements (after index) one position backwards
			for (int i=index; i< (size-1); i++)
				storage[i] = storage[i+1];
			
			size--;
			
			checkEmptiness();
						
			return removedValue;
		}
			
		else
			throw new IndexOutOfBoundsException("Index out of Bounds.");
	
	}  
	
	
	
	@Override
	public String toString(){
		// return string representation of DynamicArray
		return "DynamicArray with size "+size+", capacity "+capacity;
		
  	}
  	
	/**
	 * This main method is used to run a trial that tests the performance of the class DynamicArray.
	 * @param args Command line parameters for the, which are not necessary for this particular class.
	 */
	public static void main (String args[]){

		DynamicArray<String> ida = new DynamicArray<String>();
		if ((ida.size() == 0) && (ida.capacity() == 2)){
			System.out.println("Yay 1");
		}

		// adding to the list?
		boolean ok = true;
		
		ida.add("0");
		ida.add("5");
		ida.add("10");
		
		if (ok && ida.size()==3 && ida.get(2) == "10" && ida.capacity() == 4 ){
			System.out.println("Yay 2");
		}

		ida.add(1,"-10");
		ida.add(4,"100");
		if (ida.set(1,"-20")== "-10" && ida.get(2) == "5" && ida.size() == 5 
			&& ida.capacity() == 8 ){
			System.out.println("Yay 3");
		}
		// removing from the list?
		if (ida.remove(0) == "0" && ida.remove(0) == "-20" && ida.remove(2) == "100"  
			&& ida.size() == 2  && ida.capacity() == 4 ){
			System.out.println("Yay 4");
		}		
		// remember to tests more things...
	}

}