package syntax.expr;

public interface Source {
	  public static final char EOF = (char)-1;   // represent end of source char
	  char nextChar();
}
