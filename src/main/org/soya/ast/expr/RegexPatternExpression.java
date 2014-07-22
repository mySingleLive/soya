package org.soya.ast.expr;

import org.soya.ast.ClassNode;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class RegexPatternExpression extends Expression {

    public static final int REGEX_FLAG_I = 0x1;
    public static final int REGEX_FLAG_G = 0x1 << 1;
    public static final int REGEX_FLAG_M = 0x1 << 2;

    private List<Expression> parts;
    private int flag;

    public RegexPatternExpression(List<Expression> parts) {
        setType(ClassNode.PSTRING);
        this.parts = parts;
    }

    public List<Expression> getParts() {
        return parts;
    }

    public void setParts(List<Expression> parts) {
        this.parts = parts;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String toString() {
        return "[Regex Pattern: " + parts + "]";
    }

}
