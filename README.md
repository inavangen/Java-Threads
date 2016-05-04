# Introduction
Demonstrates the use of threads and quicksort in java. The program takes an input file and sort it alphabethically using a user-specified number of threads, and the writes the result to a file before terminating.

# Installation and running
The program take 3 arguments. 
- [0] Number of threads the program should make
- [1] Selected input file
- [2] Name of output file which the program should write to when finished

To run use the commands:
- 'javac *.java' to compile.
- 'java QuickSort [numbers of threads] [input file] [output file]'

##Example: 
- java Quicksort 50 input.txt output.txt

## Text formattng
The first line of the text file should be the number of words in the text file. So if the textifle contains a total number of 500 words, the first line should be 500 (the file should in total have 501 lines).