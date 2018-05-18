package regexpr.identify;

public class Identify {
	// машина ідентифікації слів
	String word;       // ідентифікуєме слово
	int[] failure;     // функція відмов
	
	public Identify(String w){
		int i;
		word = w;
		// будуємо функцію відмов
		failure = new int[word.length()];
		failure[0]=-1;
		for(int j=1; j<failure.length;j++){
			i=failure[j-1];
			while((i>=0) && (word.charAt(j)!=word.charAt(i+1)))i=failure[i];
			if((i==-1) && (word.charAt(j)!=word.charAt(i+1)))failure[j]=-1;
			else failure[j]=i+1;
		}
	}
	
	// повертає 
	// -1   <=> слово-образ word не входить в текст text
	// i>=0 <=> саме ліве входження слова-образу word 
	//           в текст text починається з позиції i
	public int find(String text){
		int res = -1;  // очікуємий результат
		int k=0;  // перший НЕ пройдений символ text 
		int j=0;  // перший НЕ пройдений символ word
		//System.out.println("text="+text + " word=" + word);
		//System.out.println("in text:k="+k + " word:j=" + j);
		while(j<word.length() && k<text.length()){
			while(j>=0 && word.charAt(j)!=text.charAt(k))
				if(j>0)j = failure[j-1]+1; else j=-1;
			if(j==-1)j=0; 
			//System.out.println("w1 text:k="+k + " word:j=" + j);
			if(j>=0 && word.charAt(j)==text.charAt(k))j++;
			k++;
			//System.out.println("w2 text:k="+k + " word:j=" + j);
		}
		if(j==word.length())res = k-word.length();
		return res;
	}
	@Override
    public String toString(){
		String res = "Identify = word:" +word + "\n  failure:";
		for(int j=0;j<failure.length;j++)
			res += "(" + j + ")->" + failure[j] + " ";
		return res;
	}
}
