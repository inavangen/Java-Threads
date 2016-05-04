import java.io.*;
class Monitor {
	
	/* Should be able to receive a sorted array
	* Should be able to send 2 sorted array
	* (A thread can either give the monitor a sorted array
	* or get two new arrays to sort )
	* 
	*/

	private String [] word_array; // All words
	private String [] array1; // Temp array
	private String [] array2; // Temp array
	private String out_file;
	private volatile String [] fin_array;

	private int num_words;
	private int num_threads; // total number of threads
	private int cur_num_threads; // current number of threads
	private int finished_threads = 0;
	private int words_per_thread;
	private int rest;

	Monitor(String [] word_array, int num_words, int num_threads, String out_file){
		this.word_array = word_array;
		this.num_words = num_words;
		this.num_threads = num_threads;
		this.out_file = out_file;

		calc_words_per_thread();
		initialize_threads();
	}

  	/* Creates and starts the appropriate number of threads*/
	public void initialize_threads () {
		
		int count = 0;
		String [] temp_array;

		while (cur_num_threads != num_threads){
		
			/*If there is no rest anymore */
			if (rest == 0){
				temp_array = new String[words_per_thread];
				for (int i =0 ; i < words_per_thread ; i++ ) {
					temp_array[i] = word_array[count++];	
				}
				System.out.println("Created array of: " + words_per_thread);
				

			/*If there is a rest */
			} else {
				temp_array = new String[rest];
				for (int i =0 ; i < rest ; i++ ) {
					temp_array[i] = word_array[count++];	
				}
				System.out.println("Created rest of: " + rest);
				rest = 0;
			}
			System.out.println("Sorting...");
			new SortThread("", temp_array, this).start();
			cur_num_threads++;
		}			
	}

	public void calc_words_per_thread(){
		/* If number of words and threads adds up 
		TODO: If there are more threads than words */
		if(num_words%num_threads == 0){
			words_per_thread = num_words/num_threads;
			rest = 0;
		/*If not there has to be a last thread*/
		} else {
			rest = (num_words%num_threads);
			words_per_thread = (num_words-rest)/num_threads;
			rest+=words_per_thread;
		}
			System.out.println("Words per thread:: " + words_per_thread);
			System.out.println("Rest thread: " + rest);
	}


	/* Notifies monitor that one thread is finished
	* If two threads have given their arrays: Create
	* a new thread and call merge()	*/
	synchronized public void notify(String [] new_array){

		/* Checks if either temp arrays are empty */
		if(array1 == null){
			array1 = new_array;
		} else if (array2 == null){
			array2 = new_array;

		/*If both arrays are full, start a thread an call 
		merge(array1, array2), empty both, and put 
		new_array in array1 */
		} else {
			System.out.println("Merging...");
			new SortThread("", array1, array2, this).start();
			array1 = new_array;
			array2 = null;
		}
		/*Check if finished*/
		if(array1 != null & array2 != null){
			is_last_threads(array1, array2);
		}
	} 

	/* Checks if two arrays have the same length as the total
	numbers of words. If so, they are the last remining arrays 
	and the programs calls one last merge before writing to file  */
	synchronized public void is_last_threads(String[] array1, String[] array2){

		if ((array1.length + array2.length) == num_words){
			SortThread t = new SortThread("", array1, array2, this);
			String [] fin_array = t.merge(array1, array2);
			write_to_file(fin_array);
		}
	}

	/* Writes finished array to file. */
	synchronized public void write_to_file(String [] fin_array){
		System.out.println("write to file");

		try {
			FileWriter fw = new FileWriter(out_file);
			BufferedWriter br = new BufferedWriter(fw);

			for (int i = 0; i<fin_array.length ;i++ ) {
				br.write(fin_array[i]);
				br.newLine();
			}
			br.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}