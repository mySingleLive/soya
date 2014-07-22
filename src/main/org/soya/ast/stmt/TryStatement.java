package org.soya.ast.stmt;

import org.soya.ast.BlockNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class TryStatement extends Statement {

    private BlockNode block;

    private List<CatchStatement> catchStatements;

    public TryStatement(BlockNode block) {
        this(block, new ArrayList<CatchStatement>());
    }

    public TryStatement(BlockNode block, List<CatchStatement> catchStatements) {
        this.block = block;
        this.catchStatements = catchStatements;
    }

    public BlockNode getBlock() {
        return block;
    }

    public List<CatchStatement> getCatchStatements() {
        return catchStatements;
    }

    public void addCatachStatement(CatchStatement catchStatement) {
        this.catchStatements.add(catchStatement);
    }

    public String toString() {
        return "[try " + block + " " + catchStatements + "]";
    }
}
