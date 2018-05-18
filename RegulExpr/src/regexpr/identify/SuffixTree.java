package regexpr.identify;

import java.util.*;

public class SuffixTree {
	int leaf;
	ArrayList<SufTree> node;
	
	public SuffixTree(){
		leaf=-1; node = new ArrayList<>();
	}
	
	public SuffixTree(int index){
		leaf=index; node=null;
	}
	/*
	public SuffixTree(ArrayList<String> suffixs, ArrayList<SuffixTree> trees){
		leaf=-1; node = new ArrayList<>();
		for(int i=0;i<Math.min(suffixs.size(), trees.size()); i++)
			node.add(new SufTree(suffixs.get(i),trees.get(i)));
	}
	*/
	// for testing
	public SuffixTree(String[] suffixs, SuffixTree[] trees){
		leaf=-1; node = new ArrayList<>();
		for(int i=0;i<Math.min(suffixs.length, trees.length); i++)
			node.add(new SufTree(suffixs[i],trees[i]));
	}	
	
	public SuffixTree(String s){
		leaf=-1; node=new ArrayList<>();
		for(int i=0;i<s.length();i++)
			insert(s.substring(i),i);
	}
	
	private void insert(String suffix, int index){
		//System.out.println("..insert suffix=" + suffix + " index=" + index);
		if (node==null){
			node = new ArrayList<>();
			node.add(new SufTree("",new SuffixTree(leaf)));
			node.add(new SufTree(suffix,new SuffixTree(index)));
			leaf=-1;

		} else{
			boolean notIns=true;
			int i=0;
			while(notIns && i<node.size()){
				SufTree son = node.get(i);
				int cb = comBegin(suffix,son.suffix);
				if(cb>0){
					if (cb<son.suffix.length()){
						// son.suffix =p+u1..ul (l>0)
						// suffix = p+v1..vk
						//System.out.println("--common=" + suffix.substring(0,cb) + " from add=" + suffix.substring(cb) + " from this=" + son.suffix.substring(cb));
						SuffixTree nt = new SuffixTree();
						nt.node.add(new SufTree(suffix.substring(cb),new SuffixTree(index)));  // v1..vk
						nt.node.add(new SufTree(son.suffix.substring(cb),son.tree));           // u1..ul
						son.suffix=suffix.substring(0,cb);  // son.suffix = p
						son.tree=nt;
					}
					else son.tree.insert(suffix.substring(cb), index);
					notIns=false;
				} else i++;
			}
			if (notIns) node.add(new SufTree(suffix,new SuffixTree(index)));
		}
	}
	
		
	private int comBegin(String s1, String s2){
		int k=0;
		while(k<s1.length() && k<s2.length() && s1.charAt(k)==s2.charAt(k)) k++;
		return k;
	}
	
	public String toString(){
		String res ="";
		if(node!=null){
			res += "[";
			for(int i=0;i<node.size();i++){
				if(i>0) res += ", ";
				res += node.get(i).toString();
			}
			res +="]";
		} else res += leaf; 
		return res;
	}
	
	class SufTree{
		String suffix;
		SuffixTree tree;
		SufTree(String suf, SuffixTree tr){
			suffix=suf; tree=tr;
		}
		public String toString(){
			return "\"" + suffix +"\" -> " + tree.toString();
		}
	}
}
