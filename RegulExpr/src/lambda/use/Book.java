

package lambda.use;

import java.util.*;
import java.util.regex.Pattern;

public class Book {
	ArrayList <String> authors;
	String titleName;
	int pages;
	Set <String> types;
	String pubName;
	
	
	public Book(ArrayList<String> authors, String titleName, int pages, Set<String> types,  String pubName) {
		super();
		this.authors = authors;
		this.titleName = titleName;
		this.types = types;
		this.pages = pages;
		this.pubName = pubName;
	}
	
	public Book(String input){
		String[] token = input.split("\\s*,\\s*");
		String[] au = token[0].split("\\s*:\\s*");
		authors = new ArrayList(Arrays.asList(au));
		titleName=token[1];
		Pattern nm = Pattern.compile("[+-]?\\d+");
		if(token[2].matches("[+-]?\\d+")) pages = Integer.parseInt(token[2]);
		String[] tp =  token[3].split("\\s*:\\s*");
	    types = new TreeSet(Arrays.asList(tp));
		pubName=token[4];
	}
	
	public ArrayList<String> getAuthors() {
		return authors;
	}
	public String getTitleName() {
		return titleName;
	}
	public Set<String> getTypes() {
		return types;
	}
	public int getPages() {
		return pages;
	}
	public String getPubName() {
		return pubName;
	}
	@Override
	public String toString() {
		return "Book [authors=" + authors + ", titleName=" + titleName + ", pages=" + pages + ", types=" + types
				+ ", pubName=" + pubName + "]";
	}

}
