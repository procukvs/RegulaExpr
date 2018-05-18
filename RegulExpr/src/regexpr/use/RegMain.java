package regexpr.use;
public class RegMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] s = {"567","-7","+-67","    76  ","-2  "," +43  " };
		String[] ss = {" -567 , +90   , -60 ",
				        "-7 +-67",
				        "    76  ,-2  "," +43  " };
		String[] sd = {"0.567","-0.7","+-67","76.01e+2","1.04E2", "-2e-2","+43" };
		IntegerReg ir = new IntegerReg();
		System.out.println("testing IntegerReg...");
		//for(int i=0; i<s.length; i++)
		//	System.out.println("s[" + i+"]="+s[i] + " result = " + ir.onlyInt(s[i]));
		//for(int i=0; i<ss.length; i++)
		//	System.out.println("ss[" + i+"]="+ss[i] + " result = " + ir.listInt(ss[i]));
		for(int i=0; i<sd.length; i++)
			System.out.println("sd[" + i+"]="+sd[i] + " result = " + ir.onlyDbl(sd[i]));
	}

}
