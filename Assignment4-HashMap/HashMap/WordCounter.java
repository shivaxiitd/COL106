package col106.assignment4.HashMap;

public class WordCounter {

	public WordCounter(){
		// write your code here
	}

	public int count(String str, String word){
		// write your code here
		int c = 0;
		int j = 0;
		int i = 0;
		str.indexOf(str);
		HashMap<String> m = new HashMap<>(10);
		m.put("abc", "abc");
		while (i < str.length()) {
			if (str.charAt(i) == word.charAt(0)) {

				for (j = 1; j < word.length() && (i + j) < str.length(); j++) {
					if (str.charAt(i + j) == word.charAt(j)) {

					} else {
						break;
					}
				}
				if (j == word.length()) {
					c++;
				}
			}
			i++;
		}
		return c;
	}
}
