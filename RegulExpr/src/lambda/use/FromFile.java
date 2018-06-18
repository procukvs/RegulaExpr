package lambda.use;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
//import java.util.stream.Collectors.*;


public class FromFile {
	
	static Long cntWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try {
			Stream<String> words = Files.lines(Paths.get(file))
					                    .flatMap(l->delim.splitAsStream(l));
			return words.collect(Collectors.counting());
		} catch(Exception ex){
	     	System.out.println("cntWords: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	static Long cntWordsWitha(String file){
		Pattern delim = Pattern.compile("\\s+");
		try {
			Stream<String> words = Files.lines(Paths.get(file))
                    .flatMap(l->delim.splitAsStream(l));
			return words.filter(Pattern.compile("\\.*a\\.*")
					               .asPredicate())
					    .collect(Collectors.counting());
		} catch(Exception ex){
	     	System.out.println("cntWordsWitha: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	static Long cntDistinctWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try {
			Stream<String> words = Files.lines(Paths.get(file))
                    .flatMap(l->delim.splitAsStream(l));
			return words.distinct()
					    .count();
		} catch(Exception ex){
	     	System.out.println("cntDistinctWords: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	
	static Map<String,Long> frequentWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try {
			Stream<String> words = Files.lines(Paths.get(file))
                    .flatMap(l->delim.splitAsStream(l));
			return words.collect(Collectors.groupingBy(x->x,Collectors.counting()));
		} catch(Exception ex){
	     	System.out.println("frequentWords: " + ex.getMessage());
	     	return null;
	    }            	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" Count words = " + cntWords("file.dat"));
		System.out.println(" Count distinct words = " + cntDistinctWords("file.dat"));
		Map<String,Long> m = frequentWords("file.dat");
		if (m != null) System.out.println(m);
		/*
		Pattern delim =Pattern.compile("\\s+");
		//try(Stream<String>lines = Files.lines(Paths.get("file.dat"))){
		try {
			Stream<String>lines = Files.lines(Paths.get("file.dat"));
			//lines.map(l->l.split("\\s+")).forEachOrdered(x->System.out.println(Arrays.toString(x)));
			//lines.flatMap(l->delim.splitAsStream(l)).forEachOrdered(System.out::println);
			//System.out.println(lines.flatMap(l->delim.splitAsStream(l)).count());
			//String s =lines.flatMap(l->delim.splitAsStream(l)).collect(Collectors.groupingBy(x->x)).toString();
			//System.out.println(s);
			//String s = lines.flatMap(l->delim.splitAsStream(l))
			//		.collect(Collectors.groupingBy(x->x,Collectors.counting())).toString();
			//System.out.println(s);
			Map<String,Long> m = lines.flatMap(l->delim.splitAsStream(l))
						.collect(Collectors.groupingBy(x->x,Collectors.counting()));
			System.out.println(m);
		} catch(Exception ex){
			System.out.println("FromFile: " + ex.getMessage());
		} 
		*/                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	}

}
