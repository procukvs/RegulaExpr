

package lambda.use;

import java.util.*;

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
