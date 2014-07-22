package org.soya.ast;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class MatchItem extends TreeNode {
    private Expression pattern;
    private BlockNode block;

    public MatchItem(Expression pattern, BlockNode block) {
        this.pattern = pattern;
        this.block = block;
    }

    public Expression getPattern() {
        return pattern;
    }

    public void setPattern(Expression pattern) {
        this.pattern = pattern;
    }

    public BlockNode getBlock() {
        return block;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(pattern);
        if (block != null) {
            buffer.append(" -> ").append(block);
        }
        return buffer.toString();
    }
}
