

package lambda;

import java.util.*;

public class Book {
	ArrayList <String> authors;
	String titleName;
	Set <String> types;
	int pages;
	String pubName;
	
	
	public Book(ArrayList<String> authors, String titleName, Set<String> types, int pages, String pubName) {
		super();
		this.authors = authors;
		this.titleName = titleName;
		this.types = types;
		this.pages = pages;
		this.pubName = pubName;
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

}
