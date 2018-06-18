package lambda.use;

import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class LibraryWork {
	// загальна кількість книг в бібліотеці
	public long countAllBooks(Stream <Book> library){
		return library.collect(Collectors.counting());
		//return library.count();
	}
		
	// множина всіх авторів, книги яких є в бібліотеці
	public Set<String> allAuthors(Stream <Book> library){
		//Stream<String> sa = library.flatMap(x->x.getAuthors().stream());
		return library.flatMap(x->x.getAuthors().stream())   
				      .collect(Collectors.toSet());
	}
	
	//   5.13  +  5.14 
	// кількість книг випущених кожним видавництвом
	public Map <String, Integer> numberOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));  // (Book::getPubName) (book->book.getPubName())
		Map <String, Integer> numberOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			numberOfBooks.put(entry.getKey(), entry.getValue().size());
		}
		return numberOfBooks;
	}
	
	public Map <String, Long> numberOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(Book::getPubName, // (Book::getPubName) book->book.getPubName()
				                      Collectors.counting()));
	}
	
     //  5.15  +  5.16 
	// список книг випущених кожним видавництвом
	public Map <String, List<String>> nameOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));
		Map <String, List<String>> nameOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			nameOfBooks.put(entry.getKey(), entry.getValue()
					                             .stream()
					                             .map(b->b.getTitleName())  //(Book::getTitleName)
					                             .collect(Collectors.toList()));
		}
		return nameOfBooks;
	}
	public Map <String, List<String>> nameOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(book->book.getPubName(), // Book::getPubName
                               Collectors.mapping(b->b.getTitleName(),        //Book::getTitleName,
                               Collectors.toList())));
	}
	
	
	
}
