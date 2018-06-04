package regexpr.use;

import java.util.regex.*;

public class RegexUse {
	// в рядку шукаються УСІ поштові адреси email=name@domens
	public void findEmail(String wem){
		String rem = "\\w+@(\\w+\\.)+[a-z]{2,4}";  
		Pattern pat = Pattern.compile(rem);
		Matcher mch = pat.matcher(wem);
		while (mch.find()) System.out.println("e-mail: " + mch.group() );
	}

	// s - рядок - цілі числа, що вівіляються комою, можливо, з проміжками 
	// .. результат - якщо вірно, то-сума ВСІХ чисел, в іншому випадку-null
	public Integer listInt(String s){
		Integer t=0, r = 0;
		String[] sInt = s.split(",");
		int i = 0;
		do{
			t = parseInt(sInt[i++]);
			if(t==null) r=null; else r +=t;
		} while ((r!=null)&& (i<sInt.length));
		return r;
	}
	// s - ціле зі знаком => value, інакше => null
	private Integer parseInt(String s){
		Integer r = null;
		String reg = "\\s*([+-])?(\\d+)\\s*";
		Pattern ip = Pattern.compile(reg);
		Matcher im = ip.matcher(s);
		if (im.matches()){                                             
			r = toInt(im.group(2));
			String sign = im.group(1); 
			//System.out.println("onlyInt. sign = .." + sign + "..");
			if((sign!=null) && (sign.charAt(0)=='-')) r *= (-1);
		}
		return r;
	}
	private int toInt(String s){
		int r =0;
		for(int i=0; i<s.length(); i++)	r=r*10+(s.charAt(i)- '0');
		return r;
	}
	
	public Double parseDouble(String s){
		Double r = null;
		String reg = "(?<i>[+-]?\\d+)(?<f>\\.\\d+)?(?<p>[Ee][+-]?\\d+)?";
		Pattern dp = Pattern.compile(reg);
		Matcher dm = dp.matcher(s);
		if (dm.matches() && !s.matches("[+-]?\\d+")){ 
			String i = dm.group("i"); // ціла частина
			String f = dm.group("f"); // можливо дробова частина 
			String p = dm.group("p"); // можлива степінь 
			double rd = 0;
			// обраховуємо цілу частину і перетворюючи в double
			String si=i;
			if(i.charAt(0)=='+' || i.charAt(0)=='-') si=i.substring(1);
			for(int t=0; t<si.length(); t++) rd=rd*10+(si.charAt(t)- '0');
			if(i.charAt(0)=='-') rd = -rd;
			if (f!=null) {    // додаємо дробову частину
				double coef = 1;
				for(int j=1; j<f.length(); j++){
					coef /= 10;
					rd += (f.charAt(j)-'0') * coef;
				}
			}
			if (p!=null){	  //  додаємо степінь
				String pi = p.substring(1);
				if(p.charAt(1)=='+' || p.charAt(1)=='-') pi=p.substring(2);
				double pow = 1;
				int pw = 0;
				for(int k=0; k<pi.length(); k++) pw = pw*10+(pi.charAt(k)-'0');
				for(int k=0; k<pw; k++) pow = pow*10;
				if(p.charAt(1)=='-') rd /= pow; else rd *= pow;
			}
			r = rd;
		}
		return r;
	}

	public void findEmailExt(String wem){
		String rem = "(?<b>\\w+)@(?<m>(\\w+\\.)+)(?<e>[a-z]{2,4})";   // w+ == [a-z]{2,4} !!  w ---[a-zA-Z0-9_] 
		//String rem = "(?<b>\\w+@)(?<m>(\\w+\\.)+)(?<e>\\w+)";  
		// регулярний вираз, що задає електронну адресу !!!
		// групи 1 - <b>:  ім"я скриньки
		// група 2 - <m>:  список імен доменів проміжних рівнів (...третього, другого)  - кожне ім"я закінчується знаком .
		// група 3 -  без імені - останнє з проміжних імен зі знаком .
		// група 4 - <e>:  ім"я домену першого рівня  
		Pattern pat = Pattern.compile(rem);
		Matcher mch = pat.matcher(wem);
		while (mch.find()){
			System.out.println("e-mail: " + mch.group() + " with " + mch.groupCount() + " groups:");
			System.out.println(" ..name=" + mch.group("b") + " domens=" + mch.group("m") + mch.group("e")); 
			System.out.println(" ==name=" + mch.group(1) + " domens=" + mch.group(2) + mch.group(4)); 
		}
	}
	
	
	// s - дійсне число типа: 78.67, 78e-5, 78.67e-3,  78E3
	// .... має дробову частину, можливо зі степенем, або має степень
	public Double onlyDouble(String s){
		Double r = null;
		String reg = "(?<sm>[+-])?(?<i>\\d+)((?<f>\\.\\d+)([Ee](?<s1>[+-])?(?<p1>\\d+))?|([Ee](?<s2>[+-])?(?<p2>\\d+)))";
		Pattern ip = Pattern.compile(reg);
		Matcher im = ip.matcher(s);
		if (im.matches()){                                                  //Pattern.matches(reg,s)){
			String sm = im.group("sm"); // знак всього числа
			String f = im.group("f");   // дробова частина: якщо є, то немає p2 !!!! 
			String p1 = im.group("p1"); //   можлива степінь за дробовою частиною
			String s1 = im.group("s1"); //   можливий знак перед степеню p1
			String p2= im.group("p2");  // лише степень за цілою частиною: якщо є, то немає f !!!!  
			String s2 = im.group("s2"); //   можливий знак перед степеню p2
			double rd = toInt(im.group("i"));
			double pow = 0;
			boolean isNeg = false;
			if (f!=null) {    //  є дробова частина
				rd += toFraction(f.substring(1));
				if(p1!=null){ // є степінь за дробовою частиною
					pow = toPower(p1);
					isNeg = (s1!=null) && (s1.charAt(0)=='-');
					if (isNeg) rd /= pow; else rd *= pow; 
				}
			}
			else{             // лише степень за цілою частиною
				//System.out.println("integer+power");
				pow = toPower(p2);
				isNeg = (s2!=null) && (s2.charAt(0)=='-');
				if (isNeg) rd /= pow; else rd *= pow; 				
			}
			isNeg = (sm!=null) && (sm.charAt(0)=='-');
			if(isNeg) rd *= (-1);
			r=rd;
		}
		return r;
	}	
	private double toFraction(String s){
		double r=0, c=1;
		for(int i=0; i<s.length(); i++){
			c *=0.1;
			r += ((s.charAt(i)- '0')*c);
		}
		return r;
	}
	private double toPower(String s){
		double c=1;
		for(int i=0; i<toInt(s); i++)	c *= 10; //c *=0.1;
		return c;
	}	
	/*
	private int transInt(String s){
		int r = 0;
		String si=s;
		if(s.charAt(0)=='+' || s.charAt(0)=='-') si=s.substring(1);
		for(int i=0; i<si.length(); i++) 
			r=r*10+(s.charAt(i)- '0');
		if(s.charAt(0)=='-') r = -r;
		return r;
	}
	*/
}
