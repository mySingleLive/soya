package org.soya.antlr;

import antlr.Token;
import org.soya.antlr.parser.SoyaParser;

/**
 * @author: Jun Gong
 */
public class SoyaToken extends Token {
	
	private String text;
	
	private int line;
	
	private int column;
	
	private int endLine;
	
	private int endColumn;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SoyaToken(int t) {
		super(t);
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}
	
	public String toString() {
        String typeName = SoyaParser._tokenNames[type];
		return "[(" + typeName +
                ") text: \"" + getText() + "\"" +
				" line:" + getLine() +
				", col:" + getColumn() +
				", end-line: " + getEndLine() + 
				", end-col: " + getEndColumn() +
				"]"; 
	}
	
}
