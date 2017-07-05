// drag test.tab and train.tab to malikasaksena (users)

/* ASSIGNMENT DETAILS

[1] Download and install Java and Eclipse which is the IDE for Java.

Read up the relevant articles online , to understand how to create a Java project in Eclipse.

Try a sample Hello-World example.



[2] Download the 20 newsgroups dataset from https://github.com/alexeygrigorev/datasets/tree/master/20-newsgroups

And work on the following problem in parts

(a) Read the train and test file line by line using BufferedReader/FileReader

(b) Build a dictionary of terms and classes based on the train file.


The lines in these files look like (for example)


"alt.atheism     atheismresource,resource,december,version,atheist,resources,addresses,atheist,organizations,usa,freedom..."

"talk.religion.misc      article,jim,burrill,write,jesus,never,teach,concept,trinity..."



The first token "alt.atheism" is the class of the document whose tokens are listed next (comma-separated)


What your program should do is:

After reading each line, use String class/split function to separate each line into tokens:

alt.atheism     
atheismresource
resource
december...

Then we use the tokens to create dictionaries. 

That is, each class token and term token should be assigned a different Integer ID


For example alt.atheism should be assigned 1, talk.religion.misc should be assigned 2 in the class dictionary.


Similarly, each unique token atheismresource, december etc... are assigned Integer IDs 1,2, 3...



Use Hashtable class/functions in Java



(c) At the end, print out two files corresponding to each dictionary. Use PrintWriter/FileWriter classes for this.



Sample output looks like



#ClassName ClassID

alt.atheism 1

talk.religion.misc 2




=========================


 */

import java.io.*;
import java.util.*;

public class newsgroups20 {
	// ----------------------------------------FileOpen------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	// Buffered Reader
	// write a function that opens the file (test.tab or train.tab)
	private static BufferedReader in;

	public static void fileOpen() {
		try {
			in = new BufferedReader(new FileReader(new File(
					System.getProperty("user.home"), "train.tab")));
		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------------------------------

	// ----------------------------------------FileOutput------------------------------------------------
	// --------------------------------------------------------------------------------------------------

	// Output
	// write a function that will output the read file into the console
	public static void fileOutput() {
		while (true) {
			try {
				System.out.println(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------

	// ----------------------------------------HashTables----------------------------------------------
	// --------------------------------------------------------------------------------------------------
	// 1) create two hash tables
	public static void fileTokens() {
		// key is string, value is integer
		Hashtable<String, Integer> classTokens = new Hashtable<String, Integer>();
		Hashtable<String, Integer> termTokens = new Hashtable<String, Integer>();

		String line;
		try {
			while (true) {
				line = in.readLine();
				// System.out.print(line + "\n");
				if (line == null)
					break;
				String[] part = line.split("\\s");

				// class tokens------------------
				String Key_class = part[0];
				Integer n_class = classTokens.get(Key_class);
				if (n_class == null) {
					classTokens.put(Key_class, classTokens.size() + 1);
					// key = t, value = size
				}
				n_class = termTokens.get(Key_class);

				// term tokens--------------------
				for (int i = 1; i < part.length; i++) {
					String Key_terms = part[i];
					Integer n_terms = termTokens.get(Key_terms);
					if (n_terms == null) {
						termTokens.put(Key_terms, termTokens.size() + 1);
					}
					n_terms = termTokens.get(Key_terms);

				}
				// keeps reading until end of file
			}

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --------------------------- WRITING TO
		// FILE------------------------------
		FileWriter fw;
		try {
			fw = new FileWriter(new File("TrainDictionary.txt"));
			PrintWriter pw = new PrintWriter(fw, true);
			// reading elements of class and then terms
			Enumeration<String> classKeys = classTokens.keys();
			Enumeration<String> termKeys = termTokens.keys();

			// read class table
			// System.out.println("---------------------Classes---------------------");
			for (int i = 0; i < classTokens.size(); i++) {
				String classKey = classKeys.nextElement();
				fw.write(String.format(classKey + " "
						+ classTokens.get(classKey)));
				fw.write(System.lineSeparator()); // new line

				System.out.println(classKey + " " + classTokens.get(classKey));
			}

			// read term table
			// System.out.println("---------------------Terms---------------------");
			while (termKeys.hasMoreElements()) {
				String termKey = termKeys.nextElement();
				pw.println(termKey + " " + termTokens.get(termKey));
			}

			pw.close();
			System.out.println("Written to file TrainDictionary.txt");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// --------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// --------------------------------MAIN----------------------------------------------//
	public static void main(String[] args) {
		fileOpen();
		// fileOutput();
		fileTokens();
	}

}
