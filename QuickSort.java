import java.io.*;
class QuickSort {
	public static void main (String [] args){

		int num_threads = Integer.parseInt(args[0]);
		String in_file = args[1];
		String out_file = args[2];
		int num_words; //Num of words in in_file
		String [] word_array;

		/*TODO make error if not correct args*/

		/* Reads the file and initialized an array 
		* containting every word in the file (unsorted)*/
		try { 
			BufferedReader br = new BufferedReader(new FileReader(in_file));	
	    	String word;
	    	int counter = 0;
	    	num_words = Integer.parseInt(br.readLine()); // first line is total num words.
	    	word_array = new String[num_words]; // Initialized array 

	    	while ((word = br.readLine()) != null) {
	    		word_array[counter++] = word;
	    	}

			Monitor monitor = new Monitor(word_array, num_words, num_threads, out_file);


	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	}	
}


