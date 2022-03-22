import java.util.Arrays;

public class MyStack<T> {
	T arr[];
	int initial_len = 100;
	int last_idx;

	public MyStack(){
		arr = (T[])new Object[initial_len];
		last_idx = 0;
	}
	
	public void push(T value){
	
		if(last_idx<initial_len){
			arr[last_idx] = value;
			last_idx++;
		}
		else{
			T new_arr[] = (T[])new Object[2*initial_len];
			for(int j=0; j<last_idx; j++){
				new_arr[j] = arr[j];
			}
			new_arr[last_idx] = value;
			arr = new_arr;
			initial_len = 2*initial_len;
			last_idx++;
		}
	}

	public T pop() throws EmptyStackException{
		
		if(last_idx!=0){
			T temp = arr[last_idx-1];
			arr[last_idx] = null;
			last_idx--;
			return temp;
		}
		else{
			throw new EmptyStackException("Empty Stack!");
		}
	}

	public T top() throws EmptyStackException{
		if(last_idx!=0){
			return arr[last_idx-1];
		}
		else{
			throw new EmptyStackException("Empty Stack!");
		}
	}

	public boolean isEmpty(){
		if(last_idx==0){
			return true;
		}
		else{
			return false;
		}
	}

	private void printStack(){
		for(int i=0; i<last_idx; i++){
			System.out.println(arr[i]);
		}
	}
}

class EmptyStackException extends Exception{
	public EmptyStackException(String s){
		super(s);
	}
}