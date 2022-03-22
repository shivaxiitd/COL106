import java.util.Arrays;

public class StackSort{
		MyStack stack = new MyStack<Integer>();
		String[] seq;
		public StackSort(){
			
		} 

		public int[] returnMin(int[] A){
			Arrays.sort(A);
			return A;
		}

		public String[] sort(int[] a) {
			
		try{	seq = new String[2*(a.length)];
			int[] sorted = new int[a.length];
			int[] t_sort = new int[a.length];
			for(int z=0; z<a.length; z++){
				t_sort[z] = a[z];
			}
			sorted = returnMin(t_sort);
			int temp[] = new int[a.length];
		int top = 0;  // To store the top of the Stack
		int ptr = 0;  // Pointer for traversing the given array to be sorted
		int min = sorted[0];
		int seq_ptr = 0;
		int pop_ptr = 0;
		while(ptr != a.length){
			int e = a[ptr];

			if(e == min){
				stack.push(e);
				seq[seq_ptr] = "PUSH";
				seq_ptr++;
				stack.pop();
				seq[seq_ptr] = "POP";
				seq_ptr++;
				pop_ptr++;
				// System.out.println("pop_ptr: "+pop_ptr);
				if(pop_ptr<a.length)
					min = sorted[pop_ptr];
				while(stack.isEmpty() == false){
					if((int)stack.top() == min){
						// System.out.println("Value of min just bfr insdie while loop: "+min);
						stack.pop();
						seq[seq_ptr] = "POP";
						pop_ptr++;
						seq_ptr++;
						// System.out.println(Arrays.toString(seq));
						if(pop_ptr<a.length)
							min = sorted[pop_ptr];
						// System.out.println("Value of min insdie while loop: "+min);
						// System.out.println((int)stack.pop());
						// System.out.println(Arrays.toString(seq));
					}
					else
						break;
				}
			}
			else{
				stack.push(e);
				seq[seq_ptr] = "PUSH";
				seq_ptr++;
			}
			ptr++;
			// System.out.println("ptr: "+ptr);
		}
		//System.out.println("Final pop_ptr: "+pop_ptr);
		if(pop_ptr == a.length){
			return seq;
		}
		else{
			String[] s = new String[1];
			s[0] = "NOTPOSSIBLE";
			return s;
		}
	}catch(Exception e){
		return null;
		}
	

	}
		public String[][] kSort(int[] a) {
		try{
		int[] t_sort = new int[a.length];
		int n = 0;
		for(int z=0; z<a.length; z++){
			t_sort[z] = a[z];
		}
		int x=0;
		int y=0;
		int[] sorted = new int[a.length];
		
		sorted = returnMin(t_sort);
		if(Arrays.equals(sorted,a)==true){
			String[][] aa = new String[1][2*(a.length)];
			for(int k=0; k<2*(a.length);k++){
				if(k%2==0)
					aa[0][k] = "PUSH";
				else
					aa[0][k] = "POP";
			}
			return aa;
		}
		String[][] str = new String[a.length][2*(a.length)];
		while(Arrays.equals(a, sorted)==false) {
			y=0;
			Arrays.fill(seq, null);
			MyStack stack = new MyStack<Integer>();
			String[] seq = new String[2*(a.length)];

			
			// sorted = returnMin(t_sort);
			int temp[] = new int[a.length];
		int top = 0;  // To store the top of the Stack
		int ptr = 0;  // Pointer for traversing the given array to be sorted
		int min = sorted[0];
		int seq_ptr = 0;
		int pop_ptr = 0;
		while(ptr != a.length){
			int e = a[ptr];

			if(e == min){
				stack.push(e);
				seq[seq_ptr] = "PUSH";
				//System.out.println("Value of y: "+y);
				//System.out.println("Value of x: "+x);
				str[x][y] = "PUSH";
				y++;
				seq_ptr++;
				stack.pop();
				seq[seq_ptr] = "POP";
				str[x][y] = "POP";
				y++;
				temp[pop_ptr] = e;
				seq_ptr++;
				pop_ptr++;
				if(pop_ptr<a.length)
					min = sorted[pop_ptr];
				while(stack.isEmpty() == false){
					if((int)stack.top() == min){
						temp[pop_ptr] = (int)stack.top();
						stack.pop();
						seq[seq_ptr] = "POP";
						str[x][y] = "POP";
						y++;
						pop_ptr++;
						seq_ptr++;
						if(pop_ptr<a.length)
							min = sorted[pop_ptr];
					}
					else
						break;
				}
			}
			else{
				stack.push(e);
				seq[seq_ptr] = "PUSH";
				str[x][y] = "PUSH";
				y++;
				seq_ptr++;
			}
			ptr++;
		}

		for(int t=pop_ptr; t<a.length; t++){
			temp[t] = (int)stack.pop();
			str[x][y] = "POP";
			y++;
		}

		x++;
		y = 0;
		for(int v=0; v<a.length; v++)
			a[v] = temp[v];


		
	}
	int q=0;
	while(str[q][0]!=null){
		q++;
	}
	String[][] final_seq = new String[q][2*(a.length)];
	for(int w=0; w<q; w++){
		for(int l=0; l<2*(a.length); l++){
			final_seq[w][l] = str[w][l];
		}
	}
	return final_seq;
	} catch (Exception e){
		return null;
	}	
}
}
