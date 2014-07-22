package org.soya.antlr.util;

import java.util.LinkedList;
import java.util.List;

import org.soya.ast.expr.ConstantExpression;
import org.soya.ast.expr.Expression;
import org.soya.ast.expr.TupleExpression;

public class TupleUtil {
	
	public static TupleExpression parsePair(String text) {
		int len = text.length();
		List<Expression> nums = new LinkedList<Expression>();
		int n = 0;
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c)) {
				n = n * 10 + (c - '0');
			}
			else if (c == 'x' || c == 'X') {
				nums.add(new ConstantExpression(n));
				n = 0;
			}
		}
		nums.add(new ConstantExpression(n));
		TupleExpression expression = new TupleExpression(nums, true);
		return expression;
	}

}
