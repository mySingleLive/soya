package org.soya.ast.stmt;

import org.soya.ast.BlockNode;
import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class CatchStatement extends Statement {
    private ClassNode exceptionType;
    private String parameter;
    private BlockNode block;

    public CatchStatement(String parameter, BlockNode block) {
        this(parameter, ClassNode.make(Throwable.class), block);
    }

    public CatchStatement(String parameter, ClassNode exceptionType, BlockNode block) {
        this.exceptionType = exceptionType;
        this.parameter = parameter;
        this.block = block;
    }

    public ClassNode getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ClassNode exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public BlockNode getBlock() {
        return block;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }

    public String toString() {
        return "[catch (" + parameter + ") " + block + "]";
    }
}
