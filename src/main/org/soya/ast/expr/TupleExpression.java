package org.soya.ast.expr;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class TupleExpression extends ListExpression {

	private boolean isPair;
	
	public TupleExpression(List<Expression> items, boolean isPair) {
		super(items);
		this.isPair = isPair;
    }

	public boolean isPair() {
		return isPair;
	}
	
	public String toString() {
		return "[tuple " + this.getItems().toString() + "]";
	}
}
