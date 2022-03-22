package col106.assignment4.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import col106.assignment4.HashMap.HashMap;
import col106.assignment4.WeakAVLMap.WeakAVLMap;

public class Map<V> {
	static PrintStream out;

	public PrintStream fileout() {
		return out;
	}
	
	public Map() {
		// write your code here	
	}

	public void eval(String inputFileName, String outputFileName) throws FileNotFoundException {
		// write your code here	
		// inputFileName = "src/col106/assignment4/Map/" + inputFileName;
		// outputFileName = "src/col106/assignment4/Map/" + outputFileName;
		File file;
		out = new PrintStream(new FileOutputStream(outputFileName, false), true);
		System.setOut(out);
		
		file = new File(inputFileName);
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			st = br.readLine().trim();
			int commands = Integer.parseInt(st);
			HashMap<String> h = new HashMap<>(commands);
			WeakAVLMap<String, String> w = new WeakAVLMap<>();
			long hashTimeInsert = 0;
			long hashTimeDelete = 0;
			long wavlTimeInsert = 0;
			long wavlTimeDelete = 0;
			long ini = 0;
			long fin = 0;
			for (int i = 0; i < commands; i++) {
				st = br.readLine().trim();
				String[] ar = st.split(" ");
				if (ar[0].equals("I")) {
					ini = System.currentTimeMillis();
					h.put(ar[1].replace(",", ""), ar[2]);
					fin = System.currentTimeMillis();
					hashTimeInsert += fin - ini;
					ini = System.currentTimeMillis();
					w.put(ar[1].replace(",", ""), ar[2]);
					fin = System.currentTimeMillis();
					wavlTimeInsert += fin - ini;

				} else if (ar[0].equals("D")) {
					ini = System.currentTimeMillis();
					h.remove(ar[1].replace(",", ""));
					fin = System.currentTimeMillis();
					hashTimeDelete += fin - ini;
					ini = System.currentTimeMillis();
					w.remove(ar[1].replace(",", ""));
					fin = System.currentTimeMillis();
					wavlTimeDelete += fin - ini;
				}

			}

			System.out.println("Operations " + "WAVL " + "HashMap");
			System.out.println("Insertions " + wavlTimeInsert + " " + hashTimeInsert);
			System.out.println("Deletions " + wavlTimeDelete + " " + hashTimeDelete);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
