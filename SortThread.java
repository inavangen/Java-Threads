import java.lang.Thread;
import java.util.Arrays;
class SortThread extends Thread {
	
	private String id;
	private String [] word_array;
	private String [] word_array2;
	private String [] array; // Used for sorting
	private Monitor monitor;
 
 	/* Receiving one array */
	SortThread(String id, String[]word_array, Monitor monitor){
		this.id = id;
		this.monitor = monitor;
		this.word_array = word_array;
		//System.out.println("Created thread: " + id);
	}

	/* Receiving two arrays */
	SortThread(String id, String[]word_array, String[]word_array2, Monitor monitor){
		this.id = id;
		this.monitor = monitor;
		this.word_array = word_array;
		this.word_array2 = word_array2;
		//System.out.println("Created thread: " + id);
	}

	public void run(){
		//System.out.println("Run thread");
		String [] new_array;
		
		/* If not contianing two arrays */
		if (word_array2 == null){
			new_array = sort(word_array); 
		/* Containing two arrays. */
		} else {
			new_array = merge(word_array, word_array2);
		}
		/* Notifies monitor that thread are finished
		* with either merging or sorting. Doesn't matter
		* cause it is one array anyways */
		monitor.notify(new_array);
	}

	/*Is this actually used? */
	public String [] get_word_array(){
		return word_array;
	}

	public String [] sort(String [] array){
		/*TODO: quicksort algoritm*/
		//Arrays.sort(array);
		
		this.array = array;
		int length = array.length;
		quicksort(0, length-1);
		return array;

	}

	public void quicksort(int lowIndex, int highIndex) {

		int i = lowIndex;
		int j = highIndex;
		String pivot = array[lowIndex+(highIndex-lowIndex)/2];

		while (i<=j){
			while (array[i].compareTo(pivot) < 0) {
				i++;
			} 
			while (array[j].compareTo(pivot) > 0) {
				j--;
			}
			if(lowIndex <= j){
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (lowIndex < j){
			quicksort(lowIndex, j);
		} 
		if (i < highIndex){
			quicksort(i, highIndex);
		}
	}

	/* Used by sort to exchange two
	numbers in the array on spot i and j*/
	private void exchange(int i, int j){
		String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
	}

	public String [] merge (String [] array1, String [] array2){
		String [] new_array = new String[array1.length + array2.length];
		int i = 0;
		int j = 0; 
		int k = 0;

		while(i < array1.length && j < array2.length) {
			
			if (array1[i].compareTo(array2[j]) < 0) {
				new_array[k++] = array1[i++];
			} else {
				new_array[k++] = array2[j++];
			}
		}

		while (i < array1.length){
			new_array[k++] = array1[i++];
		}
		while (j < array2.length){
			new_array[k++] = array2[j++];
		}
		return new_array;
	}
} 