package syntax.lang;

import java.io.*;

public abstract class LexerFull {
	// лексичний аналіз Вхід: рядок (initString) або файл (initFile)	
    public static final char EOF = (char)-1; // represent end of file char
    public static final int EOFT = 1;    // represent EOF token type
    BufferedReader r;
    String input; // input string
    int p;    // index into input of current character
    char c;       // current character

    public LexerFull() {r=null; input=""; p=0; c = ' '; }

    public void initString(String word){
		r=null; input=word; p=0; c = ' '; consume();
	}
    
    /** Move one character; detect "end of file" */
    public void consume() {
    	if (input != null){
    		if ( !(p < input.length()) ) inputLine(); 
    	} else c= EOF;	
        if (c!=EOF) c = input.charAt(p++);
    }
    /** Ensure x is next character on the input stream */
    public void match(char x)throws SyntaxError {
        if ( c == x) consume();
        else throw new SyntaxError("expecting "+x+"; found "+c);
    }   
    
    public boolean initFile(String name) {
		boolean b = false;
		try {
			r = new BufferedReader( new FileReader(name));
			b = true;   // input = r.readLine(); p=0; c = ' ';
			inputLine();  consume();
			//System.out.println(">>>1:" + input + ".." + "p=" + p + " c=" + c + ".. input" + input.charAt(p));
			//System.out.println(">>>:" + input + "..");
		} catch(Exception e) {
			System.out.println(">>>: Lexer.open.file:  " + e.getMessage());	
		}
		return b;
    }
    
    private void inputLine(){
    	if (r!=null) {
    		try {
    			input = r.readLine(); p=0; c=' ';
    			//System.out.println(">>>:" + input + "..");  
    			if (input==null) c=EOF;
    		} catch(Exception e) {
    			System.out.println(">>>: Lexer.input " + e.getMessage());	
    		}	
    	} else c=EOF;
    }
    
    public abstract Token nextToken()throws SyntaxError;
    public abstract String getTokenName(int tokenType);
}
