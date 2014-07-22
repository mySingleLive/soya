package org.soya.ast;

import org.soya.ast.expr.Expression;
import org.soya.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class BlockNode extends Expression {

    private List<Statement> statements = new ArrayList<Statement>();

    public BlockNode() {
        super();
    }

    public BlockNode(Statement... statements) {
        for (int i = 0; i < statements.length; i++) {
            addStatement(statements[i]);
        }
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public String toString() {
        return "[block: " + statements + "]";
    }
}
