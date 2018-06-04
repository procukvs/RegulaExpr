package syntax.grammar;

import java.util.*;

public class SynTree {
	private Character root;
	private List<SynTree> sons; 
	public SynTree(){    // leaf Eps !!
		// ������ ������, �� ���������� ���� ������������ ������� ������ A->Eps (������� �����) 
		root=null; sons=null;
	}
	public SynTree(char c){  
		// ������-������, �� ������ ������ c (������� ��� ���������) 
		root=c; sons=null;
	}	
	public void addSon(SynTree s){
		if (sons==null) sons = new ArrayList<SynTree> ();
		sons.add(s);
	}
	
	public Character getRoot() {
		return root;
	}
	public List<SynTree> getSons() {
		return sons;
	}
	@Override
	public String toString() {
		if (sons==null)
			return(root==null?"Eps":String.valueOf(root));
		StringBuilder buf = new StringBuilder();
		buf.append('('); buf.append(root); 
		for (int i=0; i<sons.size(); i++){
			buf.append(' ');
			buf.append(sons.get(i).toString());
		}
		buf.append(')');
		return buf.toString();
	}	
}
