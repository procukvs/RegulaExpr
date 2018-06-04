package regexpr.use;

import java.util.Arrays;
import java.util.regex.*;

public class ForDeleteRegular {
	public void simpleWork(){
		System.out.println("Simple work with RegEx");
		boolean b1 = Pattern.matches("a+y","aaay");
		System.out.println("Simple methog matches( )" + b1);
		Pattern p1 = Pattern.compile("a+y");
		Matcher m1 = p1.matcher("aaaaaayycaayc");
		boolean b2 = m1.matches();
		System.out.println("Matches in Matcher " + b1 +  " on pattern " + p1.pattern() );
		m1.reset();
		while (m1.find()){
			System.out.println("Matches in cycler " + m1.group());
		}
		String reg = "(?<b>\\w+@)(?<m>(\\w+\\.)+)(?<e>\\w+)"; 
		String s = "Adresfor works: procukvs@gmail.com and my@distedu.ukma.edu.ua ";
		Pattern p2 = Pattern.compile(reg);
		Matcher m2 = p2.matcher(s);
		while (m2.find()){
			System.out.println("e-mail: " + m2.group() + " with components:");
			System.out.println(" .... " + m2.group("b") + "  " + m2.group("m") + " " + m2.group("e")); 
		}
		Pattern p3 = Pattern.compile("\\d+\\s*");
		String[] words = p3.split("java8tiger 77    java6   mustang");
		for (String w : words) System.out.println(".." + w + "..");
	}
	
	public static void main(String[] args) {
		ForDeleteRegular r = new ForDeleteRegular();
		System.out.println("Hello Regular ....");
		//simpleWork();
		String re1 = "a+y";
		String[] s = {"aay", "caayyacaayc"};
		System.out.println("Використовуємо методи String і регулярний вираз " + re1); 
		for(int i=0; i<s.length; i++){
			System.out.println("...Рядок.. " + s[i] + ":"); 
			System.out.println("Відповідає виразу " + re1 + ": " + s[i].matches(re1));
			System.out.println("Розбиваємо за виразом " + re1 + ": " + Arrays.toString(s[i].split(re1)));
			System.out.println("Заміняємо перше входження виразу " + re1 + " на W : " + s[i].replaceFirst(re1,"W"));
			System.out.println("Заміняємо всі входження виразу " + re1 + " на W : " + s[i].replaceAll(re1,"W"));
		}
	}
}
