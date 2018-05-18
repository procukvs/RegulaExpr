package regexpr.use;
import java.util.regex.*;

public class IntegerReg {
	
	public Integer listInt(String s){
		Integer t=0, r = 0;
		String[] sInt = s.split(",");
		int i = 0;
		do{
			t = onlyInt(sInt[i++]);
			if(t==null) r=null; else r +=t;
		} while ((r!=null)&& (i<sInt.length));
		return r;
	}
	// s - ціле зі знаком => value, інакше => null
	public Integer onlyInt(String s){
		Integer r = null;
		String reg = "\\s*([+-])?(\\d+)\\s*";
		Pattern ip = Pattern.compile(reg);
		Matcher im = ip.matcher(s);
		if (im.matches()){                                                  //Pattern.matches(reg,s)){
			r = toInt(im.group(2));
			String sign = im.group(1); 
			//System.out.println("onlyInt. sign = .." + sign + "..");
			if((sign!=null) && (sign.charAt(0)=='-')) r *= (-1);
		}
		return r;
	}
	
	public Double onlyDbl(String s){
		Double r = null;
		String reg = "(?<sn>[+-])?(?<i>\\d+)(?<f>.(\\d+))?([Ee](?<sp>[+-])?(?<p>\\d+))?";
		Pattern ip = Pattern.compile(reg);
		Matcher im = ip.matcher(s);
		if (im.matches()){                                                  //Pattern.matches(reg,s)){
			double rd = 0.0;
			String sn = im.group("sn");
			String f = im.group("f"); 
			String sp = im.group("sp");
			String p = im.group("p");
			if (f!=null) rd = toInt(f.substring(1))/ toPower(f.substring(1)); // toPower(fr)*toInt(fr);     // toFraction(fr);
			r = rd + toInt(im.group("i")); //(Double)toInt(im.group(2));
			//System.out.println("initial s = .." + s + "..");
			//System.out.println("sn = " + sn + " f = " + f + " sp = " + sp + " p = " + p);
			if(p!=null){
				double dpow = (double) Math.pow(10.0,toInt(p));
				boolean mpow = false;
				if((sp!=null) && (sp.charAt(0)=='-')) mpow = true;
				System.out.println("onlyDbl. dpow = .." + dpow + "..");
				//if (mpow) r /= Math.pow(10.0,dpow); else r *= Math.pow(10.0,dpow);
				if (mpow) r /= dpow; else r *= dpow;
			}
			
			String sign = im.group(1); 
			if((sign!=null) && (sign.charAt(0)=='-')) r *= (-1);
			
		}
		return r;
	}
	
	private int toInt(String s){
		int r =0;
		for(int i=0; i<s.length(); i++)
			r=r*10+(s.charAt(i)- '0');
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
		for(int i=0; i<s.length(); i++)	c *= 10; //c *=0.1;
		return c;
	}

}