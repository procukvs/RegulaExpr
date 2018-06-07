package syntax.expr;

public class LexerEx {
	// ��������� ����� ������ � ������ ������� � ���������
	//    ............ ���� : ����� 
	public static final int EOFT = 1;    // represent EOF token type
	public static final int NUMB = 2;
    public static final int LPAREN = 3;  // parenthesis
    public static final int RPAREN = 4;
    public static final int ADDOP = 5;
    public static final int MULOP = 6;
    public static String[] tokenNames =
	        { "n/a", "<EOF>", "NUMB", "LPAREN", "RPAREN", "ADDOP", "MULOP" };
    
    char c;       // current character
    Source input; 
   	
	public LexerEx(Source src) { input = src; consume(); }
	public String getTokenName(int x) { return tokenNames[x]; }
	
	void consume(){ c = input.nextChar(); }
	boolean isDIGIT() { return c>='0'&&c<='9'; }
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
