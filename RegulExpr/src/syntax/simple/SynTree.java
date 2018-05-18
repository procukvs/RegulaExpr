package syntax.simple;

import java.util.*;

public class SynTree {
	Character root;
	List<SynTree> sons; 
	public SynTree(){    // leaf Eps !!
		root=null; sons=null;
	}
	public SynTree(char c){   
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
