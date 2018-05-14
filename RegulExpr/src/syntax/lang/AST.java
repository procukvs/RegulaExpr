package syntax.lang;

import java.util.*;

public class AST {
	Token root;
	List<AST> sons; 
	public AST(){    // leaf Eps !!
		root=null; sons=null;
	}
	public AST(Token c){   
		root=c; sons=null;
	}	
	public void addSon(AST s){
		if (sons==null) sons = new ArrayList<AST> ();
		sons.add(s);
	}
	
	public Token getRoot() {
		return root;
	}
	public List<AST> getSons() {
		return sons;
	}
	@Override
	public String toString() {
		if (sons==null)	return(root.toString());
		StringBuilder buf = new StringBuilder();
		buf.append('('); buf.append(root); 
		for (int i=0; i<sons.size(); i++){
			buf.append(' ');
			buf.append(sons.get(i).toString());
		}
		buf.append(')');
		return buf.toString();
	}	
	// обчислює AST, що задає арифметичний вираз
	public int evalExpr(){
		int res,op1,op2;
		switch(root.type){
		case LexerExpr.ADDOP:
			op1=sons.get(0).evalExpr();
			if (sons.size()==2){
				op2=sons.get(1).evalExpr(); 
				if (root.text.equals("-")) res=op1-op2; else res=op1+op2;   
			} else {
				res=-op1;
			}; break;
		case LexerExpr.MULOP: 
			op1=sons.get(0).evalExpr();
			op2=sons.get(1).evalExpr();
			if (!root.text.equals("*")){
				if(op2==0)throw new Error("В операції " + root.text + " дільник = 0");
				if (root.text.equals("/")) res=op1/op2; else res=op1%op2;  
			} else{
				res= op1*op2; 
			}; break;
		case LexerExpr.NUMB: res=evalNumber(root.text); break;	
		default: throw new Error("Неочікувана лексема " + root.toString());
		}
		return res;
	}
	private int evalNumber(String numb){
		int res=0;
		for(int j=0; j<numb.length(); j++)
			res = res*10+(numb.charAt(j)-'0');
		return res;
	}
}
