package syntax.lang;

public class ExprLexerFull extends LexerFull {
	// лексичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід: рядок (analys) або файл (analysFile)	
    public static int NUMB = 2;
    public static int LPAREN = 3;  // parenthesis
    public static int RPAREN = 4;
    public static int ADDOP = 5;
    public static int MULOP = 6;
    public static String[] tokenNames =
        { "n/a", "<EOF>", "NUMB", "LPAREN", "RPAREN", "ADDOP", "MULOP" };
    public String getTokenName(int x) { return tokenNames[x]; }

    public ExprLexerFull() { super(); }
    boolean isDIGIT() { return c>='0'&&c<='9'; }

    public Token nextToken() throws SyntaxError {
    	while ( c!=EOF ) {
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

    /** NUMB : ('0'..'9')+; // NUMB is sequence of >=1 digits */
    Token Number() {
        StringBuilder buf = new StringBuilder();
        do { buf.append(c); consume(); } while ( isDIGIT() );
        return new Token(NUMB, buf.toString());
    }

    /** WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
    void WS() {
        while ( c==' ' || c=='\t' || c=='\n' || c=='\r' ) consume();
    }
}