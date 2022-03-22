import java.util.Arrays;

public class MyCalculator{

	String new_str = "";
	
	public int calculate(String expression) {
		try{
		expression = expression.replaceAll("\\s","");
		char ch[] = expression.toCharArray();
		
		for(int itr=0; itr<expression.length(); itr++){
			if(ch[itr]=='+'||ch[itr]=='-'||ch[itr]=='*'||ch[itr]=='('||ch[itr]==')'){
				new_str += " "+Character.toString(ch[itr])+" ";
			}
			else
				new_str += Character.toString(ch[itr]);
		}

		char[] tokens = new_str.toCharArray();

		MyStack values = new MyStack<Integer>();
		MyStack ops = new MyStack<Character>();

		for(int i=0; i<tokens.length; i++){
			if(tokens[i]==' ')
				continue;
			if(tokens[i] >= '0' && tokens[i] <= '9'){
				StringBuffer str = new StringBuffer();
				while(i<tokens.length && tokens[i]>='0' && tokens[i]<='9'){
					str.append(tokens[i++]);
				}
				values.push(Integer.parseInt(str.toString()));
			}
			else if(tokens[i]=='(')
				ops.push(tokens[i]);
			else if(tokens[i]==')'){
				while((char)ops.top() != '(')
					values.push(operated((char)ops.pop(),(int)values.pop(),(int)values.pop()));
				ops.pop();
			}
			else if (tokens[i]=='+'||tokens[i]=='-'||tokens[i]=='*') {
				while(!ops.isEmpty() && bodmas(tokens[i],(char)ops.top()))
					values.push(operated((char)ops.pop(),(int)values.pop(),(int)values.pop()));

				ops.push(tokens[i]);
			}
		}

		while(!ops.isEmpty())
			values.push(operated((char)ops.pop(),(int)values.pop(),(int)values.pop()));
		
		return (int) values.pop();
	}catch(Exception e){
		return 0;
		}
	}

	public static boolean bodmas(char op1, char op2){
		if (op2=='('||op2==')') {
			return false;
		}
		if (op1=='*'&&(op2=='+'||op2=='-')) {
			return false;
		}
		else
			return true;
	}

	public static int operated(char op, int b, int a){
		switch (op) 
        { 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
		}
		return 0;
		
	
	}	
}