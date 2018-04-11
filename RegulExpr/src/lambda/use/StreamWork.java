package lambda.use;

import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class StreamWork {
	
	//   5.13  +  5.14 
	// ������� ���� ��������� ������ ������������
	public Map <String, Integer> numberOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));
		Map <String, Integer> numberOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			numberOfBooks.put(entry.getKey(), entry.getValue().size());
		}
		return numberOfBooks;
	}
	
	public Map <String, Long> numberOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(book->book.getPubName(),
				                      Collectors.counting()));
	}
	
     //  5.15  +  5.16 
	// ������ ���� ��������� ������ ������������
	public Map <String, List<String>> nameOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));
		Map <String, List<String>> nameOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			nameOfBooks.put(entry.getKey(), entry.getValue()
					                             .stream()
					                             .map(Book::getTitleName)
					                             .collect(Collectors.toList()));
		}
		return nameOfBooks;
	}
	public Map <String, List<String>> nameOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(book->book.getPubName(),
                               Collectors.mapping(Book::getTitleName,
                               Collectors.toList())));
	}
}
