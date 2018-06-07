package syntax.expr;

public class Lexer {
	// лексичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід : рядок 
	public static int EOFT = 1;    // represent EOF token type
	public static int NUMB = 2;
    public static int LPAREN = 3;  // parenthesis
    public static int RPAREN = 4;
    public static int ADDOP = 5;
    public static int MULOP = 6;
    public static String[] tokenNames =
	        { "n/a", "<EOF>", "NUMB", "LPAREN", "RPAREN", "ADDOP", "MULOP" };
    
    char c;       // current character
    Letter input; 
   	
	public Lexer(Letter src) { input = src; consume(); }
	
	void consume(){ c = input.nextChar(); }
	/*  
	public void match(char x)throws SyntaxError {
	  if ( c == x) consume();
	  else throw new SyntaxError("expecting "+x+"; found "+c);
	} */  
	boolean isDIGIT() { return c>='0'&&c<='9'; }
	
    public String getTokenName(int x) { return tokenNames[x]; }

	public Token nextToken() throws SyntaxError {
	  	//System.out.println("LexerExpr : c= " + c);
	   	while ( c!=input.EOF ) {
	        switch ( c ) {
	                case ' ': case '\t': case '\n': case '\r': WS(); continue;
	                case '(' : consume(); return new Token(LPAREN, "(");
	                case ')' : consume(); return new Token(RPAREN, ")");
	                case '+' : consume(); return new Token(ADDOP, "+");
	                case '-' : consume(); return new Token(ADDOP, "-");
	                case '*' : consume(); return new Token(MULOP, "*");
	                case '/' : consume(); return new Token(MULOP, "/");
	                case '%' : consume(); return new Token(MULOP, "%");
	                default:
	                    if ( isDIGIT() ) return Number();
	                    throw new SyntaxError("invalid character: "+c);
	        }
	    }
	    return new Token(EOFT,"<EOF>");
	}

	Token Number() {
		StringBuilder buf = new StringBuilder();
	    do { buf.append(c); consume(); } while ( isDIGIT() );
	    return new Token(NUMB, buf.toString());
	}

	void WS() {
		while ( c==' ' || c=='\t' || c=='\n' || c=='\r' ) consume();
	}	 
}
